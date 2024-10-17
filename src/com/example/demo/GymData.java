package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class GymData {


    private ArrayList<Customer> customers;

    public GymData()
    {
    }

    public ArrayList<Customer> getCustomers()
    {
        return this.customers;
    }

    public void setCustomers(ArrayList<Customer> customers) { this.customers = customers; }

    public static boolean searchIdCustomers(String id, GymData gymData)
    {
        for (Customer cust : gymData.getCustomers())
            if(cust.getId().equals(id))
                return true;
        return false;
    }


    public static ArrayList<Customer> retrieveAllCustomers()
    {
        System.out.println("Retrieving customers...");
        ArrayList<Customer> customers = new ArrayList<Customer>();


        try
        {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer");

            while (rs.next())
            {
                customers.add(new Customer(rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getDate(7).toLocalDate(),
                        rs.getDate(8).toLocalDate(), rs.getString(1)));
            }

            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception exc)
        {

            GymData.catchExceptions("Exception during login retrieval: " + exc.getMessage());
            exc.printStackTrace();
        }
        return customers;
    }


    public static void addCustomer(String fname, String lname, String phoneNumber, String email, int year,
                                   LocalDate startDate, LocalDate endDate, String id)
    {
        System.out.println("Adding customer...");


        try
        {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();

            String query = "INSERT INTO customer (cust_id, cust_fname, cust_lname, cust_pn, cust_email, cust_yob, cust_start_date, cust_end_date)"
                    + " VALUES ('" + id + "', '" + fname + "', '" + lname + "', '" + phoneNumber + "', '" + email + "', " + year
                    + ", '" + Date.valueOf(startDate) + "', '" + Date.valueOf(endDate) + "');";
            stmt.executeUpdate(query);

            stmt.close();
            conn.close();
        }
        catch(Exception exc)
        {
            GymData.catchExceptions("Exception during customer adding: " + exc.getMessage());
            exc.printStackTrace();
        }
    }

    public static void updateCustomer(String fname, String lname, String phoneNumber, String email, int year,
                                      LocalDate startDate, LocalDate endDate, String id)
    {
        System.out.println("Updating customer...");


        try
        {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();

            String query = "UPDATE customer SET cust_fname = '" +  fname +"', cust_lname = '" + lname + "', cust_pn = '" + phoneNumber + "', "
                    + "cust_email = '" + email + "', cust_yob = " + year + ", cust_start_date = '" + Date.valueOf(startDate)
                    + "', cust_end_date = '" + Date.valueOf(endDate) + "' WHERE cust_id = '" + id + "';";
            stmt.executeUpdate(query);

            stmt.close();
            conn.close();
        }
        catch(Exception exc)
        {
            GymData.catchExceptions("Exception during customer updating: " + exc.getMessage());
            exc.printStackTrace();
        }
    }



    public static void deleteCustomer(String id)
    {
        System.out.println("Deleting customer...");


        try
        {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();

            String query = "DELETE FROM customer WHERE cust_id = '" + id + "';";
            stmt.executeUpdate(query);

            stmt.close();
            conn.close();
        }
        catch(Exception exc)
        {
            GymData.catchExceptions("Exception during customer deleting: " + exc.getMessage());
            exc.printStackTrace();
        }
    }














    public static void catchExceptions(String message)
    {
        try(PrintWriter output = new PrintWriter(new File("C:\\Users\\User\\Desktop\\final project prog\\errors\\errors.txt")))
        {
            output.append(message);
        }
        catch (IOException exc)
        {
            System.out.println("An error occurred while writing to the file: " + exc.getMessage());
            exc.printStackTrace();
        }
    }



}
