import java.sql.*;

public class JavaConnector {
    String url = "jdbc:mysql://localhost:3306/aeroplastics";
    String userName = "root";
    String pass = "Bob4oSirop4o";
    private Connection connection;

    public JavaConnector(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, userName, pass);


        }catch(NumberFormatException numF1) {

        }catch(Exception e1) {
            e1.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }


}

