package JDBC;

import java.sql.*;
import java.util.Scanner;

public class JDBC {
    private static final String url = "jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC&useSSL=false";
    private static final String user = "root";
    private static final String password = "ebosut90";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet rs = null;
    private static int state;
    private static boolean bool = true;

    public static void main(String args[]){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, user, password);

            statement=connection.createStatement();
            while (bool) {
                Scanner input = new Scanner(System.in);
                System.out.println("Input 1 for read data from all tables");
                System.out.println("Input 2 for call call procedure that insert Apple to Warehouse");
                System.out.println("Input 3 for update Price");
                System.out.println("Input 4 for delete Price");
                System.out.println("Input 5 for insert Price");
                System.out.println("Input Anything for exit");
                state = input.nextInt();

                switch (state) {
                    case 1:
                        readData();
                        break;
                    case 2:
                        callProcedureForInsertToAppleWarehouse();
                        break;
                    case 3:
                        updateDataPrice();
                        break;
                    case 4:
                        deleteDataPrice();
                        break;
                    case 5:
                        insertDataPrice();
                        break;
                    default:
                        bool = false;
                        break;
                }
            }





        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver is not loaded");

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {
            //close connection ,statement and resultset
            if (rs != null) try { rs.close(); } catch (SQLException e) { } // ignore
            if (statement != null) try { statement.close(); } catch (SQLException e) { }
            if (connection != null) try { connection.close(); } catch (SQLException e) { }
        }
    }

    private static void readData() throws SQLException {


        // Read Warehouse table
        rs=statement.executeQuery("SELECT * FROM Warehouse");

        System.out.format("\nTable Warehouse --------------------\n");
        System.out.format("%3s %-12s %-12s %-10s %s\n", "ID", "WarehouseName", "City", "Street", "Email");
        while (rs.next())
        {
            int id=rs.getInt("ID_Warehouse");
            String warhouseName = rs.getString("WarehouseName");
            String city=rs.getString("City");
            String street = rs.getString("Street");
            String email = rs.getString("Email");
            System.out.format("%3d %-12s %-12s %-10s %s\n", id, warhouseName, city, street, email);
        }


        //Read Apple table
        rs=statement.executeQuery("SELECT * FROM Apple");

        System.out.format("\nTable Apple --------------------\n");
        System.out.format("%3s %-18s %-18s %s\n", "ID", "AppleName", "Amount", "ID_Price");
        while (rs.next())
        {
            int id=rs.getInt("ID_Apple");
            String appleName = rs.getString("AppleName");
            String amount=rs.getString("Amount");
            int id_p =rs.getInt("ID_Price");
            System.out.format("%3d %-18s %-18s %3d\n", id, appleName, amount, id_p);
        }

        //Read Price Table
        rs=statement.executeQuery("SELECT * FROM Price");

        System.out.format("\nTable Price --------------------\n");
        System.out.format("%s\n", "Price");
        while (rs.next())
        {
            int id = rs.getInt("ID_Price");
            String price = rs.getString("Price");
            System.out.format("%3d %s\n", id, price);
        }

        // Read AppleWarehouse
        String query="Select " +
                "(SELECT WarehouseName FROM Warehouse WHERE ID_Warehouse = P.ID_Warehouse) AS WarehouseName, " +
                "(SELECT AppleName FROM Apple WHERE ID_Apple = P.ID_Apple) AS AppleName " +
                "FROM AppleWarehouse AS P";
        rs=statement.executeQuery(query);

        System.out.format("\nJoining Table AppleWarehouse --------------------\n");
        System.out.format("%-15s %s\n", "WarehouseName", "AppleName");
        while (rs.next())
        {
            String warehouseName = rs.getString("WarehouseName");
            String appleName = rs.getString("AppleName");
            System.out.format("%-15s %s\n", warehouseName, appleName);
        }
    }

    private static void updateDataPrice() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input Price value what you want to update: ");
        String price = input.next();
        System.out.println("Input new Price value for: "+ price);
        String pricenew = input.next();
        statement.execute("UPDATE Price SET Price ='"+pricenew+"' WHERE Price = '"+price+"';");
    }

    private static void insertDataPrice() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new Price ID value: ");
        String priceID = input.next();
        System.out.println("Input a new Price value: ");
        String newPrice = input.next();

        PreparedStatement preparedStatement;
        preparedStatement=connection.prepareStatement("INSERT Price VALUES (?, ?)");
        preparedStatement.setString(1, priceID);
        preparedStatement.setString(2, newPrice);
        int n=preparedStatement.executeUpdate();
        System.out.println("Count rows that inserted: "+n);

    }

    private static void deleteDataPrice() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new Price ID value: ");
        String priceID = input.next();
        System.out.println("Input a Price value for delete: ");
        String price = input.next();

        PreparedStatement preparedStatement;
        preparedStatement=connection.prepareStatement("DELETE FROM Price WHERE ID_Price =? AND Price=?");
        preparedStatement.setString(1, priceID);
        preparedStatement.setString(2, price);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that deleted: " + n);
    }

    private static void callProcedureForInsertToAppleWarehouse() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput Warehouse Name for Warehouse: ");
        String WarehouseName = input.next();
        System.out.println("Input Apple Name for Apple: ");
        String AppleName = input.next();

        CallableStatement callableStatement;
        callableStatement = connection.prepareCall("{call InsertAppleWarehouse(?, ?)}");
        callableStatement.setString("WarehouseNameIn",WarehouseName);
        callableStatement.setString("AppleNameIN",AppleName);
        ResultSet rs = callableStatement.executeQuery();

        while (rs.next())
        {
            String msg = rs.getString(1);
            System.out.format("\nResult: "+msg);
        }
    }
}