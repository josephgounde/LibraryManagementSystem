package model;
import java.time.LocalDate;


public class Member {
    private int id;
    private String name;
    private String email;
    private LocalDate enrollmentDate;

    //constructors
    public Member(){

    }

    public Member(int id, String name, String email, LocalDate enrollmentDate){
        this.id=id;
        this.name=name;
        this.email=email;
        this.enrollmentDate=enrollmentDate;
    }

    //Getters and Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
