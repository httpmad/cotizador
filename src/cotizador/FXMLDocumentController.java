/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cotizador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author daniel
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField nombreProducto;
    @FXML
    private TextField precioProducto;
    @FXML
    private TextArea descripcionProducto;
    @FXML
    private ListView listaDeProductos;
    
    private ObservableList observableList = FXCollections.observableArrayList();

    //private final static Conexion CONEXION = Conexion.dameConexion();
    
    
    private final static String SQL_QUERY = "select ARTICULOS.NOMBRE, PRECIOS_ARTICULOS.PRECIO, PRECIOS_EMPRESA.NOMBRE, ARTICULOS.COSTO_ULTIMA_COMPRA from ((PRECIOS_ARTICULOS INNER JOIN ARTICULOS ON PRECIOS_ARTICULOS.ARTICULO_ID = ARTICULOS.ARTICULO_ID) INNER JOIN PRECIOS_EMPRESA ON PRECIOS_ARTICULOS.PRECIO_EMPRESA_ID=PRECIOS_EMPRESA.PRECIO_EMPRESA_ID) WHERE PRECIOS_EMPRESA.NOMBRE = 'Precio de lista' order by(ARTICULOS.NOMBRE)";
    
    private ArrayList<Producto> lista = new ArrayList<>();
    
    @FXML 
    private void guardarProductos(){
        try {
            FileOutputStream archivo = new FileOutputStream("productos.dan");
            ObjectOutputStream objeto = new ObjectOutputStream(archivo);
            objeto.writeObject(lista);
            objeto.close();    
            archivo.close();
            System.out.println("PRODUCTOS GUARDADOS");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @FXML
    private void abrirProductos(){
        FileInputStream archivo = null;
        try {
            archivo = new FileInputStream("productos.dan");
            ObjectInputStream objeto = new ObjectInputStream(archivo);
            lista = (ArrayList<Producto>) objeto.readObject();
            objeto.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualizarLista();
    }
    
    @FXML
    private void agregarProducto() {

        
        listaDeProductos.setItems(observableList);
    }
    
    HBox cajaContenedora(String nombre,String precio){
        HBox caja = new HBox(new Label(nombre),new Label(precio));
        return caja;
    }
    
    @FXML
    private void abrirDB(){
        PreparedStatement ordenSQL;
        ResultSet resultado;
        Conexion conexion  = Conexion.dameConexion();
        try {
            ordenSQL = conexion.getConexionDB().prepareStatement(SQL_QUERY);
            resultado = ordenSQL.executeQuery();
            lista = new ArrayList<>();
            while (resultado.next()){
               lista.add(
                       new Producto( resultado.getString(1),
                                    resultado.getString(2), 
                                     resultado.getString(3),
                                     resultado.getString(4))
                    );
                
            }
            actualizarLista();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void actualizarLista(){
        for(Producto producto: lista){
            observableList.add(producto.getNombre());
        }
        listaDeProductos.setItems(observableList);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          
    }    
    
}
