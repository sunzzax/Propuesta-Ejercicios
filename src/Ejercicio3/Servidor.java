/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Ejercicio3;

import java.io.*;
import java.net.*;

public class Servidor {

    public static void main(String[] args) {

        int puerto = 6001;
        String mensaje = "";

        try (ServerSocket servidor = new ServerSocket(puerto)) {

            try (Socket clienteConectado = servidor.accept()) {

                try (DataInputStream entradaDatos = new DataInputStream(clienteConectado.getInputStream()); 
                        DataOutputStream salidaDatos = new DataOutputStream(clienteConectado.getOutputStream())) {

                    System.out.println("Cliente conectado.");
                    
                    // Hago un bucle infinito que se seguira ejecutando mientras que el mensaje que yo escriba no sea 'salir'
                    while (!mensaje.equals("salir")) {
                        mensaje = entradaDatos.readUTF();
                        
                        // Muestro la palabra que me ha enviado el cliente.
                        System.out.print("Dato recibido del cliente:");
                        System.out.println(mensaje);

                        
                        // Si esta vacío le envio al usuario un mensaje indicandole que necesito una palabra
                        if (mensaje.isEmpty()) {
                            salidaDatos.writeUTF("No has introducido nada. Se necesita una palabra...");
                        } else {
                            
                            // Declaro la variable 'palabra', esta la usaré para luego añadir letra por letra la palabra
                            // que he recibido del cliente
                            String palabra = ""; 

                            
                            // Hago un bucle 'for' para recorrer la palabra que el cliente ha escrito, letra por letra
                            for (int i = 0; i < mensaje.length(); i++) {

                                // Creo una variable 'letra' que obtendra en cada pasada del bucle la letra en la 
                                // posición de 'i'
                                char letra = mensaje.charAt(i);

                                // Aqui hago las comprobaciónes. Si el caracter que almacena 'letra' es realmente
                                // un caracter, se le suma 3.
                                if (Character.isLetter(letra)) {
                                    letra += 3;

                                    
                                    // Aqui compruebo dos situaciones, una en la que la letra sea minuscula y otra
                                    // por si es mayuscula.
                                    
                                    // Si es miniscula y supera la letra 'z' se le resta 26 para volver hacia atras, porque
                                    // en la tabla ascii si contamos hay 26 letras
                                    if (Character.isLowerCase(mensaje.charAt(i)) && letra > 'z') {
                                        letra -= 26;
                                        
                                        // Y si es mayuscula pues lo mismo, pero con mayusculas...
                                    } else if (Character.isUpperCase(mensaje.charAt(i)) && letra > 'Z') {
                                        letra -= 26;
                                    }

                                    // Despues de haber comprobado esas dos condiciones se le pasa a palabra el
                                    // caracter para que vaya almacenandolos uno por uno en cada vuelta del bucle
                                    palabra += letra;

                                    // Pero si por ejemplo no es una letra lo que hago es añadirle a palabra un espacio
                                    // en blanco, porque sino lo que hacia es que al poner otra palabra salia
                                    // mezclado con lo anterior o simplemente salia otra cosa completamente distinta...
                                } else {
                                    palabra += "";
                                }

                            }
                            
                            
                            // FUERA DEL BUCLE
                            
                            // Si escribo 'salir' el porgrama termina, como arriba en el 'while' hemos dicho que si lo que 
                            // escribo es igual a 'salir' entonces no se ejecutará y de esa manera acaba
                            if (mensaje.equals("salir")) {
                                salidaDatos.writeUTF("Fin del Programa.");
                                
                                // Sino imprime la palabra
                            } else {
                                salidaDatos.writeUTF(palabra);
                            }

                        }

                    }

                } catch (IOException ex) {
                    System.err.println("Error. No se ha podido conectar con el cliente. " + ex.getMessage());
                }
            } catch (IOException ex) {
                System.err.println("Error. No se ha podido realizar la conexión por el puerto: " + puerto + ". " + ex.getMessage());
            } catch (StringIndexOutOfBoundsException ex) {
                System.err.println("Error: No se ha recibido ningún carácter. Puede que el mensaje esté vacío.");
            }

        } catch (IOException ex) {
            System.err.println("Error. Nos se ha podido iniciar el servidor. " + ex.getMessage());
        }
    }
}
