package examen4;

import java.io.*;
import java.net.*;

public class ClienteHandler extends Thread {
    private final Socket socket;

    public ClienteHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            Socket cliente = socket;
            DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
            DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream())
        ) {
            // Leemos la fecha enviada por el cliente
            String fecha = flujoEntrada.readUTF();

            // Extraemos el día, mes y año
            String[] partes = fecha.split("/");
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);

            // Verificamos si la fecha es válida
            if (esFechaValida(dia, mes, anio)) {
                flujoSalida.writeUTF("La fecha " + fecha + " es válida.");
            } else {
                flujoSalida.writeUTF("La fecha " + fecha + " no es válida.");
            }

        } catch (IOException ex) {
            System.out.println("Error en la comunicación con el cliente: " + ex.getMessage());
        }
        
//        finally { // AQU  ES DONDE EL CLIENTE CIERRA LA CONEXI N.
//            try {
//                clienteSocket.close();
//            } catch (IOException ex) {
//                System.out.println("Error al cerrar el socket: " + ex.getMessage());
//            }
        
    }

    public static boolean esFechaValida(int dia, int mes, int anio) {
        boolean valido = false;
        if (mes >= 1 && mes <= 12) {
            switch (mes) {
                case 1: case 3: case 5: case 7:
                case 8: case 10: case 12:
                    if (dia >= 1 && dia <= 31) valido = true;
                    break;
                case 4: case 6: case 9: case 11:
                    if (dia >= 1 && dia <= 30) valido = true;
                    break;
                case 2:
                    if (esBisiesto(anio)) {
                        if (dia >= 1 && dia <= 29) valido = true;
                    } else {
                        if (dia >= 1 && dia <= 28) valido = true;
                    }
                    break;
            }
        }
        return valido;
    }

    public static boolean esBisiesto(int anio) {
        return (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0);
    }
}