package myservice.mynamespace.database.collections;

import myservice.mynamespace.database.data.enums.UnitOfMass;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;

import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_BOOKID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_CANCELLED;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_CLASS;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_CUSTOMID;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_CUSTTYPE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_INVOICE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_LUGGWEIGHT;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_ORDER_DATE;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_RESERVED;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_SMOKER;
import static myservice.mynamespace.service.entities.definitions.EntityNames.SBOOK_WUNIT;

/**
 *
 */
@Entity(value = SBOOK, noClassnameStored = true)
public class Sbook {

    @Id
    @Property(SBOOK_BOOKID)
    private String bookId;

    @Reference
    //    @Property(SBOOK_CARRID)
    private Scarr carrId;

    @Reference
    //    @Property(SBOOK_CONNID)//TODO lazy = true
    private Spfli connId;

    @Reference
    //    @Property(SBOOK_FLDATE)
    private Sflight flDate;

    @Property(SBOOK_CUSTOMID)
    private String customId;

    @Property(SBOOK_CUSTTYPE)
    private Character custType;

    @Property(SBOOK_SMOKER)
    private Character smoker;

    @Property(SBOOK_LUGGWEIGHT)
    private double luggWeight;

    @Property(SBOOK_WUNIT)
    private UnitOfMass wUnit;

    @Property(SBOOK_INVOICE)
    private boolean invoice;

    @Property(SBOOK_CLASS)
    private Character flightClass;

    @Property(SBOOK_ORDER_DATE)
    private Date orderDate;

    @Property(SBOOK_CANCELLED)
    private boolean cancelled;

    @Property(SBOOK_RESERVED)
    private boolean reserved;

    public Sbook() {
    }

    public Sbook(String bookId, Scarr carrId, Spfli connId, Sflight flDate, String customId, Character custType, Character smoker, double luggWeight,
                 UnitOfMass wUnit, boolean invoice, Character flightClass, Date orderDate, boolean cancelled, boolean reserved) {
        this.bookId = bookId;
        this.carrId = carrId;
        this.connId = connId;
        this.flDate = flDate;
        this.customId = customId;
        this.custType = custType;
        this.smoker = smoker;
        this.luggWeight = luggWeight;
        this.wUnit = wUnit;
        this.invoice = invoice;
        this.flightClass = flightClass;
        this.orderDate = orderDate;
        this.cancelled = cancelled;
        this.reserved = reserved;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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

    public Sflight getFlDate() {
        return flDate;
    }

    public void setFlDate(Sflight flDate) {
        this.flDate = flDate;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getCustType() {
        return custType == 'M' ? "Male" : "Female";//TODO könnte hier crashen
    }

    public void setCustType(Character custType) {
        this.custType = custType;
    }

    public boolean isSmoker() {//TODO überall wo boolean für eig Character steht ändern
        return smoker == 'Y';
    }

    public void setSmoker(Character smoker) {
        this.smoker = smoker;
    }

    public double getLuggWeight() {
        return luggWeight;
    }

    public void setLuggWeight(double luggWeight) {
        this.luggWeight = luggWeight;
    }

    public UnitOfMass getwUnit() {
        return wUnit;
    }

    public void setwUnit(UnitOfMass wUnit) {
        this.wUnit = wUnit;
    }

    public boolean hasInvoice() {
        return invoice;
    }

    public void setInvoice(Character invoice) {
        this.invoice = 'Y' == invoice;
    }

    public String getFlightClass() {
        switch (flightClass) {
            case 'E':
                return "Economy";
            case 'B':
                return "Business";
            case 'F':
                return "First Class";
            default:
                return "N/A";
        }
    }

    public void setFlightClass(Character flightClass) {
        this.flightClass = flightClass;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(Character cancelled) {
        this.cancelled = 'Y' == cancelled;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(Character reserved) {
        this.reserved = 'Y' == reserved;
    }
}
