package examen3;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    public static void main(String[] args) {
        
        String host = "localhost";
        int puerto = 7000;
        Scanner scan = new Scanner(System.in);
        
        try(Socket cliente = new Socket(host, puerto)){
            
            try(DataInputStream entradaDatos = new DataInputStream(cliente.getInputStream());
                    DataOutputStream salidaDatos = new DataOutputStream(cliente.getOutputStream())) {
                
                System.out.println("Introduce la fecha en formato dd/mm/aaaa :");
                
                String fecha = scan.nextLine();
                
                salidaDatos.writeUTF(fecha);
                
                System.out.println("Respuesta del Servidor: " + entradaDatos.readUTF());
                
            } catch (IOException ex) {
                System.err.println("Error. No se ha podido enviar/recibir datos. " + ex.getMessage());
            }
            
        } catch (IOException ex) {
            System.err.println("Error. No se ha podido realizar la conexión con el cliente. " + ex.getMessage());
        }
        
    }
}