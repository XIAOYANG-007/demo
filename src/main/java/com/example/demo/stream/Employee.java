package com.example.demo.stream;

import lombok.Data;

@Data
public class Employee {
    //姓名
    private String name;
    //年龄
    private Integer age;
    //性别
    private String sex;
    //薪资
    private Double salary;
    //住址
    private String address;
    //工作地
    private String baseAddress;
    //职位
    private String position;
    //主管
    private Employee employee;

    public Employee(String name, Integer age, String sex, Double salary, String address, String baseAddress, String position, Employee employee) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.salary = salary;
        this.address = address;
        this.baseAddress = baseAddress;
        this.position = position;
        this.employee = employee;
    }
}
