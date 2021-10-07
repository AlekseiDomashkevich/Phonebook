package storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Person;
import marshaller.Marshaller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public interface Storage<E> {

    Class<E> getEntityClass();

    void setEntityClass(Class<E> clazz);

    void setMarshaller(Marshaller marshaller);

    String getFilePath();

    void save(Object person);

    default List<E> findAll() {
        var entities = new ArrayList<E>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            entities = (ArrayList<E>) mapper.readValue(new File("./phonebook.json"), new TypeReference<List<Person>>() {});

        } catch (IOException e) {
            e.printStackTrace();
        }

//        try (var scanner = new Scanner(new FileInputStream(this.getFilePath())).useDelimiter("\\Z")) {
//            var content = scanner.next();
//            var lines = content.split("\n");
//            for (int i = 0; i < lines.length; i++) {
//                var row = lines[i];
//                var columns = row.split("/");
//                try {
//                    var o = getEntityClass().newInstance();
//                    ((Entity)o).setData(columns);
//                    entities.add(o);
//                } catch (InstantiationException | IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        return entities;
    }
}
