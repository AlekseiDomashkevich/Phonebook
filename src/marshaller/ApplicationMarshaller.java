package marshaller;



import entity.Application;
import entity.Person;

import java.io.*;
import java.util.List;

public class ApplicationMarshaller implements Marshaller {
    private OutputStream fout;

    @Override
    public void setStream(FileOutputStream fin) {
        this.fout = fin;
    }

    @Override
    public void process(Object entity) throws IOException {
        var application = (Application) entity;
        var oos = new ObjectOutputStream(fout);
        oos.writeObject(application);
        oos.flush();
//        fin.write(application.getId().toString().getBytes(StandardCharsets.UTF_8));
//        fin.write("/".getBytes(StandardCharsets.UTF_8));
//        fin.write(application.getFirstname().getBytes(StandardCharsets.UTF_8));
//        fin.write("/".getBytes(StandardCharsets.UTF_8));
//        fin.write(application.getLastname().getBytes(StandardCharsets.UTF_8));
//        fin.write("/".getBytes(StandardCharsets.UTF_8));
//        fin.write(application.getAge().toString().getBytes(StandardCharsets.UTF_8));
//        fin.write("/".getBytes(StandardCharsets.UTF_8));
//        fin.write(application.getAddress().getBytes(StandardCharsets.UTF_8));
//        fin.write("/".getBytes(StandardCharsets.UTF_8));
//        fin.write(application.getStatus().toString().getBytes(StandardCharsets.UTF_8));
//        fin.write("\n".getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void appendProcess(Object entity) throws IOException {

    }

    @Override
    public List<Person> toJavaObject(File file) throws IOException {
        return null;
    }

    @Override
    public void toJSON(List<Person> person, File file) throws IOException {

    }
}
