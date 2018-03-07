package myservice.mynamespace.delete;

import com.mongodb.util.JSON;
import org.bson.BSONObject;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.Mapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by bruhn on 10.12.2017.
 */
public class EnterTestData {

    //Reihenfolge: Scarr, Saplane, Spfli, Sflights, Sbook
//    Scarr
//    JSONParser

    //    JsonReader
    public static void main(String[] args) {


        JSON sd = new JSON();
//        JsonReader reader = new JsonReader();


        Class clazz = EnterTestData.class;
//        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = clazz.getResourceAsStream("/dummyData/dummyDataScarr.json");
        String data = readFromInputStream(inputStream);
//        System.out.println(data);
//        JsonReader reader = new JsonReader(data);

        BSONObject doc = (BSONObject) JSON.parse(data);





        Morphia morphia = new Morphia();
        Mapper morphiaMapper = morphia.getMapper();
//        morphiaMapper.getOptions().getObjectFactory() = new CustomMapper();
//        INPUT_JSON = String.join( "\n", Files.readAllLines( Paths.get( "src/test/resources/EpcisConverterTestInput.json" ) ) );

    }









    public static String readFromInputStream(InputStream inputStream) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (Exception e) {
            System.out.print("");
        }
        return resultStringBuilder.toString();
    }
}


