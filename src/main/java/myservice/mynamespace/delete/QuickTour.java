package myservice.mynamespace.delete;

import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.query.Query;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruhn on 10.12.2017.
 */
public class QuickTour {
    private QuickTour() {
    }

    public static void main(final String[] args) throws UnknownHostException {
        final Morphia morphia = new Morphia();

        // tell morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.mapPackage("myservice.mynamespace.connection");

        // create the Datastore connecting to the database running on the default port on the local host
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "morphia_example");
        datastore.getDB().dropDatabase();
        datastore.ensureIndexes();

        final Employee elmer = new Employee("Elmer Fudd", 50000.0);
        datastore.save(elmer);

        final Employee daffy = new Employee("Daffy Duck", 40000.0);
        datastore.save(daffy);

        final Employee pepe = new Employee("Pepé Le Pew", 25000.0);
        datastore.save(pepe);

        elmer.getDirectReports().add(daffy);
        elmer.getDirectReports().add(pepe);

        datastore.save(elmer);

        Query<Employee> query = datastore.find(Employee.class);
        final List<Employee> employees = query.asList();
        for (Employee sc : employees){
            System.out.println(sc.toString());
        }

//        Assert.assertEquals(3, employees.size());
//
//        List<Employee> underpaid = datastore.find(Employee.class)
//                .filter("salary <=", 30000)
//                .asList();
//        Assert.assertEquals(1, underpaid.size());
//
//        underpaid = datastore.find(Employee.class)
//                .field("salary").lessThanOrEq(30000)
//                .asList();
//        Assert.assertEquals(1, underpaid.size());
//
//        final Query<Employee> underPaidQuery = datastore.find(Employee.class)
//                .filter("salary <=", 30000);
//        final UpdateOperations<Employee> updateOperations = datastore.createUpdateOperations(Employee.class)
//                .inc("salary", 10000);
//
//        final UpdateResults results = datastore.update(underPaidQuery, updateOperations);
//
//        Assert.assertEquals(1, results.getUpdatedCount());
//
//        final Query<Employee> overPaidQuery = datastore.find(Employee.class)
//                .filter("salary >", 100000);
//        datastore.delete(overPaidQuery);
    }
}

@Entity("employees")
@Indexes(@Index(value = "salary", fields = @Field("salary")))
class Employee {
    @Id
    private ObjectId id;
    private String name;
    private Integer age;
    @Reference
    private Employee manager;
    @Reference
    private List<Employee> directReports = new ArrayList<Employee>();
    @Property("wage")
    private Double salary;

    public Employee() {
    }

    public Employee(final String name, final Double salary) {
        this.name = name;
        this.salary = salary;
    }

    public List<Employee> getDirectReports() {
        return directReports;
    }

    public void setDirectReports(final List<Employee> directReports) {
        this.directReports = directReports;
    }

    public ObjectId getId() {
        return id;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(final Employee manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(final Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", manager=" + manager +
                ", directReports=" + directReports +
                ", salary=" + salary +
                '}';
    }
}
