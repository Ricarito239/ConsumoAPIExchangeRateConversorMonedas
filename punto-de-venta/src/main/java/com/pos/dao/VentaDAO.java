package com.pos.dao;

import com.pos.database.DatabaseConnection;
import com.pos.model.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    public void crearTabla() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS ventas (id INT AUTO_INCREMENT PRIMARY KEY, producto_id INT, nombre_producto VARCHAR(100), cantidad INT, total DOUBLE, fecha TIMESTAMP)";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void registrar(Venta venta) throws SQLException {
        String sql = "INSERT INTO ventas (producto_id, nombre_producto, cantidad, total, fecha) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, venta.getProductoId()); // Necesitarás agregar getter en Venta
            pstmt.setString(2, venta.getNombreProducto());
            pstmt.setInt(3, venta.getCantidad());
            pstmt.setDouble(4, venta.getTotal());
            pstmt.setTimestamp(5, Timestamp.valueOf(venta.getFecha()));
            pstmt.executeUpdate();
        }
    }

    public List<Venta> historial() throws SQLException {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ventas ORDER BY fecha DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Venta v = new Venta(rs.getInt("producto_id"), rs.getString("nombre_producto"), rs.getInt("cantidad"), rs.getDouble("total"));
                v.setId(rs.getInt("id"));
                lista.add(v);
            }
        }
        return lista;
    }
}
