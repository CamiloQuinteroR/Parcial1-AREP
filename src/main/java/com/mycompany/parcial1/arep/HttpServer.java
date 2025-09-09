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

public class HttpServer {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
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
            String inputLine;
            String outputLine = "";
            boolean firtsLine = true;
            String uri = "";
            while ((inputLine = in.readLine()) != null) {
                if (firtsLine) {
                    System.out.println("Recibi: " + inputLine);
                    uri = inputLine.split(" ")[1];
                    firtsLine = false;
                    continue;
                }
                if (!in.ready()) {
                    break;
                }
            }
            URI requestURI = new URI(uri);
            
            if(requestURI.getPath().startsWith("/app")){
               outputLine = getOutputLine(); 
            }else{
                outputLine = getOutputLine();
            }

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();

    }

    public static String getOutputLine() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "\n"
                + "<head>\n"
                + "    <title>Parcial 1 AREP </title>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "</head>\n"
                + "\n"
                + "<body>\n"
                + "    <h1>setkv</h1>\n"
                + "    <form action=\"/setkv\">\n"
                + "        <label for=\"name\">Name:</label><br>\n"
                + "        <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n"
                + "        <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n"
                + "    </form>\n"
                + "    <div id=\"getrespmsg\"></div>\n"
                + "\n"
                + "    <script>\n"
                + "        function loadGetMsg() {\n"
                + "            let nameVar = document.getElementById(\"name\").value;\n"
                + "            const xhttp = new XMLHttpRequest();\n"
                + "            xhttp.onload = function () {\n"
                + "                document.getElementById(\"getrespmsg\").innerHTML =\n"
                + "                    this.responseText;\n"
                + "            }\n"
                + "            xhttp.open(\"GET\", \"/setkv?key=\" + nameVar);\n"
                + "            xhttp.send();\n"
                + "        }\n"
                + "    </script>\n"
                + "\n"
                + "</body>\n"
                + "\n"
                + "</html>";
    }
}
