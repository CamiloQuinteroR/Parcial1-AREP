/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parcial1.arep;

/**
 *
 * @author CAMILO.QUINTERO-R
 */
import java.net.*;
import java.io.*;

public class FacadeBack {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(40000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 40000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {

            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean firtsLine = true;
            String uri = "";
            while ((inputLine = in.readLine()) != null) {
                if(firtsLine){
                   System.out.println("Recibi: " + inputLine);
                   uri = inputLine.split(" ")[1];
                   firtsLine = false;
                   
                }
                
                if (!in.ready()) {
                    break;
                }
            }
            
            URI requestURI = new URI(uri);
            String respuesta = logica(requestURI.getQuery().split("//?")[1]);
            outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + respuesta;
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();

        }
        serverSocket.close();
    }
    
    public static String logica(String query){
        return "";
    }
}
