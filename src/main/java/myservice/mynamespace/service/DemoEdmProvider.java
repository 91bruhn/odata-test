
package myservice.mynamespace.service;

import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;

import java.util.ArrayList;
import java.util.List;

import static myservice.mynamespace.util.EntityNames.CONTAINER;
import static myservice.mynamespace.util.EntityNames.CONTAINER_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SAPLANE_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SBOOK_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SCARR_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SFLIGHT_NAME;
import static myservice.mynamespace.util.EntityNames.ES_SPFLI_NAME;
import static myservice.mynamespace.util.EntityNames.ET_SAPLANE_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SBOOK_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SCARR_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SFLIGHT_FQN;
import static myservice.mynamespace.util.EntityNames.ET_SPFLI_FQN;
import static myservice.mynamespace.util.EntityNames.NAMESPACE;
import static myservice.mynamespace.util.EntitySets.getBookingEntitySet;
import static myservice.mynamespace.util.EntitySets.getCarrierEntitySet;
import static myservice.mynamespace.util.EntitySets.getConnectionsEntitySet;
import static myservice.mynamespace.util.EntitySets.getFlightsEntitySet;
import static myservice.mynamespace.util.EntitySets.getPlaneEntitySet;
import static myservice.mynamespace.util.EntityTypes.getBookingEntityType;
import static myservice.mynamespace.util.EntityTypes.getCarrierEntityType;
import static myservice.mynamespace.util.EntityTypes.getConnectionEntityType;
import static myservice.mynamespace.util.EntityTypes.getFlightEntityType;
import static myservice.mynamespace.util.EntityTypes.getPlaneEntityType;

/**
 * The base class CsdlAbstractEdmProvider provides methods for declaring the metadata of all OData elements.
 * For example: The entries that are displayed in the Service Document are provided by the method getEntityContainerInfo() The structure of EntityTypes is declared in the method getEntityType()
 * <p>
 * In our simple example, we implement the minimum amount of methods, required to run a meaningful OData service. These are:
 * <p>
 * getEntityType() Here we declare the EntityType “Product” and a few of its properties
 * getEntitySet() Here we state that the list of products can be called via the EntitySet “Products”
 * getEntityContainer() Here we provide a Container element that is necessary to host the EntitySet.
 * getSchemas() The Schema is the root element to carry the elements.
 * getEntityContainerInfo() Information about the EntityContainer to be displayed in the Service Document
 * <p>
 * Summary: We have created a class that declares the metadata of our OData service. We have declared the main elements of an OData service: EntityType, EntitySet, EntityContainer and Schema (with the corresponding Olingo classes CsdlEntityType, CsdlEntitySet, CsdlEntityContainer and CsdlSchema).
 * <p>
 * At runtime of an OData service, such metadata can be viewed by invoking the Metadata Document.
 */
public class DemoEdmProvider extends CsdlAbstractEdmProvider {

    @Override
    public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) {
        // this method is called for each EntityType that are configured in the Schema
        CsdlEntityType entityType = null;

        if (entityTypeName.equals(ET_SFLIGHT_FQN)) {
            entityType = getFlightEntityType();
        } else if (entityTypeName.equals(ET_SPFLI_FQN)) {
            entityType = getConnectionEntityType();
        } else if (entityTypeName.equals(ET_SCARR_FQN)) {
            entityType = getCarrierEntityType();
        } else if (entityTypeName.equals(ET_SBOOK_FQN)) {
            entityType = getBookingEntityType();
        } else if (entityTypeName.equals(ET_SAPLANE_FQN)) {
            entityType = getPlaneEntityType();
        }

        return entityType;
    }

    @Override
    public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) {
        CsdlEntitySet entitySet = null;

        if (entityContainer.equals(CONTAINER)) {
            switch (entitySetName) {
                case ES_SFLIGHT_NAME:
                    entitySet = getFlightsEntitySet();
                    break;
                case ES_SPFLI_NAME:
                    entitySet = getConnectionsEntitySet();
                    break;
                case ES_SCARR_NAME:
                    entitySet = getCarrierEntitySet();
                    break;
                case ES_SBOOK_NAME:
                    entitySet = getBookingEntitySet();
                    break;
                case ES_SAPLANE_NAME:
                    entitySet = getPlaneEntitySet();
                    break;
            }
        }

        return entitySet;
    }

    @Override
    public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) {
        // This method is invoked when displaying the service document at
        // e.g. http://localhost:8080/DemoService/DemoService.svc
        if (entityContainerName == null || entityContainerName.equals(CONTAINER)) {
            final CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
            entityContainerInfo.setContainerName(CONTAINER);
            return entityContainerInfo;
        }

        return null;
    }

    @Override
    public List<CsdlSchema> getSchemas() {
        // create Schema
        final CsdlSchema schema = new CsdlSchema();
        schema.setNamespace(NAMESPACE);

        // add EntityTypes
        final List<CsdlEntityType> entityTypes = new ArrayList<>();
        entityTypes.add(getEntityType(ET_SFLIGHT_FQN));
        entityTypes.add(getEntityType(ET_SPFLI_FQN));
        entityTypes.add(getEntityType(ET_SAPLANE_FQN));
        entityTypes.add(getEntityType(ET_SBOOK_FQN));
        entityTypes.add(getEntityType(ET_SCARR_FQN));
        schema.setEntityTypes(entityTypes);

        // add EntityContainer
        schema.setEntityContainer(getEntityContainer());

        // finally
        final  List<CsdlSchema> schemas = new ArrayList<>();
        schemas.add(schema);

        return schemas;
    }

    @Override
    public CsdlEntityContainer getEntityContainer() {
        // create EntitySets
        final List<CsdlEntitySet> entitySets = new ArrayList<>();
        entitySets.add(getEntitySet(CONTAINER, ES_SFLIGHT_NAME));
        entitySets.add(getEntitySet(CONTAINER, ES_SPFLI_NAME));
        entitySets.add(getEntitySet(CONTAINER, ES_SAPLANE_NAME));
        entitySets.add(getEntitySet(CONTAINER, ES_SBOOK_NAME));
        entitySets.add(getEntitySet(CONTAINER, ES_SCARR_NAME));

        // create EntityContainer
        final CsdlEntityContainer entityContainer = new CsdlEntityContainer();
        entityContainer.setName(CONTAINER_NAME);
        entityContainer.setEntitySets(entitySets);

        return entityContainer;
    }
}