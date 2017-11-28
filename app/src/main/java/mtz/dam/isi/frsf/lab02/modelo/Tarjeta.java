package mtz.dam.isi.frsf.lab02.modelo;

import java.io.Serializable;
import java.util.Date;


public class Tarjeta implements Serializable {
    private String nombre;
    private Integer numero;
    private Integer seguridad;
    private Date fechaVencimiento;

    public Tarjeta() {
    }

    public Tarjeta(String nombre, Integer numero, Integer seguridad, Date fechaVencimiento) {
        this.nombre = nombre;
        this.numero = numero;
        this.seguridad = seguridad;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Tarjeta(String nombre) {
        this.nombre = nombre;
    }

    public Tarjeta(Integer numero) {
        this.numero = numero;
    }

    public Tarjeta(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getNumero() {
        return numero;
    }

    public Integer getSeguridad() {
        return seguridad;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setSeguridad(Integer seguridad) {
        this.seguridad = seguridad;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return "Tarjeta{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}