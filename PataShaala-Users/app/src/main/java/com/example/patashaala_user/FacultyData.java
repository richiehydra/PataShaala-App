package com.example.patashaala_user;

public class FacultyData {
    private String name;
    private String department;
    private String email;

    public FacultyData() {
    }

    public FacultyData(String name, String department, String email) {
        this.name = name;
        this.department =department;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }
}
