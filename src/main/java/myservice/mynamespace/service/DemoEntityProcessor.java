
package myservice.mynamespace.service;

import myservice.mynamespace.database.data.CRUDHandler;
import myservice.mynamespace.util.Util;
import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.ContextURL.Suffix;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmNavigationProperty;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpMethod;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.deserializer.DeserializerException;
import org.apache.olingo.server.api.deserializer.DeserializerResult;
import org.apache.olingo.server.api.deserializer.ODataDeserializer;
import org.apache.olingo.server.api.processor.EntityProcessor;
import org.apache.olingo.server.api.serializer.EntitySerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.UriResourceNavigation;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class DemoEntityProcessor implements EntityProcessor {

    private OData odata;
    private ServiceMetadata srvMetadata;
    private CRUDHandler mCRUDHandler;

    public DemoEntityProcessor(CRUDHandler CRUDHandler) {
        this.mCRUDHandler = CRUDHandler;
    }

    public void init(OData odata, ServiceMetadata serviceMetadata) {
        this.odata = odata;
        this.srvMetadata = serviceMetadata;//umbennen?
    }

    /**
     * This method is invoked when a single entity has to be read.
     * In our example, this can be either a "normal" read operation, or a navigation:
     * <p>
     * Example for "normal" read operation:
     * http://localhost:8080/DemoService/DemoService.svc/Products(1)
     * <p>
     * Example for navigation
     * http://localhost:8080/DemoService/DemoService.svc/Products(1)/Category
     */
    public void readEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType responseFormat)
        throws ODataApplicationException, SerializerException {

        EdmEntityType responseEdmEntityType = null; // we'll need this to build the ContextURL
        EdmEntitySet responseEdmEntitySet = null; // we need this for building the contextUrl
        Entity responseEntity = null; // required for serialization of the response body

        // 1st step: retrieve the requested Entity: can be "normal" read operation, or navigation (to-one)
        final List<UriResource> resourceParts = uriInfo.getUriResourceParts();
        final int segmentCount = resourceParts.size();

        final UriResource uriResource = resourceParts.get(0); // in our example, the first segment is the EntitySet
        if (!(uriResource instanceof UriResourceEntitySet)) {
            throw new ODataApplicationException("Only EntitySet is supported", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ENGLISH);
        }

        final UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) uriResource;
        final EdmEntitySet startEdmEntitySet = uriResourceEntitySet.getEntitySet();

        // Analyze the URI segments
        if (segmentCount == 1) { // no navigation
            responseEdmEntityType = startEdmEntitySet.getEntityType();
            responseEdmEntitySet = startEdmEntitySet; // since we have only one segment

            // 2. step: retrieve the data from backend
            final List<UriParameter> keyPredicates = uriResourceEntitySet.getKeyPredicates();
            responseEntity = mCRUDHandler.readEntityData(startEdmEntitySet, keyPredicates);
        } else if (segmentCount == 2) { // navigation
            final UriResource navSegment = resourceParts.get(1); // in our example we don't support more complex URIs
            if (navSegment instanceof UriResourceNavigation) {
                final UriResourceNavigation uriResourceNavigation = (UriResourceNavigation) navSegment;
                final EdmNavigationProperty edmNavigationProperty = uriResourceNavigation.getProperty();
                responseEdmEntityType = edmNavigationProperty.getType();//todo kriege ich das gewünschte?
                // contextURL displays the last segment
                responseEdmEntitySet = Util.getNavigationTargetEntitySet(startEdmEntitySet, edmNavigationProperty);

                // 2nd: fetch the data from backend.
                // e.g. for the URI: Products(1)/Category we have to find the correct Category entity
                final List<UriParameter> keyPredicates = uriResourceEntitySet.getKeyPredicates();
                // e.g. for Products(1)/Category we have to find first the Products(1)
                final Entity sourceEntity = mCRUDHandler.readEntityData(startEdmEntitySet, keyPredicates);

                // now we have to check if the navigation is
                // a) to-one: e.g. Products(1)/Category
                // b) to-many with key: e.g. Categories(3)/Products(5)
                // the key for nav is used in this case: Categories(3)/Products(5)
                final List<UriParameter> navKeyPredicates = uriResourceNavigation.getKeyPredicates();
                if (navKeyPredicates.isEmpty()) { // e.g. DemoService.svc/Products(1)/Category
                    responseEntity = mCRUDHandler.getRelatedEntity(sourceEntity, responseEdmEntityType);//TODO testen to many, also /Carriers(1)/Flights(1)
                } else { // e.g. DemoService.svc/Categories(3)/Products(5)
                    responseEntity = mCRUDHandler.getRelatedEntity(sourceEntity, responseEdmEntityType, navKeyPredicates);
                }
            }
        } else {
            // this would be the case for e.g. Products(1)/Category/Products(1)/Category
            throw new ODataApplicationException("Not supported", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
        }

        if (responseEntity == null) {
            // this is the case for e.g. DemoService.svc/Categories(4) or DemoService.svc/Categories(3)/Products(999) --> product existing but not in that cat.
            throw new ODataApplicationException("Nothing found.", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ROOT);
        }

        // 3. serialize
        final ContextURL contextUrl = ContextURL.with().entitySet(responseEdmEntitySet).suffix(Suffix.ENTITY).build();
        final EntitySerializerOptions opts = EntitySerializerOptions.with().contextURL(contextUrl).build();

        final ODataSerializer serializer = this.odata.createSerializer(responseFormat);
        final SerializerResult serializerResult = serializer.entity(this.srvMetadata, responseEdmEntityType, responseEntity, opts);

        // 4. configure the response object
        response.setContent(serializerResult.getContent());
        response.setStatusCode(HttpStatusCode.OK.getStatusCode());
        response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());
    }

  /*
   * These processor methods are not handled in this tutorial
   */

    //  public void createEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo,
    //      ContentType requestFormat, ContentType responseFormat)
    //      throws ODataApplicationException, DeserializerException, SerializerException {
    //    throw new ODataApplicationException("Not supported.", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
    //  }

    /**
     * Example request:
     * <p>
     * POST URL: http://localhost:8080/DemoService/DemoService.svc/Products<p>
     * Header: Content-Type: application/json; odata.metadata=minimal
     * Request body:
     * {
     * "ID":3,
     * "Name":"Ergo Screen",
     * "Description":"17 Optimum Resolution 1024 x 768 @ 85Hz, resolution 1280 x 960"
     * }
     */
    public void createEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType requestFormat, ContentType responseFormat)
        throws ODataApplicationException, DeserializerException, SerializerException {
        //1. Retrieve the entity type from the URI
        final EdmEntitySet edmEntitySet = Util.getEdmEntitySet(uriInfo);
        final EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        //2. create the data in the backend
        //2.1 retrieve the payload from the POST request for the entity to create and deserialize it
        final InputStream requestInputStream = request.getBody();
        final ODataDeserializer deserializer = this.odata.createDeserializer(requestFormat);
        final DeserializerResult result = deserializer.entity(requestInputStream, edmEntityType);
        final Entity requestEntity = result.getEntity();
        //2.2 do the creation in the backend, which returns the newly created entity
        final Entity createdEntity = mCRUDHandler.createEntityData(edmEntitySet, requestEntity);//TODO handle ungültigkeit

        //3. serialize the response (we have to return the created entity)
        final ContextURL contextURL = ContextURL.with().entitySet(edmEntitySet).build();
        //expand and select currently not supported
        final EntitySerializerOptions options = EntitySerializerOptions.with().contextURL(contextURL).build();

        final ODataSerializer serializer = this.odata.createSerializer(responseFormat);
        final SerializerResult serializedResponse = serializer.entity(srvMetadata, edmEntityType, createdEntity, options);

        //4. configure the response object
        response.setContent(serializedResponse.getContent());
        response.setStatusCode(HttpStatusCode.CREATED.getStatusCode());
        response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());
    }

    //    public void updateEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType requestFormat, ContentType responseFormat)
    //        throws ODataApplicationException, DeserializerException, SerializerException {
    //        throw new ODataApplicationException("Not supported.", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
    //    }

    public void updateEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType requestFormat, ContentType responseFormat)
        throws ODataApplicationException, DeserializerException, SerializerException {
        //1. Retrieve the entity set which belongs to the requested entity
        final List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        //Note: only in our example we can assume that the first segment is the EntitySet
        final UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);
        final EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();
        final EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        // 2. update the data in backend
        // 2.1. retrieve the payload from the PUT request for the entity to be updated
        final InputStream requestInputStream = request.getBody();
        final ODataDeserializer deserializer = this.odata.createDeserializer(requestFormat);
        final DeserializerResult result = deserializer.entity(requestInputStream, edmEntityType);
        final Entity requestEntity = result.getEntity();
        // 2.2 do the modification in backend
        final List<UriParameter> keyPredicates = uriResourceEntitySet.getKeyPredicates();
        // Note that this updateEntity()-method is invoked for both PUT or PATCH operations
        final HttpMethod httpMethod = request.getMethod();
        mCRUDHandler.updateEntityData(edmEntitySet, keyPredicates, requestEntity, httpMethod);

        //3. configure the response object
        response.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    }

    //    public void deleteEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo) throws ODataApplicationException {
    //        throw new ODataApplicationException("Not supported.", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
    //    }

    public void deleteEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo) throws ODataApplicationException {
        // 1. Retrieve the entity set which belongs to the requested entity
        final List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        // Note: only in our example we can assume that the first segment is the EntitySet
        final UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);
        final EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

        // 2. delete the data in backend
        final List<UriParameter> keyPredicates = uriResourceEntitySet.getKeyPredicates();
        mCRUDHandler.deleteEntityData(edmEntitySet, keyPredicates);

        //3. configure the response object
        response.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    }
}
