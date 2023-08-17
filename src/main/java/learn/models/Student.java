package learn.models;

import learn.annotations.DbColumn;
import learn.annotations.DbEntity;
import learn.annotations.DbTable;

@DbEntity
@DbTable(name = "students")
public class Student {
    @DbColumn(name = "student_id")
    private String studentId;

    @DbColumn(name = "name")
    private String name;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
