package myservice.mynamespace.database.service.crud;

import myservice.mynamespace.database.service.tmp2.EntityBookingService;
import myservice.mynamespace.database.service.tmp2.EntityCarrierService;
import myservice.mynamespace.database.service.tmp2.EntityConnectionService;
import myservice.mynamespace.database.service.tmp2.EntityFlightService;
import myservice.mynamespace.database.service.tmp2.EntityPlaneService;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.http.HttpMethod;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

import java.util.List;

import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SAPLANE_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SBOOK_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SCARR_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SFLIGHT_NAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.ES_SPFLI_NAME;

/**
 *
 */
public class CRUDHandler {//Service? DB Service Handler???

    private EntityFlightService mEntityFlightService;
    private EntityBookingService mEntityBookingService;
    private EntityConnectionService mEntityConnectionService;
    private EntityPlaneService mEntityPlaneService;
    private EntityCarrierService mEntityCarrierService;

    public CRUDHandler() {
        mEntityFlightService = new EntityFlightService();
        mEntityBookingService = new EntityBookingService();
        mEntityConnectionService = new EntityConnectionService();
        mEntityPlaneService = new EntityPlaneService();
        mEntityCarrierService = new EntityCarrierService();

    }

    public EntityCollection readEntitySetData(EdmEntitySet edmEntitySet) {//TODO USE ABSTRACT CRUD CLASS

        switch (edmEntitySet.getName()) {
            case ES_SFLIGHT_NAME:
                return mEntityFlightService.getFlights();
            case ES_SPFLI_NAME:
                return mEntityConnectionService.getConnections();
            case ES_SCARR_NAME:
                return mEntityCarrierService.getCarriers();
            case ES_SBOOK_NAME:
                return mEntityBookingService.getBookings();
            case ES_SAPLANE_NAME:
                return mEntityPlaneService.getPlanes();
            default:
                return null;
        }
    }

    //TODO überall throws? checke andere IMPL.
    public Entity readEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams) throws ODataApplicationException {
        final EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        switch (edmEntitySet.getName()) {
            case ES_SFLIGHT_NAME:
                return mEntityFlightService.getFlight(edmEntityType, keyParams);
            case ES_SPFLI_NAME:
                return mEntityConnectionService.getConnection(edmEntityType, keyParams);
            case ES_SCARR_NAME:
                return mEntityCarrierService.getCarrier(edmEntityType, keyParams);
            case ES_SBOOK_NAME:
                return mEntityBookingService.getBooking(edmEntityType, keyParams);
            case ES_SAPLANE_NAME:
                return mEntityPlaneService.getPlane(edmEntityType, keyParams);
            default:
                return null;
        }
    }

    public Entity createEntityData(EdmEntitySet edmEntitySet, Entity entityToCreate) {
        final EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        switch (edmEntitySet.getName()) {
            case ES_SFLIGHT_NAME:
                return mEntityFlightService.createFlight(edmEntityType, entityToCreate);
            case ES_SPFLI_NAME:
                return mEntityConnectionService.createConnection(edmEntityType, entityToCreate);
            case ES_SCARR_NAME:
                return mEntityCarrierService.createCarrier(edmEntityType, entityToCreate);
            case ES_SBOOK_NAME:
                return mEntityBookingService.createBooking(edmEntityType, entityToCreate);
            case ES_SAPLANE_NAME:
                return mEntityPlaneService.createPlane(edmEntityType, entityToCreate);
            default:
                return null;
        }
    }

    public void updateEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams, Entity updateEntity, HttpMethod httpMethod)
        throws ODataApplicationException {
        final EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        switch (edmEntitySet.getName()) {
            case ES_SFLIGHT_NAME:
                mEntityFlightService.updateFlight(edmEntityType, keyParams, updateEntity, httpMethod);
                return;
            case ES_SPFLI_NAME:
                mEntityConnectionService.updateConnection(edmEntityType, keyParams, updateEntity, httpMethod);
                return;
            case ES_SCARR_NAME:
                mEntityCarrierService.updateCarrier(edmEntityType, keyParams, updateEntity, httpMethod);
                return;
            case ES_SBOOK_NAME:
                mEntityBookingService.updateBooking(edmEntityType, keyParams, updateEntity, httpMethod);
                return;
            case ES_SAPLANE_NAME:
                mEntityPlaneService.updatePlane(edmEntityType, keyParams, updateEntity, httpMethod);
        }
    }

    public void deleteEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams) throws ODataApplicationException {
        final EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        switch (edmEntitySet.getName()) {
            case ES_SFLIGHT_NAME:
                mEntityFlightService.deleteFlight(edmEntityType, keyParams);
                return;
            case ES_SPFLI_NAME:
                mEntityConnectionService.deleteConnection(edmEntityType, keyParams);
                return;
            case ES_SCARR_NAME:
                mEntityCarrierService.deleteCarrier(edmEntityType, keyParams);
                return;
            case ES_SBOOK_NAME:
                mEntityBookingService.deleteBooking(edmEntityType, keyParams);
                return;
            case ES_SAPLANE_NAME:
                mEntityPlaneService.deletePlane(edmEntityType, keyParams);
        }
    }

}
