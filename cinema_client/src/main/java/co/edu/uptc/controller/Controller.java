package co.edu.uptc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import co.edu.uptc.model.pojos.Movie;
import co.edu.uptc.network.ConectionManager;
import co.edu.uptc.network.JsonResponse;

public class Controller {

    private Socket socket;
    private final String host;
    private final int port;
    private ConectionManager conectionManager;

    public Controller(String host, int port) {
        this.host = host;
        this.port = port;

    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
        conectionManager = new ConectionManager(socket);
    }

    public <T> void sendMsg(String type, String status, T data) {
        try {
            JsonResponse<T> response = new JsonResponse<>(type, status, data);
            conectionManager.sendMessage(response);
        } catch (IOException e) {
            e.printStackTrace();
            // Aquí puedes agregar manejo adicional si es necesario
        }
    }

    public JsonResponse reciveMsg() {
        try {
            JsonResponse msg = conectionManager.receiveMessage();
            return msg;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}