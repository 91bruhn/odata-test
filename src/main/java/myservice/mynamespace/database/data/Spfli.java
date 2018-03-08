package myservice.mynamespace.database.data;

import myservice.mynamespace.database.data.enums.UnitOfLength;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import java.time.LocalTime;

import static myservice.mynamespace.util.EntityNames.SPFLI;
import static myservice.mynamespace.util.EntityNames.SPFLI_AIRPFROM;
import static myservice.mynamespace.util.EntityNames.SPFLI_AIRPTO;
import static myservice.mynamespace.util.EntityNames.SPFLI_ARRTIME;
import static myservice.mynamespace.util.EntityNames.SPFLI_CITYFROM;
import static myservice.mynamespace.util.EntityNames.SPFLI_CITYTO;
import static myservice.mynamespace.util.EntityNames.SPFLI_CONNID;
import static myservice.mynamespace.util.EntityNames.SPFLI_COUNTRYFR;
import static myservice.mynamespace.util.EntityNames.SPFLI_COUNTRYTO;
import static myservice.mynamespace.util.EntityNames.SPFLI_DEPTIME;
import static myservice.mynamespace.util.EntityNames.SPFLI_DISTANCE;
import static myservice.mynamespace.util.EntityNames.SPFLI_DISTID;
import static myservice.mynamespace.util.EntityNames.SPFLI_FLTIME;
import static myservice.mynamespace.util.EntityNames.SPFLI_FLTYPE;
import static myservice.mynamespace.util.EntityNames.SPFLI_PERIOD;

/**
 *
 */
@Entity(value = SPFLI, noClassnameStored = true)
public class Spfli {

    // ------------------------------------------------------------------------
    // constants
    // ------------------------------------------------------------------------
    /**
     * Serialization-Id.
     */
    private static final long serialVersionUID = 5226388856370727508L;

    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    @Id
    @Property(SPFLI_CONNID)
    private String connId;

    @Reference
    //    @Property(CARRID)
    private Scarr carrId;

    @Property(SPFLI_AIRPFROM)
    private String airpFrom;

    @Property(SPFLI_AIRPTO)
    private String airpTo;

    @Property(SPFLI_CITYFROM)
    private String cityFrom;

    @Property(SPFLI_CITYTO)
    private String cityTo;

    @Property(SPFLI_COUNTRYFR)
    private String countryFrom;

    @Property(SPFLI_COUNTRYTO)
    private String countryTo;

    @Property(SPFLI_FLTIME)
    private int flTime;

    @Property(SPFLI_DEPTIME)
    private String depTime;//Time field (hhmmss)

    @Property(SPFLI_ARRTIME)
    private String arrTime;//Time field (hhmmss)

    @Property(SPFLI_DISTANCE)
    private double distance;

    @Property(SPFLI_DISTID)
    private UnitOfLength distId;

    @Property(SPFLI_FLTYPE)
    private String flType;//boolean?

    @Property(SPFLI_PERIOD)
    private int period;

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    /**
     * Default constructor.
     */
    public Spfli() {}

    public Spfli(String connId, Scarr carrId, String airpFrom, String airpTo, String cityFrom, String cityTo, String countryFrom, String countryTo, int flTime,
                 String depTime, String arrTime, double distance, UnitOfLength distId, String flType, int period) {
        this.connId = connId;
        this.carrId = carrId;
        this.airpFrom = airpFrom;
        this.airpTo = airpTo;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.countryFrom = countryFrom;
        this.countryTo = countryTo;
        this.flTime = flTime;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.distance = distance;
        this.distId = distId;
        this.flType = flType;
        this.period = period;
    }

    //    private String convertToString(BSONObject bsonObject, String value) {//TODO replace?? oder hier immer verwenden - machts safer to use
    //        return (String) bsonObject.get(value);
    //    }
    //
    //    /**
    //     * Validates a String that is supposed to be in SAP's {@code TIMS}-format.
    //     * <p>
    //     * Note: If the value for seconds is invalid, the actual time is still considered valid.
    //     * Therefore, seconds may be a 0 in that case.
    //     *
    //     * @return Returns String as valid {@code TIMS} time format or an empty String if the parsing failed.
    //     */
    //    private String convertAndValidateStringAsTIMS(BSONObject bsonObject, String value) {
    //        String result = "";
    //        String colon = ":";
    //        String tims = convertToString(bsonObject, value);
    //        String[] timeValues = tims.split(colon);
    //
    //        int invalidValue = -1;
    //        int hour = invalidValue;
    //        int min = invalidValue;
    //        int sec = invalidValue;
    //
    //        if (timeValues.length > 0 && timeValues.length == 3) {
    //            hour = Integer.parseInt(timeValues[0]);
    //            if (hour >= 0 && hour < 24) {
    //                min = Integer.parseInt(timeValues[1]);
    //                if (min >= 0 && min < 60) {
    //                    String secondsValue = timeValues[3];
    //                    if (secondsValue.length() == 2) {
    //                        sec = Integer.parseInt(secondsValue);
    //                    } else if ((secondsValue.length() > 2)) {
    //                        sec = Integer.parseInt(secondsValue.substring(0, 1));
    //                    }
    //                    //fallback if seconds is invalid
    //                    if (sec < 0 || sec > 60) {
    //                        sec = 0;
    //                    }
    //                }
    //            }
    //        }
    //        if (hour != invalidValue && min != invalidValue) {
    //            result = String.valueOf(hour) + colon + String.valueOf(min) + colon + String.valueOf(sec);
    //        }
    //        return result;
    //    }

