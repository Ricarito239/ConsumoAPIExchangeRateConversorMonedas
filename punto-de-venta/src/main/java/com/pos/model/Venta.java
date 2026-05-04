package com.pos.model;

import java.time.LocalDateTime;

public class Venta {
    private int id;
    private int productoId;
    private String nombreProducto;
    private int cantidad;
    private double total;
    private LocalDateTime fecha;

    public Venta(int productoId, String nombreProducto, int cantidad, double total) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.total = total;
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters COMPLETOS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProductoId() { return productoId; } // <-- Este faltaba
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public int getCantidad() { return cantidad; } // <-- Este era el del error
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
