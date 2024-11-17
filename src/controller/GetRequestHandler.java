package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GetRequestHandler implements IHandler {
    @Override
    public void handleRequest(Socket clientSocket, String requestLine) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        System.out.println("Im in get hadnler!");
        System.out.println("request is: " + requestLine);

        String[] requestParts = requestLine.split(" ");
        String method = requestParts[0];
        String path = requestParts[1];

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println("Content-Length: 22");
        out.println();
        out.println("<h1>Hello, World!</h1>");
        out.flush();

        clientSocket.close();
    }
}
