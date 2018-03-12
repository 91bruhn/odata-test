////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service.tmp2;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 */
public class AbstractEntityService {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------



    //create id, incrementing...
    //TODO vorübergehend auskommentiert
    //    private boolean productIdExists(int id) {
    //        for (Entity entity : this.productList) {
    //            Integer existingId = (Integer) entity.getProperty("ID").getValue();
    //            if (existingId.intValue() == id) {
    //                return true;
    //            }
    //        }
    //        return false;

    //    }

      /* INTERNAL */

    //    private String id(Property idProperty){
    //        if (idProperty != null) {
    //            final String planeType = (String) idProperty.getValue();
    //
    //            if (idTaken(Saplane.class, planeType)) {
    //                //LOG plane already defined in db
    //                return null;
    //            } else {
    //                id = planeType;
    //            }
    //            idProperty.setValue(ValueType.PRIMITIVE, id);//TODO was macht das?
    //        } else {
    //            return null;
    //        }
    //    }

    //
    //    /* HELPER */
    //    private URI createId(Entity entity, String idPropertyName) {
    //        return createId(entity, idPropertyName, null);
    //    }
    //
    //        private URI createId(Entity entity, String idPropertyName, String navigationName) {
    //            try {
    //                StringBuilder sb = new StringBuilder(getEntitySetName(entity)).append("(");
    //                final Property property = entity.getProperty(idPropertyName);
    //                sb.append(property.asPrimitive()).append(")");
    //                if (navigationName != null) {
    //                    sb.append("/").append(navigationName);
    //                }
    //                return new URI(sb.toString());
    //            } catch (URISyntaxException e) {
    //                throw new ODataRuntimeException("Unable to create (Atom) id for entity: " + entity, e);
    //            }
    //        }

    //    private String getEntitySetName(Entity entity) {
    //        if (FlightDataEdmProvider.ET_SPFLI_FQN.getFullQualifiedNameAsString().equals(entity.getType())) {
    //            return FlightDataEdmProvider.ES_SPFLI_NAME;
    //        } else if (FlightDataEdmProvider.ET_SFLIGHT_FQN.getFullQualifiedNameAsString().equals(entity.getType())) {
    //            return FlightDataEdmProvider.ES_SFLIGHT_NAME;
    //        }
    //        return entity.getType();
    //    }

}
