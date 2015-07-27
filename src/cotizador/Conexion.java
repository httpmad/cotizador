package cotizador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class Conexion {
    //"jdbc:firebirdsql://localhost:3050/C:\\firebird\\database\\EMPLOYEE.FDB"
    private final static String DRIVER = "org.firebirdsql.jdbc.FBDriver";
    //private final static String DIRECCION_MYSQL = "jdbc:mysql://localhost:3306/catmail";
    private final static String DIRECCION_FIREBIRD = "jdbc:firebirdsql:192.168.100.7/3050:C:/a.FDB";
    private final static String USUARIO = "SYSDBA";
    private final static String PASSWORD = "masterkey";
    
    public static Conexion instance;
    private Connection conexionDB;

    private Conexion() {
        try {
            Class.forName(DRIVER); //driver jdbc
            conexionDB = DriverManager.getConnection(
                    DIRECCION_FIREBIRD,USUARIO,PASSWORD);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            throw new IllegalStateException("Cannot connect the database!", ex);
        }
    }
    
    public synchronized static Conexion dameConexion(){
        if(instance == null){
            instance = new Conexion();
        }
        return instance;
    }

    public Connection getConexionDB() {
        return conexionDB;
    }
    
    public void cerrarConexion(){
        //conexionDB.close();
        instance = null;
    }
    
}
