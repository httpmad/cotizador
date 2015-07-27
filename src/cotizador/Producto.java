/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cotizador;

import java.io.Serializable;

/**
 *
 * @author daniel
 */
public class Producto implements Serializable{
    private String nombre;
    private String precio;
    private String nombreEmpresa;
    private String Costo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getCosto() {
        return Costo;
    }

    public void setCosto(String Costo) {
        this.Costo = Costo;
    }

    public Producto(String nombre, String precio, String nombreEmpresa, String Costo) {
        this.nombre = nombre;
        this.precio = precio;
        this.nombreEmpresa = nombreEmpresa;
        this.Costo = Costo;
    }
}
