package com.caleno;

import java.sql.Date;

public class employee {
    private Integer employee_Id;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNum;
    private String position;
    private String profileImage;
    private Date date;
    private Double salary;

    public employee() {
    }

    public employee(Integer employee_Id, String firstName, String lastName, String gender,
                    String phoneNum, String position, String profileImage, Date date){
        this.employee_Id = employee_Id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.position = position;
        this.profileImage = profileImage;
        this.date = date;
    }

    public employee(Integer employee_Id, String firstName, String lastName,String position, Double salary){
        this.employee_Id = employee_Id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
    }

    public Integer getEmployee_Id() {
        return employee_Id;
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
    public String getPhoneNum() {
        return phoneNum;
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
    public Date getDate() {
        return date;
    }
}
