package Ejercicio1;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServidorTrivial {

    public static void main(String[] args) {

        int puerto = 6000;

        System.out.println("Servidor Iniciado...");

        try (ServerSocket servidor = new ServerSocket(puerto)) {

            try (Socket clienteConectado = servidor.accept()) {

                System.out.println("Cliente conectado: " + clienteConectado.getInetAddress());

                try (DataInputStream entradaDatos = new DataInputStream(clienteConectado.getInputStream()); 
                        DataOutputStream salidaDatos = new DataOutputStream(clienteConectado.getOutputStream())) {

                    ArrayList<Pregunta> preguntas = new ArrayList<>();
                    preguntas.add(new Pregunta("¿Cuál es la capital de Francia?", "parís"));
                    preguntas.add(new Pregunta("¿Cuánto es 10 +5?", "15"));
                    preguntas.add(new Pregunta("¿A que comunidad autónoma pertence Almería?", "andalucía"));
                    preguntas.add(new Pregunta("¿Cómo se llama la aplicación para subir las tareas?", "moodle"));

                    boolean esVerdad = true;
                    int contador = 0;
                    String respuesta = "";
                    
                    while (esVerdad && contador < 4) {
                        salidaDatos.writeUTF("Servidor: " + preguntas.get(contador).pregunta);

                         respuesta = entradaDatos.readUTF();

                        if (respuesta.equals("salir".trim())) {
                            esVerdad = false;
                            salidaDatos.writeUTF("Servidor: FIN DEL JUEGO. Gracias por participar.");
                            
                        } else if (respuesta.startsWith("respuesta:")) {
                            
                            if (respuesta.substring("respuesta:".length()).trim().toLowerCase().equals(preguntas.get(contador).respuesta)) {
                                salidaDatos.writeUTF("Servidor: CORRECTO");
                            } else {
                                salidaDatos.writeUTF("Servidor: INCORRECTO. La respuesta correcta era: " + preguntas.get(contador).respuesta);
                            }
                            
                        } else {
                            salidaDatos.writeUTF("Servidor: Debes utilizar este formato, (respuesta:xxx).");
                        }

                        contador++;

                    }
                    if (contador == 4) {
                        salidaDatos.writeUTF("Servidor: FIN DEL JUEGO. Gracias por participar.");
                    }

                } catch (IOException ex) {
                    System.err.println("Error. No se ha podido leer o enviar datos al Cliente. " + ex.getMessage());
                }

            } catch (IOException ex) {
                System.err.println("Error. No se ha podido conectar con el Cliente. " + ex.getMessage());
            }

        } catch (IOException ex) {
            System.err.println("Error. No se ha podido iniciar el servidor. " + ex.getMessage());
        }

    }
}