    // ------------------------------------------------------------------------
    // getters/setters
    // ------------------------------------------------------------------------

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getConnId() {
        return connId;
    }

    public void setConnId(String connId) {
        this.connId = connId;
    }

    public Scarr getCarrId() {
        return carrId;
    }

    public void setCarrId(Scarr carrId) {
        this.carrId = carrId;
    }

    public String getAirpFrom() {
        return airpFrom;
    }

    public void setAirpFrom(String airpFrom) {
        this.airpFrom = airpFrom;
    }

    public String getAirpTo() {
        return airpTo;
    }

    public void setAirpTo(String airpTo) {
        this.airpTo = airpTo;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public String getCountryFrom() {
        return countryFrom;
    }

    public void setCountryFrom(String countryFrom) {
        this.countryFrom = countryFrom;
    }

    public String getCountryTo() {
        return countryTo;
    }

    public void setCountryTo(String countryTo) {
        this.countryTo = countryTo;
    }

    public int getFlTime() {
        return flTime;
    }

    public void setFlTime(int flTime) {
        this.flTime = flTime;
    }

    public LocalTime getDepTime() {
        return LocalTime.parse(depTime);
    }

    public void setDepTime(String depTime) {
        //        convertAndValidateStringAsTIMS()

        this.depTime = depTime;
    }//TODO implement check if valid?

    public LocalTime getArrTime() {
        return LocalTime.parse(arrTime);
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public UnitOfLength getDistId() {
        return distId;
    }

    public void setDistId(UnitOfLength distId) {
        this.distId = distId;
    }

    public String getFlType() {
        return flType;
    }

    public void setFlType(String flType) {
        this.flType = flType;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    //    private String airpFrom;todo delete
    //
    //    private String airpTo;
    //
    //    private String cityFrom;

    //    @OneToOne(cascade = CascadeType.ALL)
    //    @ForeignKey(name = "SIM11_HALFAUTOMATIC_FK")

    //@Column(name = "SIMULATION_IS_ACTIVE", nullable = false)

    //    @ManyToOne
    //    @ForeignKey(name = "SIM13_HALFAUTO_SCENARIO_FK")
    //    @JoinColumn(name = "SIM12_HALFAUTO_SCENARIO_ID")
    //    public HalfautomaticScenario getHalfautomaticScenario() {
    //        return mHalfautomaticScenario;
    //    }

    //    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "halfautomaticScenario")
    //    @OrderColumn(name = "ORDER_INDEX", nullable = true)
    //    public List<ScenarioAssignment> getScenarioAssignmentList() {
    //        return mScenarioAssignmentList;
    //    }
    //
    //    /**
    //     * Sets {@link HalfautomaticScenario#mScenarioAssignmentList}.
    //     *
    //     * @param scenarioAssignment List of scenario assignment objects.
    //     */
    //    public void setScenarioAssignmentList(List<ScenarioAssignment> scenarioAssignment) {
    //        mScenarioAssignmentList = scenarioAssignment;
    //    }
    //
    //    /**
    //     * Adds a scenario assignment to the common list.
    //     *
    //     * @param scenarioAssignment Scenario assingment object to add.
    //     */
    //    @Transient
    //    public void addScenarioAssignment(ScenarioAssignment scenarioAssignment) {
    //        mScenarioAssignmentList.add(scenarioAssignment);
    //    }
    //
    //    /**
    //     * Adds a scenario assignment to the common list at indexed position.
    //     *
    //     * @param scenarioAssignment Scenario assingment object to add.
    //     * @param index              Index where to add the object in the list.
    //     */
    //    @Transient
    //    public void addScenarioAssignment(int index, ScenarioAssignment scenarioAssignment) {
    //        mScenarioAssignmentList.add(index, scenarioAssignment);
    //    }

    //    */
    //    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "specificScenario")
    //    @OrderColumn(name = "ORDER_INDEX", nullable = true)
    //    public List<ScenarioAssignment> getSpecificScenarioAssignment() {
    //        return mSpecificScenarioAssignment;
    //    }

    //
    //    @Lob
    //    public String getSetupSequenceText() {
    //        return mSetupSequenceText;
    //    }

    //    @Column(name = "CARRID")//TODO check fk settings
    //    public String getCarrId() {
    //        return carrId;
    //    }
    //
    //    public void setCarrId(String airportFrom) {
    //        airpFrom = airportFrom;
    //    }
    //
    //    @Column(name = "CONNID")//TODO check fk settings
    //    public String getConnId() {
    //        return connId;
    //    }
    //
    //    public void setAirportFrom(String airportFrom) {
    //        airpFrom = airportFrom;
    //    }
    //
    //    @Column(name = "AIRP_FROM")
    //    public String getAirportFrom() {
    //        return airpFrom;
    //    }
    //
    //    public void setAirpFrom(String airportFrom) {
    //        airpFrom = airportFrom;
    //    }
    //
    //    @Column(name = "AIRP_TO")
    //    public String getAirpTo() {
    //        return airpTo;
    //    }
    //
    //    public void setAirpTo(String airpTo) {
    //        this.airpTo = airpTo;
    //    }
    //
    //    @Column(name = "CITY_FROM")
    //    public String getCityFrom() {
    //        return cityFrom;
    //    }
    //
    //    public void setCityFrom(String cityFrom) {
    //        this.cityFrom = cityFrom;
    //    }
    //
    //    @Column(name = "CITYTO")
    //    public String getCityTo() {
    //        return cityTo;
    //    }
    //
    //    public void setCityTo(String cityTo) {
    //        this.cityTo = cityTo;
    //    }
    //
    //    @Column(name = "COUNTRYFR")
    //    public String getCountryFrom() {
    //        return countryFrom;
    //    }
    //
    //    public void setCountryFrom(String countryFrom) {
    //        this.countryFrom = countryFrom;
    //    }
    //
    //    @Column(name = "COUNTRYTO")
    //    public String getCountryTo() {
    //        return countryTo;
    //    }
    //
    //    public void setCountryTo(String countryTo) {
    //        this.countryTo = countryTo;
    //    }
    //
    //    @Column(name = "FLTIME")
    //    public int getFlTime() {
    //        return flTime;
    //    }
    //
    //    public void setFlTime(int flTime) {
    //        this.flTime = flTime;
    //    }
    //
    //    @Column(name = "DEPTIME")
    //    public String getDepTime() {
    //        return depTime;
    //    }
    //
    //    public void setDepTime(String depTime) {
    //        this.depTime = depTime;
    //    }
    //
    //    @Column(name = "ARRTIME")
    //    public String getArrTime() {
    //        return arrTime;
    //    }
    //
    //    public void setArrTime(String arrTime) {
    //        this.arrTime = arrTime;
    //    }
    //
    //    @Column(name = "DISTANCE____")
    //    public double getDistance() {
    //        return distance;
    //    }
    //
    //    public void setDistance(double distance) {
    //        this.distance = distance;
    //    }
    //
    //    @Column(name = "DISTID")
    //    public String getDistId() {
    //        return distId;
    //    }
    //
    //    public void setDistId(String distId) {
    //        this.distId = distId;
    //    }
    //
    //    @Column(name = "FLTYPE")
    //    public String getFlType() {
    //        return flType;
    //    }
    //
    //    public void setFlType(String flType) {
    //        this.flType = flType;
    //    }
    //
    //    @Column(name = "PERIOD____")
    //    public int getPeriod() {
    //        return period;
    //    }
    //
    //    public void setPeriod(int period) {
    //        this.period = period;
    //    }

    //    /**
    //     * Returns {@Link OrderProcessing#mRegistrationBeforeTargetStart}.
    //     *
    //     * @return Selection whether operations may be registered before the target start.
    //     */
    //    @Column(name = "REG_BEFORE_PLANNED_START")
    //    public boolean getRegistrationBeforeTargetStart() {
    //        return mRegistrationBeforeTargetStart;
    //    }
    //
    //    /**
    //     * Sets {@Link OrderProcessing#mRegistrationBeforeTargetStart}
    //     *
    //     * @param registrationBeforeTargetStart Registration setting.
    //     */
    //    public void setRegistrationBeforeTargetStart(boolean registrationBeforeTargetStart) {
    //        this.mRegistrationBeforeTargetStart = registrationBeforeTargetStart;
    //    }
    //
    //    /**
    //     * Returns {@Link OrderProcessing#mRegistrationBeforeEarliestStart}.
    //     *
    //     * @return Selection whether operations may be registered before the earliest possible start.
    //     */
    //    @Column(name = "REG_BEFORE_EARLIEST_START")
    //    public boolean getRegistrationBeforeEarliestStart() {
    //        return mRegistrationBeforeEarliestStart;
    //    }
    //
    //    /**
    //     * Sets {@Link OrderProcessing#mRegistrationBeforeEarliestStart}
    //     *
    //     * @param registrationBeforeEarliestStart Registration setting.
    //     */
    //    public void setRegistrationBeforeEarliestStart(boolean registrationBeforeEarliestStart) {
    //        this.mRegistrationBeforeEarliestStart = registrationBeforeEarliestStart;
    //    }
    //
    //    /**
    //     * Returns {@Link OrderProcessing#mRegistrationBeforePreviousEnding}.
    //     *
    //     * @return Selection whether operations may be registered before the previous operations ended.
    //     */
    //    @Column(name = "REG_BEFORE_PREVIOUSE_ENDING")
    //    public boolean getRegistrationBeforePreviousEnding() {
    //        return mRegistrationBeforePreviousEnding;
    //    }
    //
    //    /**
    //     * Sets {@Link OrderProcessing#mRegistrationBeforePreviousEnding}
    //     *
    //     * @param registrationBeforePreviousEnding Registration setting.
    //     */
    //    public void setRegistrationBeforePreviousEnding(boolean registrationBeforePreviousEnding) {
    //        this.mRegistrationBeforePreviousEnding = registrationBeforePreviousEnding;
    //    }
    //
    //    /**
    //     * Returns {@Link OrderProcessing#mSequence}.
    //     *
    //     * @return Startsequence as enum.
    //     */
    //    @Column(length = 30)
    //    @Enumerated(EnumType.STRING)
    //    public StartSequence getSequence() {
    //        return mSequence;
    //    }
    //
    //    /**
    //     * Sets {@Link OrderProcessing#mSequence}
    //     *
    //     * @param sequence Startsequence setting.
    //     */
    //    public void setSequence(StartSequence sequence) {
    //        this.mSequence = sequence;
    //    }
    //
    //    /**
    //     * Returns {@Link OrderProcessing#mOperatingStateIdOrderShortage}.
    //     *
    //     * @return Link to a operating state.
    //     */
    //    @Column(name = "OP_STATE_ID_ORDER_SHORTAGE", nullable = false)
    //    public long getOperatingStateIdOrderShortage() {
    //        return mOperatingStateIdOrderShortage;
    //    }
    //
    //    /**
    //     * Sets {@Link OrderProcessing#mOperatingStateIdOrderShortage}
    //     *
    //     * @param operatingStateIdOrderShortage Link to a operating state.
    //     */
    //    public void setOperatingStateIdOrderShortage(long operatingStateIdOrderShortage) {
    //        this.mOperatingStateIdOrderShortage = operatingStateIdOrderShortage;
    //    }
    //
    //    /**
    //     * Returns {@Link OrderProcessing#mOperatingStateIdWaitForStart}.
    //     *
    //     * @return Link to a operating state.
    //     */
    //    @Column(name = "OP_STATE_ID_WAIT_FOR_START", nullable = false)
    //    public long getOperatingStateIdWaitForStart() {
    //        return mOperatingStateIdWaitForStart;
    //    }
    //
    //    /**
    //     * Sets {@Link OrderProcessing#mOperatingStateIdWaitForStart}
    //     *
    //     * @param operatingStateIdWaitForStart Link to a operating state.
    //     */
    //    public void setOperatingStateIdWaitForStart(long operatingStateIdWaitForStart) {
    //        this.mOperatingStateIdWaitForStart = operatingStateIdWaitForStart;
    //    }
    //
    //    /**
    //     * Returns {@Link OrderProcessing#mHalfautomaticScenario}.
    //     *
    //     * @return Link to a halfautomatic scenario.
    //     */
    //    @OneToOne(cascade = CascadeType.ALL)
    //    @ForeignKey(name = "SIM14_HALFAUTOMATIC_FK")
    //    public HalfautomaticScenario getHalfautomaticScenario() {
    //        return mHalfautomaticScenario;
    //    }
    //
    //    /**
    //     * Sets {@Link OrderProcessing#mHalfautomaticScenario}.
    //     *
    //     * @param halfautomaticScenarioId Halfautomatic settings inside this.
    //     */
    //    public void setHalfautomaticScenario(HalfautomaticScenario halfautomaticScenarioId) {
    //        this.mHalfautomaticScenario = halfautomaticScenarioId;
    //    }
}
