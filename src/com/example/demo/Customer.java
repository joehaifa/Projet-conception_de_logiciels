package com.example.demo;

import java.time.LocalDate;

public class Customer extends Person {
    private LocalDate startDate;
    private LocalDate endDate;


    public Customer(String fname, String lname, String phoneNumber, String email, int year,
                    LocalDate startDate, LocalDate endDate, String id) throws IllegalArgumentException
    {
        super(fname, lname, phoneNumber, email, year, id);

        if (startDate.compareTo(endDate) > 0)
            throw new IllegalArgumentException("Start date is greater than end date.");
        else
        {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }


    public Customer(String fname, String lname, String id)
    {
        super(fname, lname, id);
    }



    public LocalDate getStartDate()
    {
        return this.startDate;
    }
    public void setStartDate(LocalDate startDate)
    {
        if (startDate.compareTo(this.endDate) > 0)
            throw new IllegalArgumentException("Start date is greater than end date.");
    }


    public LocalDate getEndDate()
    {
        return this.endDate;
    }
    public void setEndDate(LocalDate endDate)
    {
        if(this.startDate.compareTo(endDate) > 0)
            throw new IllegalArgumentException("start date is greater than end date.");
    }


    public String toString()
    {
        String str = "\nID : " + super.getId()
                + "\nFirst name : " + super.getFirstName()
                + "\nLast name : " + super.getLastName()
                + "\nPhone number : " + super.getPhoneNumber()
                + "\nEmail : " + super.getEmail()
                + "\nStart date : " + this.startDate
                + "\nEnd date : " + this.endDate;
        return str;
    }


}
