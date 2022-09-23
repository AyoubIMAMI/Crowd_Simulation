import java.util.ArrayList;
import java.util.List;

public class Person {
    int id;
    Position position;
    List<Person> personList = new ArrayList<>();

    public Person(int id, Position position) {
        this.id = id;
        this.position = position;
    }

    List<Person> getPersonList() {
        return personList;
    }

    void addPersonToList(Person person) {
        personList.add(person);
    }

}
