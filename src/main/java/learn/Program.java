package learn;

import learn.models.Student;
import learn.services.EntityServices;

public class Program {
    private EntityServices entityServices;
    public Program() {
        entityServices = new EntityServices(getClass().getPackageName());
    }

    public void debug() {
        entityServices.debug();
    }

    public void debugInsertStatement(Object entity) {
        entityServices.debugInsertStatement(entity);
    }

    public static void main(String[] args) {
        Program p = new Program();
        p.debug();

        Student s = new Student();
        s.setStudentId("1");
        s.setName("Hello");

        p.debugInsertStatement(s);
    }
}
