package com.pos.service;

import com.pos.dao.ProductoDAO;
import com.pos.dao.VentaDAO;
import com.pos.model.Producto;
import com.pos.model.Venta;

import java.sql.SQLException;
import java.util.List;

public class TiendaService {
    private ProductoDAO productoDAO = new ProductoDAO();
    private VentaDAO ventaDAO = new VentaDAO();

    public void inicializar() throws SQLException {
        productoDAO.crearTabla();
        ventaDAO.crearTabla();
    }

    public void agregarProducto(String nombre, double precio, int stock) throws SQLException {
        Producto p = new Producto(nombre, precio, stock);
        productoDAO.guardar(p);
        System.out.println("✅ Producto '" + nombre + "' agregado.");
    }

    public void vender(int idProducto, int cantidad) throws SQLException {
        // 1. Verificar si existe y hay stock
        Producto producto = productoDAO.buscarPorId(idProducto);

        if (producto == null) {
            System.out.println("❌ Error: Producto no encontrado.");
            return;
        }
        if (producto.getStock() < cantidad) {
            System.out.println("❌ Error: Stock insuficiente. Solo hay " + producto.getStock());
            return;
        }

        // 2. Calcular total
        double total = producto.getPrecio() * cantidad;

        // 3. Registrar Venta
        Venta venta = new Venta(producto.getId(), producto.getNombre(), cantidad, total);
        ventaDAO.registrar(venta);

        // 4. Descontar Stock
        productoDAO.actualizarStock(producto.getId(), producto.getStock() - cantidad);

        System.out.println("✅ Venta registrada. Total: $" + total);
    }

    public void mostrarInventario() throws SQLException {
        List<Producto> productos = productoDAO.listar();
        System.out.println("\n--- INVENTARIO ---");
        for (Producto p : productos) {
            System.out.println("ID: " + p.getId() + " | " + p.getNombre() + " - $" + p.getPrecio() + " (Stock: " + p.getStock() + ")");
        }
        System.out.println("--------------------\n");
    }

    public void mostrarVentas() throws SQLException {
        List<Venta> ventas = ventaDAO.historial();
        System.out.println("\n--- HISTORIAL DE VENTAS ---");
        for (Venta v : ventas) {
            System.out.println("Venta ID: " + v.getId() + " | " + v.getNombreProducto() + " x" + v.getCantidad() + " = $" + v.getTotal());
        }
        System.out.println("---------------------------\n");
    }
    // Agregando funcionalidad con nuevo metodo
    public void buscarProducto(String termino) throws SQLException {
        List<Producto> productos = productoDAO.listar();
        boolean encontrado = false;

        System.out.println("\n--- RESULTADOS DE BÚSQUEDA ---");
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(termino.toLowerCase())) {
                System.out.println("ID: " + p.getId() + " | " + p.getNombre() + " - $" + p.getPrecio() + " (Stock: " + p.getStock() + ")");
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron productos con '" + termino + "'");
        }
        System.out.println("--------------------------------\n");
    }

}
