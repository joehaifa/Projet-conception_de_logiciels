package com.example.demo;

public class Employee extends Person
{
    private String post;
    private float salary;

    public Employee(String fname, String lname, String phoneNumber, String email, int year,
                    String post, float salary, String id) throws IllegalArgumentException
    {
        super(fname, lname, phoneNumber, email, year, id);
        this.post = post;
        if(salary < 0)
            throw new IllegalArgumentException("Salary is negative.");
        else
            this.salary = salary;
    }


    public Employee(String fname, String lname, String id)
    {
        super(fname,lname, id);
    }

    public String getPost()
    {
        return post;
    }
    public void setPost(String post)
    {
        this.post = post;
    }



    public float getSalary()
    {
        return salary;
    }

    public void setSalary(float salary) throws IllegalArgumentException{
        if(salary < 0)
            throw new IllegalArgumentException("Salary is negative.");
        else
            this.salary = salary;
    }



    public String toString()
    {
        String str = "\nID : " + super.getId()
                + "\nFirst name : " + super.getFirstName()
                + "\nLast name : " + super.getLastName()
                + "\nPhone number : " + super.getPhoneNumber()
                + "\nEmail : " + super.getEmail()
                + "\nPost : " + this.post
                + "\nSalary : " + this.salary;
        return str;
    }




}
