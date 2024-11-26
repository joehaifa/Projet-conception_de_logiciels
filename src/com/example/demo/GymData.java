package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GymData {
    private ArrayList<LogIn> logins;
    private ArrayList<Employee> employees;
    private ArrayList<Customer> customers;
    private ArrayList<Appointment> appointments;


    public GymData()
    {
        this.logins = new ArrayList<LogIn>();
        this.employees = new ArrayList<Employee>();
        this.customers = new ArrayList<Customer>();
        this.appointments = new ArrayList<Appointment>();
    }



    public ArrayList<LogIn> getLogins() { return this.logins; }

    public ArrayList<Customer> getCustomers()
    {
        return this.customers;
    }
    public ArrayList<Employee> getEmployees()
    {
        return this.employees;
    }

    public void setEmployees(ArrayList<Employee> employees) { this.employees = employees; }

    public void setLogins(ArrayList<LogIn> logins)
    {
        this.logins = logins;
    }
    public void setCustomers(ArrayList<Customer> customers) { this.customers = customers; }







    public static boolean searchIdCustomers(String id, GymData gymData)
    {
        for (Customer cust : gymData.getCustomers())
            if(cust.getId().equals(id))
                return true;
        return false;
    }






    public static ArrayList<LogIn> retrieveAllLogIns()
    {
        System.out.println("Retrieving logins...");
        ArrayList<LogIn> logins = new ArrayList<LogIn>();


        try
        {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM login");

            while (rs.next())
            {
                logins.add(new LogIn(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
            System.out.println(logins.get(0));

            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception exc)
        {

            GymData.catchExceptions("Exception during login retrieval: " + exc.getMessage());
            exc.printStackTrace();
        }
        return logins;
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
        id="cust"+id;

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
        try(PrintWriter output = new PrintWriter(new File("file:C:\\Users\\User\\Desktop\\final project prog\\errors\\errors.txt")))
        {
            output.append(message);
        }
        catch (IOException exc)
        {
            System.out.println("An error occurred while writing to the file: " + exc.getMessage());
            exc.printStackTrace();
        }
    }


    public static void addLogIn(String username, String password)
    {
        System.out.println("Adding login...");


        try
        {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();
            String hashedPassword = hashPassword(password);
            String query = "INSERT INTO login (login_un, login_pass) VALUES ('" + username + "', '" + hashedPassword + "')";
            stmt.executeUpdate(query);

            stmt.close();
            conn.close();
        }
        catch(Exception exc)
        {
            GymData.catchExceptions("Exception during login adding: " + exc.getMessage());
            exc.printStackTrace();
        }
    }


    public static void updateLogIn(String id, String username, String password) {
        System.out.println("Updating login...");

        try {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();

            // Hash the password before updating it
            String hashedPassword = hashPassword(password);

            String query = "UPDATE login SET login_un = '" + username + "', login_pass = '" + hashedPassword + "' WHERE login_id = " + id;
            stmt.executeUpdate(query);

            stmt.close();
            conn.close();
        } catch (Exception exc) {
            GymData.catchExceptions("Exception during login updating: " + exc.getMessage());
            exc.printStackTrace();
        }
    }



    public static void deleteLogIn(String id)
    {
        System.out.println("Deleting login...");


        try
        {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();

            String query = "DELETE FROM login WHERE login_id =" + id;
            stmt.executeUpdate(query);

            stmt.close();
            conn.close();
        }
        catch(Exception exc)
        {
            GymData.catchExceptions("Exception during login deleting: " + exc.getMessage());
            exc.printStackTrace();
        }
    }
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Employee> retrieveAllEmployees()
    {
        System.out.println("Retrieving employees...");
        ArrayList<Employee> employees = new ArrayList<Employee>();


        try
        {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee");

            while (rs.next())
            {
                employees.add(new Employee(rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getString(7),
                        rs.getFloat(8), rs.getString(1)));
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
        return employees;
    }

    public static void addEmployee(String fname, String lname, String phoneNumber, String email, int year,
                                   String post, float salary, String id)
    {
        System.out.println("Adding employee...");


        try
        {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();

            String query = "INSERT INTO employee (emp_id, emp_fname, emp_lname, emp_pn, emp_email, emp_yob, emp_post, emp_salary)"
                    + " VALUES ('" + id + "', '" + fname + "', '" + lname + "', '" + phoneNumber + "', '" + email + "', " + year
                    + ", '" + post + "', " + salary + ");";
            stmt.executeUpdate(query);

            stmt.close();
            conn.close();
        }
        catch(Exception exc)
        {
            GymData.catchExceptions("Exception during employee adding: " + exc.getMessage());
            exc.printStackTrace();
        }
    }


    public static void updateEmployee(String fname, String lname, String phoneNumber, String email, int year,
                                      String post, float salary, String id)
    {
        System.out.println("Updating employee...");


        try
        {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();

            String query = "UPDATE employee SET emp_fname = '" + fname + "', emp_lname = '" + lname + "', emp_pn = '" + phoneNumber + "', "
                    + "emp_email = '" + email + "', emp_yob = " + year + ", emp_post = '" + post + "', emp_salary = " + salary
                    + " WHERE emp_id = '" + id + "';";
            stmt.executeUpdate(query);

            stmt.close();
            conn.close();
        }
        catch(Exception exc)
        {
            GymData.catchExceptions("Exception during employee updating: " + exc.getMessage());
            exc.printStackTrace();
        }
    }

    public static void deleteEmployee(String id)
    {
        System.out.println("Deleting employee...");


        try
        {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();

            String query = "DELETE FROM employee WHERE emp_id = '" + id + "';";
            stmt.executeUpdate(query);

            stmt.close();
            conn.close();
        }
        catch(Exception exc)
        {
            GymData.catchExceptions("Exception during employee deleting: " + exc.getMessage());
            exc.printStackTrace();
        }
    }

    public static ArrayList<Employee> retrieveSimilarEmployee(String fname, String lname)
    {
        System.out.println("Retrieving similar employees...");
        ArrayList<Employee> employees = new ArrayList<Employee>();


        try
        {
            String url = "jdbc:mysql://localhost:3306/gymdb";
            Connection conn = DriverManager.getConnection(url, "root", "W@2915djkq#");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT emp_id, emp_fname, emp_lname FROM employee "
                    + "WHERE emp_fname LIKE '%" + fname + "%' AND emp_lname LIKE '%" + lname + "%';");

            while (rs.next())
            {
                employees.add(new Employee(rs.getString(2), rs.getString(3), rs.getString(1)));
            }

            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception exc)
        {

            GymData.catchExceptions("Exception during similar employee retrieval: " + exc.getMessage());
            exc.printStackTrace();
        }
        return employees;
    }
    public static boolean searchIdCustomersAndEmployees(String id, GymData gymData)
    {
        for (Customer cust : gymData.getCustomers())
            if(cust.getId().equals(id))
                return true;
        for (Employee emp : gymData.getEmployees())
            if(emp.getId().equals(id))
                return true;
        return false;
    }

    public static boolean searchIdEmployees(String id, GymData gymData)
    {
        for (Employee emp : gymData.getEmployees())
            if(emp.getId().equals(id))
                return true;
        return false;
    }



































}
