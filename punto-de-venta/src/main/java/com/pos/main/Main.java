package com.pos.main;

import com.pos.service.TiendaService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TiendaService servicio = new TiendaService();
        Scanner scanner = new Scanner(System.in);

        try {
            servicio.inicializar(); // Crea las tablas si no existen

            while (true) {
                System.out.println("\n=== PUNTO DE VENTA JAVA ===");
                System.out.println("1. Alta de Producto");
                System.out.println("2. Ver Inventario");
                System.out.println("3. Realizar Venta");
                System.out.println("4. Ver Historial de Ventas");
                System.out.println("5. Salir");
                System.out.print("Elige una opción: ");

                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Precio: ");
                        double precio = scanner.nextDouble();
                        System.out.print("Stock inicial: ");
                        int stock = scanner.nextInt();
                        servicio.agregarProducto(nombre, precio, stock);
                        break;
                    case 2:
                        servicio.mostrarInventario();
                        break;
                    case 3:
                        System.out.print("ID del producto a vender: ");
                        int id = scanner.nextInt();
                        System.out.print("Cantidad: ");
                        int cant = scanner.nextInt();
                        servicio.vender(id, cant);
                        break;
                    case 4:
                        servicio.mostrarVentas();
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        return;
                    default:
                        System.out.println("Opción no válida.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
