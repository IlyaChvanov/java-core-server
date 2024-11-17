package controller;

import java.io.IOException;
import java.net.Socket;

public interface IHandler {
    void handleRequest(Socket clientSocket, String requestLine) throws IOException;
}
