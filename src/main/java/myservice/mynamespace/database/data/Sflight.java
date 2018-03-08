package myservice.mynamespace.database.data;

import myservice.mynamespace.database.data.enums.Currency;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;

import static myservice.mynamespace.util.EntityNames.SFLIGHT;
import static myservice.mynamespace.util.EntityNames.SFLIGHT_CURRENCY;
import static myservice.mynamespace.util.EntityNames.SFLIGHT_FLDATE;
import static myservice.mynamespace.util.EntityNames.SFLIGHT_PRICE;
import static myservice.mynamespace.util.EntityNames.SFLIGHT_SEATSMAX;
import static myservice.mynamespace.util.EntityNames.SFLIGHT_SEATSMAX_B;
import static myservice.mynamespace.util.EntityNames.SFLIGHT_SEATSMAX_F;
import static myservice.mynamespace.util.EntityNames.SFLIGHT_SEATSOCC;
import static myservice.mynamespace.util.EntityNames.SFLIGHT_SEATSOCC_B;
import static myservice.mynamespace.util.EntityNames.SFLIGHT_SEATSOCC_F;

/**
 *
 */
@Entity(value = SFLIGHT, noClassnameStored = true)
public class Sflight {//todo compound indexes um schneller zu sein zb. monat, jahr, ...land, country

    @Id
    @Property(SFLIGHT_FLDATE)
    private Date flDate;

    @Reference//todo can also be loaded when this is loaded, idOnly() parameter to just save the key value
    //    @Property(SFLIGHT_CARRID)
    private Scarr carrId;

    @Reference
    //    @Property(SFLIGHT_CONNID)
    private Spfli connId;

    @Reference
    //    @Property(SFLIGHT_PLANETYPE)
    private Saplane planeType;

    @Property(SFLIGHT_PRICE)
    private double price;

    @Property(SFLIGHT_CURRENCY)
    private Currency currency;

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

    public Sflight(Date flDate, Scarr carrId, Spfli connId, Saplane planeType, double price, Currency currency, int seatsMax, int seatsOcc, int seatsMaxB,
                   int seatsOccB, int seatsMaxF, int seatsOccF) {
        this.flDate = flDate;
        this.carrId = carrId;
        this.connId = connId;
        this.planeType = planeType;
        this.price = price;
        this.currency = currency;
        this.seatsMax = seatsMax;
        this.seatsOcc = seatsOcc;
        this.seatsMaxB = seatsMaxB;
        this.seatsOccB = seatsOccB;
        this.seatsMaxF = seatsMaxF;
        this.seatsOccF = seatsOccF;
    }

    public Date getFlDate() {
        return flDate;
    }

    public void setFlDate(Date flDate) {
        this.flDate = flDate;
    }

    public Scarr getCarrId() {
        return carrId;
    }

    public void setCarrId(Scarr carrId) {
        this.carrId = carrId;
    }

    public Spfli getConnId() {
        return connId;
    }

    public void setConnId(Spfli connId) {
        this.connId = connId;
    }

    public Saplane getPlaneType() {
        return planeType;
    }

    public void setPlaneType(Saplane planeType) {
        this.planeType = planeType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
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
