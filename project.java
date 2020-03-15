/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcproject;
/**
 *
 * @author jigom
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;



public class project {
    //JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/JDBCProject";
    //Database credentials
    //I dont have a password or username so i dont need it
    //static final String USER = " ";
    //static final String PASS = " ";
    
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            //open connection
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            //menu goes here?
            menu(conn, stmt);
        }catch(SQLException se){
            //handle errors for Jbdc
            se.printStackTrace();
        }catch (Exception e){
            //handle erros for jbdc
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2) {
            }//nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }
    
    
    
    //Create a simple menu to run all of the options below: -- Jose 
    static void menu(Connection conn, Statement stmt) throws SQLException{
        Scanner cin = new Scanner(System.in);
        String choice = null;
        Boolean quit = false;
        while(!quit){
            System.out.print("-----------------------------------\n"
                +" Menu \n" 
                +"-----------------------------------\n"
                +"1. List all writing groups\n"
                +"2. List all the data for a specific group\n"
                +"3. List all publishers\n"
                +"4. List all data for a specific publisher\n"
                +"5. List all book titles\n"
                +"6. List all data for a specific book\n"
                +"7. Insert a new book into database\n"
                +"8. Insert a new publisher"
                +"9. Remove a book\n"
                +"Q. Quit\n"
                +"Enter choice: ");
            
            choice = cin.nextLine();
            
            switch(choice){
                case "1": displayAllWritingGroups(conn, stmt); break;
                case "2": displayWritingGroup(conn, stmt); break;
                case "3": break;
                case "4": break;
                case "5": break;
                case "6": break;
                case "7": break;
                case "8": break;
                case "9": break;
                case "q":
                case "Q": quit = true; break;
                default: System.out.println("Invalid option"); break;   
            }
            System.out.println("\n\n");
        }
    }
    
    //List all writing groups -- Jose 
    static void displayAllWritingGroups(Connection conn, Statement stmt) throws SQLException{
        ResultSet rs = stmt.executeQuery("Select * from writinggroup");
        System.out.print("\n\n-----------------------------\n Writing groups\n-----------------------------\n");
        System.out.format("%-26s %-15s %-15s %s\n", "Group Name", "Head Writer", "Year formed", "Subject");
        System.out.println("--------------------------------------------------------------------------");
        while(rs.next()){
            String groupname = rs.getString("groupname");
            String headwriter = rs.getString("headwriter");
            int yearformed = rs.getInt("yearformed");
            String subject = rs.getString("subject");
                
            //Display Values
            System.out.format("%-26s %-15s %-15d %s\n", groupname, headwriter, yearformed, subject);
        }
        rs.close();
    }
    //List all the data for a group specified by the user .
    //This includes all the data for the associated books and publishers. -- Jose
    static void displayWritingGroup(Connection conn, Statement stmt) throws SQLException{
        Scanner cin = new Scanner(System.in);
        ResultSet rs = stmt.executeQuery("select * from writinggroups");
        ArrayList<String> names = new ArrayList<String>();
        for(int i = 1; rs.next(); i++){
            String groupname = rs.getString("groupname");
            names.add(groupname);
            System.out.println(i + ") " + groupname );
        }
        System.out.print("Select a writing group:  ");
        String choice = cin.nextLine();
        String SQL = "select * from books natural join publisher natural join writinggroup where groupname = ?";
        try{
            int i = Integer.parseInt(choice);
            if((i -1) < 0 || (i -1) > names.size()){
                System.out.println("Invalid Input");
            }
            else{
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, names.get(i-1));
                rs = pstmt.executeQuery();
                while(rs.next()){
                    
                }
            }
        }catch(NumberFormatException e){
            System.out.println("Invalid Input");
        }   
    }

    //List all publishers -- Edwin 

    //List all the data for a pubisher specified by the user.
    //This includes all the data for the associated books and writing groups. --Edwin

    //List all book titles -- Jose 

    //List all the data for a single book specified by the user.
    //This includes all the data for the associated publisher and writing group. -- Jose

    //Insert a new book -- Edwin 

    //Insert a new publisher and update all book published by one publisher to be published by the new pubisher.
    //This requirement is two separate operations. The idea is that a new publisher,
    //(xyz) buys out an existing publisher (abc). After the new publisher is added to the database, 
    //all books that are currently published by abc will now be published by xyz. --Edwin

    //Remove a single book specified by the user --Edwin
}
