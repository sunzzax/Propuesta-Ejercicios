package Ejercicio2;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorTrivial {

    public static void main(String[] args) {

        int puerto = 6000;

        System.out.println("Servidor Iniciado...");

        ArrayList<Pregunta> preguntas = new ArrayList<>();

        try (BufferedReader bfr = new BufferedReader(new FileReader("./src/Ejercicio2/preguntas.txt"))) {
            String linea;
            while ((linea = bfr.readLine()) != null) {
                // String partes[] = linea.split("\\|"); forma mas rapida, debo usar \\ porque java interpreta | como 'o' (condición)
//                if (partes.length == 2) {
//                    preguntas.add(new Pregunta(partes[0].trim(), partes[1].trim()));
                int separado = linea.indexOf('|'); // busca la posicion en la que se encuentra '|'... si lo encuentra devuelve un numero entero
                // con la posición, por eso aqui abajo he puesto -1 porque si es distinto se añade.
                if(separado != -1){
                    preguntas.add(new Pregunta(linea.substring(0, separado).trim(), linea.substring(separado + 1).trim()));
                }

            }

        } catch (FileNotFoundException ex) {
            System.err.println("Error. No se ha podido encontrar el archivo. " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Error. No se ha podido leer el archivo. " + ex.getMessage());
        }

        try (ServerSocket servidor = new ServerSocket(puerto)) {

            try (Socket clienteConectado = servidor.accept()) {

                System.out.println("Cliente conectado: " + clienteConectado.getInetAddress());

                try (DataInputStream entradaDatos = new DataInputStream(clienteConectado.getInputStream()); 
                        DataOutputStream salidaDatos = new DataOutputStream(clienteConectado.getOutputStream())) {

//                    ArrayList<Pregunta> preguntas = new ArrayList<>();
//                    preguntas.add(new Pregunta("¿Cuál es la capital de Francia?", "parís"));
//                    preguntas.add(new Pregunta("¿Cuánto es 10 +5?", "15"));
//                    preguntas.add(new Pregunta("¿A que comunidad autónoma pertence Almería?", "andalucía"));
//                    preguntas.add(new Pregunta("¿Cómo se llama la aplicación para subir las tareas?", "moodle"));

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
