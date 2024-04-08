package com.caleno;

import java.sql.Date;

public class employee {
    //TODO: cribar atributos?

    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String gender;
    private String location;
    private String phoneNumber;
    private String position;
    private String profileImage;
    private Date dateOfBirth;
    private Double salary;

    public employee() {
    }

    public employee(Integer employeeId, String firstName, String lastName, String gender, String location, String phoneNumber,
                    String position, String profileImage, Date dateOfBirth, Double salary) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.profileImage = profileImage;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
    }

    public employee(Integer employeeId, String firstName, String lastName, String location, String position, Double salary) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.position = position;
        this.salary = salary;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getGender() {
        return gender;
    }
    public String getLocation() {
        return location;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getPosition() {
        return position;
    }
    public String getProfileImage() {
        return profileImage;
    }
    public Double getSalary() {
        return salary;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}
