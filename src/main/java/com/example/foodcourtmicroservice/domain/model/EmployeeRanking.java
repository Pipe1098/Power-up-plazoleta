package com.example.foodcourtmicroservice.domain.model;

public class EmployeeRanking {
    private Long employeeId;
    private Long averageTime;
    private String mail;

    public EmployeeRanking(Long employeeId, Long averageTime, String mail) {
        this.employeeId = employeeId;
        this.averageTime = averageTime;
        this.mail = mail;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Long averageTime) {
        this.averageTime = averageTime;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public EmployeeRanking() {
    }


}
