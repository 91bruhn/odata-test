package myservice.mynamespace.util;

import myservice.mynamespace.database.collections.Saplane;
import myservice.mynamespace.database.collections.Scarr;
import myservice.mynamespace.database.collections.Sflight;
import myservice.mynamespace.database.collections.Spfli;
import myservice.mynamespace.database.service.tmp.IDBService;
import myservice.mynamespace.database.service.tmp.SaplaneService;
import myservice.mynamespace.database.service.tmp.ScarrService;
import myservice.mynamespace.database.service.tmp.SflightService;
import myservice.mynamespace.database.service.tmp.SpfliService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmBindingTarget;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmNavigationProperty;
import org.apache.olingo.commons.api.edm.EdmPrimitiveType;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeException;
import org.apache.olingo.commons.api.edm.EdmProperty;
import org.apache.olingo.commons.api.edm.EdmType;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriInfoResource;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class Util {

    public static String generateRandomId(int length, boolean useLetters, boolean useNumbers) {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static URI createId(String entitySetName, Object id) {
        try {
            return new URI(entitySetName + "(" + String.valueOf(id) + ")");
        } catch (URISyntaxException e) {
            throw new ODataRuntimeException("Unable to create id for entity: " + entitySetName, e);
        }
    }

    public static Scarr loadAssociatedCarrier(String carrierId) {
        return new ScarrService().getById(carrierId);
    }

    public static Spfli loadAssociatedConnection(String connectionId) {
        return new SpfliService().getById(connectionId);
    }

    public static Saplane loadAssociatedPlane(String planeType) {
        return new SaplaneService().getById(planeType);
    }

    public static Sflight loadAssociatedFlight(String flDate) {
        return new SflightService().getById(flDate);
    }

    public static boolean idTaken(String idToCheckIfTaken, IDBService idbService) {
        return !StringUtils.isEmpty(idToCheckIfTaken) && idbService.idTaken(idToCheckIfTaken);
    }

    //checks first if the given id is not taken, if so a new one will be created and returned
    public static String generateId(String idToCheckIfTaken, int length, boolean useLetters, boolean useNumbers, IDBService idbService) {
        while (Util.idTaken(idToCheckIfTaken, idbService)) {
            idToCheckIfTaken = Util.generateRandomId(length, useLetters, useNumbers);
        }

        return idToCheckIfTaken;
    }

    public static EdmEntitySet getEdmEntitySet(UriInfoResource uriInfo) throws ODataApplicationException {
        List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        //To get the entity set we have to interpret all URI segments
        if (!(resourcePaths.get(0) instanceof UriResourceEntitySet)) {
            throw new ODataApplicationException("Invalid resource type for first segment.", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ENGLISH);
        }

        final UriResourceEntitySet uriResource = (UriResourceEntitySet) resourcePaths.get(0);
        return uriResource.getEntitySet();
    }

    public static Entity findEntity(EdmEntityType edmEntityType, EntityCollection rt_entitySet, List<UriParameter> keyParams) throws ODataApplicationException {

        List<Entity> entityList = rt_entitySet.getEntities();

        // loop over all entities in order to find that one that matches
        // all keys in request e.g. contacts(ContactID=1, CompanyID=1)
        for (Entity entity : entityList) {
            final boolean foundEntity = entityMatchesAllKeys(edmEntityType, entity, keyParams);
            if (foundEntity) {
                return entity;
            }
        }

        return null;
    }

    //TODO delete this method?
    public static boolean entityMatchesAllKeys(EdmEntityType edmEntityType, Entity rt_entity, List<UriParameter> keyParams) throws ODataApplicationException {

        // loop over all keys
        for (final UriParameter key : keyParams) {
            // key
            final String keyName = key.getName();
            String keyText = key.getText();
            final String str = "'";
            if (keyText.contains(str)) {
                keyText = keyText.replace(str, StringUtils.EMPTY);
            }

            // note: below line doesn't consider: keyProp can be part of a complexType in V4
            // in such case, it would be required to access it via getKeyPropertyRef()
            // but since this isn't the case in our model, we ignore it in our implementation
            final EdmProperty edmKeyProperty = (EdmProperty) edmEntityType.getProperty(keyName);
            //            EdmKeyPropertyRef edmKeyProperty22 = edmEntityType.getKeyPropertyRef(keyName);
            //            List<EdmKeyPropertyRef> edmKeyProperty3 = edmEntityType.getKeyPropertyRefs();
            //            List<String> edmKeyPropert4 = edmEntityType.getKeyPredicateNames();
            // Edm: we need this info for the comparison below
            final Boolean isNullable = edmKeyProperty.isNullable();
            final Integer maxLength = edmKeyProperty.getMaxLength();
            final Integer precision = edmKeyProperty.getPrecision();
            final Boolean isUnicode = edmKeyProperty.isUnicode();
            final Integer scale = edmKeyProperty.getScale();
            // get the EdmType in order to compare
            final EdmType edmType = edmKeyProperty.getType();
            // if(EdmType instanceof EdmPrimitiveType) // do we need this?
            final EdmPrimitiveType edmPrimitiveType = (EdmPrimitiveType) edmType;

            // Runtime data: the value of the current entity
            // don't need to check for null, this is done in FWK
            final Object valueObject = rt_entity.getProperty(keyName).getValue();//null-check
            // TODO if the property is a complex type

            // now need to compare the valueObject with the keyText String
            // this is done using the type.valueToString
            final String valueAsString;
            try {
                valueAsString = edmPrimitiveType.valueToString(valueObject, isNullable, maxLength, precision, scale, isUnicode);
            } catch (EdmPrimitiveTypeException e) {
                throw new ODataApplicationException("Failed to retrieve String value", HttpStatusCode.INTERNAL_SERVER_ERROR.getStatusCode(), Locale.ENGLISH, e);
            }

            if (valueAsString == null) {
                return false;
            }

            final boolean matches = valueAsString.equals(keyText);
            if (!matches) {//TODO lesen und löschen/umbauen
                // if any of the key properties is not found in the entity, we don't need to search further
                return false;
            }
        }

        return true;
    }

    /**
     * Example:
     * For the following navigation: DemoService.svc/Categories(1)/Products
     * we need the EdmEntitySet for the navigation property "Products"
     * <p>
     * This is defined as follows in the metadata:
     * <code>
     * <p>
     * <EntitySet Name="Categories" EntityType="OData.Demo.Category">
     * <NavigationPropertyBinding Path="Products" Target="Products"/>
     * </EntitySet>
     * </code>
     * The "Target" attribute specifies the target EntitySet
     * Therefore we need the startEntitySet "Categories" in order to retrieve the target EntitySet "Products"
     */
    public static EdmEntitySet getNavigationTargetEntitySet(EdmEntitySet startEdmEntitySet, EdmNavigationProperty edmNavigationProperty)
        throws ODataApplicationException {

        final EdmEntitySet navigationTargetEntitySet;
        final String navPropName = edmNavigationProperty.getName();
        final EdmBindingTarget edmBindingTarget = startEdmEntitySet.getRelatedBindingTarget(navPropName);

        if (edmBindingTarget == null) {
            throw new ODataApplicationException("Not supported.", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
        }

        if (edmBindingTarget instanceof EdmEntitySet) {
            navigationTargetEntitySet = (EdmEntitySet) edmBindingTarget;
        } else {
            throw new ODataApplicationException("Not supported.", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
        }

        return navigationTargetEntitySet;
    }

}
