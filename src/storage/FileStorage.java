package storage;



import entity.Person;
import marshaller.Marshaller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileStorage<E> implements Storage<E> {
    private String filePath;
    private Marshaller marshaller;
    private Class<E> entityClass;

    public FileStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Class<E> getEntityClass() {
        return this.entityClass;
    }

    @Override
    public void setEntityClass(Class<E> clazz) {
        this.entityClass = clazz;
    }

    @Override
    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    @Override
    public String getFilePath() {
        return this.filePath;
    }

    @Override
    public void save(Object person) {
        List<Person> list = new ArrayList<>();
        File file = new File(this.filePath);
        try  {


            if(file.exists()){
//                var fin = new FileOutputStream(file, true);
//                this.marshaller.setStream(fin);
//                this.marshaller.appendProcess(person);
                //System.out.println(Convertor.toJavaObject());
                list = marshaller.toJavaObject(file);
                list.add((Person) person);
                marshaller.toJSON(list, file);
            }else {
//                var fin = new FileOutputStream(file);
//                this.marshaller.setStream(fin);
//                this.marshaller.process(person);
                list.add((Person) person);
                marshaller.toJSON(list, file);

            }

//            fin.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
