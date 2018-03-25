package myservice.mynamespace.database.collections;

import myservice.mynamespace.database.data.enums.UnitOfCurrency;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;

import static myservice.mynamespace.service.entities.definitions.EntityNames.DB_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SCARR;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SCARR_CARRID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SCARR_CARRNAME;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SCARR_CURRCODE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SCARR_URL;

/**
 *
 */
@Entity(value = SCARR, noClassnameStored = true)
@Indexes({ @Index(fields = @Field(DB_ID)) })
public class Scarr {

    @Id
    @Property(SCARR_CARRID)
    private String carrId;

    @Property(SCARR_CARRNAME)
    private String carrName;

    @Property(SCARR_CURRCODE)
    private UnitOfCurrency currCode;

    @Property(SCARR_URL)
    private String url;

    public Scarr() {}

    public Scarr(String carrId, String carrName, UnitOfCurrency currCode, String url) {
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

    public UnitOfCurrency getCurrCode() {
        return currCode;
    }

    public void setCurrCode(UnitOfCurrency currCode) {
        this.currCode = currCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
