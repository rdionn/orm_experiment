Just experiment project to understand how orm like framework works
under the hood

```aidl
Entity: learn.models.Student
Table Name: students
Field: studentId
Column Name: student_id
Field Type Object: java.lang.String
Field: name
Column Name: name
Field Type Object: java.lang.String
```

Generated insert statement
```aidl
Insert Into students(student_id,name) Values ('1','Hello')
```