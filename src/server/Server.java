package server;

import controller.GetRequestHandler;
import controller.IHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final int port = 8080;
    private final ServerSocket serverSocket = new ServerSocket(port);
    private static final Map<String, IHandler> handlers = new HashMap<>(Map.of(
            "GET", new GetRequestHandler()
    ));

    public Server() throws IOException {}

    public void Start() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected");
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String requestLine = in.readLine();
            System.out.println(requestLine);
            if (requestLine == null) {
                clientSocket.close();
                continue;
            }

            String[] requestParts = requestLine.split(" ");
            String requestType = requestParts[0];

            IHandler handler = handlers.get(requestType);
            if (handler != null) {
                System.out.println("hanlder not null");
                handler.handleRequest(clientSocket, requestLine);
            } else {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("HTTP/1.1 405 Method Not Allowed");
                out.println("Content-Type: text/plain");
                out.println("Content-Length: 16");
                out.println();
                out.println("Method Not Allowed");
                out.flush();
                clientSocket.close();
            }
        }
    }
}
