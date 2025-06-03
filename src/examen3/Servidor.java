package examen3;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static void main(String[] args) {

        int puerto = 7000;

        try (ServerSocket servidor = new ServerSocket(puerto)) {

            try (Socket clienteConectado = servidor.accept()) {

                try (DataInputStream entradaDatos = new DataInputStream(clienteConectado.getInputStream()); 
                        DataOutputStream salidaDatos = new DataOutputStream(clienteConectado.getOutputStream())) {

                    
                    String fecha = entradaDatos.readUTF();
                    String[] partes = fecha.split("/");
                    
                    int dia = Integer.parseInt(partes[0]);
                    int mes = Integer.parseInt(partes[1]);
                    int anio = Integer.parseInt(partes[2]);
                    
                    if(esFechaValida(dia, mes, anio)){
                        salidaDatos.writeUTF("La fecha " + fecha  + " es valida.");
                    } else {
                        salidaDatos.writeUTF("La fecha " + fecha  + " NO es valida.");
                    }
                    
                    System.out.println("Cliente desconectado. Cerrando servidor...");
                    
                } catch (IOException ex) {
                    System.err.println("Error. No se ha podido enviar/recibir datos." + ex.getMessage());
                }

            } catch (IOException ex) {
                System.err.println("Error. No se ha podido conectar con el cliente. " + ex.getMessage());
            }

        } catch (IOException ex) {
            System.err.println("Error. No se ha podido realizar la conexión por el puerto: " + puerto
                    + ". " + ex.getMessage());
        }

    }

    public static boolean esFechaValida(int dia, int mes, int anio){
        boolean valido = false;
        
        if(mes >= 1 && mes <= 12){
            switch (mes) {
                case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                    if(dia >= 1 && dia <= 31){
                        valido = true;
                    }
                    
                case 4: case 6: case 9: case 11:
                    if(dia >=1 && dia <=30){
                        valido = true;
                    }
                    
                default:
                    if(esBisiesto(anio)){
                        if(dia >= 1 && dia <= 29){
                            valido = true;
                        } else{
                            if(dia >= 1 && dia <= 28){
                                
                            }
                        }
                    }
            }
        }
        
        return valido;
        
    }
    
    public static boolean esBisiesto(int anyo) {
        boolean valido = false;
        if ((anyo % 4 == 0 && anyo % 100 != 0) || (anyo % 400 == 0)) {
            valido = true;
        }
        return valido;
    }

}
