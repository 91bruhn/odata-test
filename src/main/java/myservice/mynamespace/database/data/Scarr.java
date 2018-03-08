package myservice.mynamespace.database.data;

import myservice.mynamespace.database.data.enums.Currency;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import static myservice.mynamespace.util.EntityNames.SCARR;
import static myservice.mynamespace.util.EntityNames.SCARR_CARRID;
import static myservice.mynamespace.util.EntityNames.SCARR_CARRNAME;
import static myservice.mynamespace.util.EntityNames.SCARR_CURRCODE;
import static myservice.mynamespace.util.EntityNames.SCARR_URL;

/**
 *
 */
@Entity(value = SCARR, noClassnameStored = true)
public class Scarr {

    @Id
    @Property(SCARR_CARRID)
    private String carrId;

    @Property(SCARR_CARRNAME)
    private String carrName;

    @Property(SCARR_CURRCODE)
    private Currency currCode;

    @Property(SCARR_URL)
    private String url;

    public Scarr() {
    }

    public Scarr(String carrId, String carrName, Currency currCode, String url) {
        this.carrId = carrId;
        this.carrName = carrName;
        this.currCode = currCode;
        this.url = url;
    }

    public String getCarrId() {
        return carrId;
    }

    public void setCarrId(String carrId) {
        this.carrId = carrId;
    }

    public String getCarrName() {
        return carrName;
    }

    public void setCarrName(String carrName) {
        this.carrName = carrName;
    }

    public Currency getCurrCode() {
        return currCode;
    }

    public void setCurrCode(Currency currCode) {
        this.currCode = currCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
