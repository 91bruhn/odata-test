
package myservice.mynamespace.service;

import myservice.mynamespace.database.data.CRUDHandler;
import myservice.mynamespace.util.Util;
import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmNavigationProperty;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.UriResourceNavigation;

import java.util.List;
import java.util.Locale;

public class DemoEntityCollectionProcessor implements EntityCollectionProcessor {

    private OData odata;
    private ServiceMetadata srvMetadata;
    // our database-mock
    private CRUDHandler mCRUDHandler;//TODO nutze stattdessen eigene Implementierung

    public DemoEntityCollectionProcessor(CRUDHandler CRUDHandler) {
        this.mCRUDHandler = CRUDHandler;
    }

    public void init(OData odata, ServiceMetadata serviceMetadata) {
        /**This method is invoked by the Olingo library, allowing us to store the context object*/
        this.odata = odata;
        this.srvMetadata = serviceMetadata;
    }

    /*
     * This method is invoked when a collection of entities has to be read.
     * In our example, this can be either a "normal" read operation, or a navigation:
     *
     * Example for "normal" read entity set operation:
     * http://localhost:8080/DemoService/DemoService.svc/Categories
     *
     * Example for navigation
     * http://localhost:8080/DemoService/DemoService.svc/Categories(3)/Products
     */
    public void readEntityCollection(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType responseFormat)
        throws ODataApplicationException, SerializerException {
        /**Here we have to fetch the required data and pass it back to the Olingo library*/
        EdmEntitySet responseEdmEntitySet = null; // we'll need this to build the ContextURL
        EntityCollection responseEntityCollection = null; // we'll need this to set the response body

        // 1st retrieve the requested EntitySet from the uriInfo (representation of the parsed URI)
        final List<UriResource> resourceParts = uriInfo.getUriResourceParts();
        final int segmentCount = resourceParts.size();
        //the first segment represents here the EntitySet
        final UriResource uriResource = resourceParts.get(0);

        if (!(uriResource instanceof UriResourceEntitySet)) {
            throw new ODataApplicationException("Only EntitySet is supported", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
        }

        final UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) uriResource;
        final EdmEntitySet startEdmEntitySet = uriResourceEntitySet.getEntitySet();

        if (segmentCount == 1) {
            // this is the case for: DemoService/DemoService.svc/Categories TODO delete
            responseEdmEntitySet = startEdmEntitySet; // the response body is built from the first (and only) entitySet

            // 2nd: fetch the data from backend for this requested EntitySetName and deliver as EntitySet
            responseEntityCollection = mCRUDHandler.readEntitySetData(startEdmEntitySet);
        } else if (segmentCount == 2) { // in case of navigation: DemoService.svc/Categories(3)/Products
            // in our example we don't support more complex URIs
            final UriResource lastSegment = resourceParts.get(1);

            if (lastSegment instanceof UriResourceNavigation) {
                final UriResourceNavigation uriResourceNavigation = (UriResourceNavigation) lastSegment;
                final EdmNavigationProperty edmNavigationProperty = uriResourceNavigation.getProperty();
                final EdmEntityType targetEntityType = edmNavigationProperty.getType();
                // from Categories(1) to Products
                responseEdmEntitySet = Util.getNavigationTargetEntitySet(startEdmEntitySet, edmNavigationProperty);

                // 2nd: fetch the data from backend
                // first fetch the entity where the first segment of the URI points to
                final List<UriParameter> keyPredicates = uriResourceEntitySet.getKeyPredicates();
                // e.g. for Categories(3)/Products we have to find the single entity: Category with ID 3
                //wenn man über navigation geht und erstmal alle carriers raussucht, den gesuchten nimmt und dazu alle flüge sucht.
                final Entity sourceEntity = mCRUDHandler.readEntityData(startEdmEntitySet, keyPredicates);
                // error handling for e.g. DemoService.svc/Categories(99)/Products
                if (sourceEntity == null) {
                    throw new ODataApplicationException("Entity not found.", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ROOT);
                }
                // then fetch the entity collection where the entity navigates to
                // note: we don't need to check uriResourceNavigation.isCollection(),
                // because we are the EntityCollectionProcessor
                responseEntityCollection = mCRUDHandler.getRelatedEntityCollection(sourceEntity, targetEntityType);

            }
        } else { // this would be the case for e.g. Products(1)/Category/Products
            throw new ODataApplicationException("Not supported", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
        }

        // 3rd: create and configure a serializer
        //TODO responseEdmEntitySet might be null
        final ContextURL contextUrl = ContextURL.with().entitySet(responseEdmEntitySet).build();
        final String id = request.getRawBaseUri() + "/" + responseEdmEntitySet.getName();
        final EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions.with().contextURL(contextUrl).id(id).build();
        final EdmEntityType edmEntityType = responseEdmEntitySet.getEntityType();

        final ODataSerializer serializer = odata.createSerializer(responseFormat);
        final SerializerResult serializerResult = serializer.entityCollection(this.srvMetadata, edmEntityType, responseEntityCollection, opts);

        // 4th: configure the response object: set the body, headers and status code
        response.setContent(serializerResult.getContent());
        response.setStatusCode(HttpStatusCode.OK.getStatusCode());
        response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());
    }

}
