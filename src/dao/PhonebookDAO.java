package dao;

import entity.Person;
import storage.Storage;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PhonebookDAO {
    private final List<Storage<Person>> storages;

    public PhonebookDAO(List<Storage<Person>> storages) {
        this.storages = storages;
    }

    public List<Person> findBy(Predicate<Person> predicate) {
        List<Person> result;
        var personList = this.findAll();
        result = personList.stream()
                .filter(predicate::test)
                .collect(Collectors.toList());
        return result;
    }


    private void saveAll(Person[] people) {
        this.deleteFile();

        IntStream.range(0, people.length)
                .filter(i -> people[i] != null)
                .mapToObj(i -> people[i])
                .forEach(this::save);
    }

    private void deleteFile() {
        new File("./user1.EXAMPLE.json").delete();
    }

    public Person findByLastname(String lastname) {
        try {
            var ois = new ObjectInputStream(new FileInputStream("./phonebook.txt"));
            Person person;
            while (true){
                try{
                    person =(Person) ois.readObject();

                    if(person.getLastname().equals(lastname)){
                        return person;
                    }
                }catch (EOFException e){
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

//        try (var ois = new ObjectInputStream(new FileInputStream("./phonebook.txt"))) {
//            Person p;
//            while ((p = (Person) ois.readObject()) != null) {
//                if (p.getLastname().equals(lastname)) {
//                    return p;
//                }
//            }
//        } catch (IOException | ClassNotFoundException exception) {
//            exception.printStackTrace();
//        }


//        try (var scanner = new Scanner(new FileInputStream("./phonebook.txt")).useDelimiter("\\Z")) {
//            var content = scanner.next();
//            var lines = content.split("\n");
//            for (int i = 0; i < lines.length; i++) {
//                var row = lines[i];
//                var columns = row.split("/");
//                var p = new Person(columns[0], columns);
//                if (p.getLastname().equals(lastname)) {
//                    return p;
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        return null;
    }

    public Person find(Integer id) {
        var storage = this.storages.get(0);
        var people = storage.findAll();
        return people.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void delete(int id) {
        Person[] people = this.storages.get(0).findAll().toArray(new Person[0]);
        IntStream.range(0, people.length)
                .filter(i -> people[i].getId().equals(id))
                .forEach(i -> people[i] = null);
        this.saveAll(people);
    }

    public void save(Person person) {
        this.storages.forEach(storage -> storage.save(person));
    }

    public void saveIndex(){

        var personList = this.findAll();
        var map = personList.stream()
                .collect(Collectors.toMap(
                        Person::getId,
                        Person::getLastname,
                        (a, b) -> b, HashMap::new)
                );
        ObjectOutputStream ous = null;
        try {
            ous = new ObjectOutputStream(new FileOutputStream("./Index.txt", true));
            ous.writeObject(map);
            ous.flush();
            ous.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public List<Person> findAll() {
        return this.storages.get(0).findAll();

    }

    public String findByIndex(int id) {
        ObjectInputStream ois = null;
        HashMap<Integer, String> personMap;
        try {
            ois = new ObjectInputStream(new FileInputStream("./Index.txt"));
            personMap =(HashMap<Integer, String>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            return null;
        }
        return personMap.get(id);
    }
}
