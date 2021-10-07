package marshaller;

import entity.Person;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public interface Marshaller {
    void setStream(FileOutputStream fin);

    void process(Object entity) throws IOException;

    void appendProcess(Object entity) throws IOException;

    List<Person> toJavaObject(File file) throws IOException;

    void toJSON(List<Person> person, File file) throws IOException;
}
