package myservice.mynamespace.database.collections;

import myservice.mynamespace.database.data.enums.UnitOfCurrency;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import static myservice.mynamespace.service.entities.definitions.EntityNames.DB_ID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_CURRENCY;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_FLDATE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_PRICE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_SEATSMAX;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_SEATSMAX_B;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_SEATSMAX_F;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_SEATSOCC;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_SEATSOCC_B;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SFLIGHT_SEATSOCC_F;

/**
 *
 */
@Entity(value = SFLIGHT, noClassnameStored = true)
@Indexes({
    @Index(fields = @Field(DB_ID)),
    @Index(fields = @Field("scarr")),
    @Index(fields = @Field("spfli")),
    @Index(fields = @Field("saplane"))
})
public class Sflight {

    /** Represented in SAP's DATS-format (YYYYMMDD) */
    @Id
    @Property(SFLIGHT_FLDATE)
    private String flDate;//TODO verändern

    @Reference
    private Scarr scarr;

    @Reference
    private Spfli spfli;

    @Reference
    private Saplane saplane;

    @Property(SFLIGHT_PRICE)
    private double price;

    @Property(SFLIGHT_CURRENCY)
    private UnitOfCurrency currency;

    @Property(SFLIGHT_SEATSMAX)
    private int seatsMax;

    @Property(SFLIGHT_SEATSOCC)
    private int seatsOcc;

    @Property(SFLIGHT_SEATSMAX_B)
    private int seatsMaxB;

    @Property(SFLIGHT_SEATSOCC_B)
    private int seatsOccB;

    @Property(SFLIGHT_SEATSMAX_F)
    private int seatsMaxF;

    @Property(SFLIGHT_SEATSOCC_F)
    private int seatsOccF;

    public Sflight() {}

    public Sflight(String flDate, Scarr scarr, Spfli spfli, Saplane saplane, double price, UnitOfCurrency currency, int seatsMax, int seatsOcc, int seatsMaxB,
                   int seatsOccB, int seatsMaxF, int seatsOccF) {
        this.flDate = flDate;
        this.scarr = scarr;
        this.spfli = spfli;
        this.saplane = saplane;
        this.price = price;
        this.currency = currency;
        this.seatsMax = seatsMax;
        this.seatsOcc = seatsOcc;
        this.seatsMaxB = seatsMaxB;
        this.seatsOccB = seatsOccB;
        this.seatsMaxF = seatsMaxF;
        this.seatsOccF = seatsOccF;
    }

    public String getFlDate() {
        return flDate;
    }

    public void setFlDate(String flDate) {
        this.flDate = flDate;
    }

    public Scarr getScarr() {
        return scarr;
    }

    public void setScarr(Scarr scarr) {
        this.scarr = scarr;
    }

    public Spfli getSpfli() {
        return spfli;
    }

    public void setSpfli(Spfli spfli) {
        this.spfli = spfli;
    }

    public Saplane getSaplane() {
        return saplane;
    }

    public void setSaplane(Saplane saplane) {
        this.saplane = saplane;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UnitOfCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(UnitOfCurrency currency) {
        this.currency = currency;
    }

    public int getSeatsMax() {
        return seatsMax;
    }

    public void setSeatsMax(int seatsMax) {
        this.seatsMax = seatsMax;
    }

    public int getSeatsOcc() {
        return seatsOcc;
    }

    public void setSeatsOcc(int seatsOcc) {
        this.seatsOcc = seatsOcc;
    }

    public int getSeatsMaxB() {
        return seatsMaxB;
    }

    public void setSeatsMaxB(int seatsMaxB) {
        this.seatsMaxB = seatsMaxB;
    }

    public int getSeatsOccB() {
        return seatsOccB;
    }

    public void setSeatsOccB(int seatsOccB) {
        this.seatsOccB = seatsOccB;
    }

    public int getSeatsMaxF() {
        return seatsMaxF;
    }

    public void setSeatsMaxF(int seatsMaxF) {
        this.seatsMaxF = seatsMaxF;
    }

    public int getSeatsOccF() {
        return seatsOccF;
    }

    public void setSeatsOccF(int seatsOccF) {
        this.seatsOccF = seatsOccF;
    }
}
