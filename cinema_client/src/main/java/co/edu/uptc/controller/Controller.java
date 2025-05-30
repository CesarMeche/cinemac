package co.edu.uptc.controller;

import java.io.IOException;
import java.net.Socket;

import co.edu.uptc.network.ConectionManager;
import co.edu.uptc.network.JsonResponse;
import co.edu.uptc.view.MainFrame;

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
        MainFrame lf= new MainFrame(this);
        lf.init();
    }

    public <T> void sendMsg(String type, String status, T data) {
        try {
            JsonResponse<T> response = new JsonResponse<>(type, status, data);
            conectionManager.sendMessage(response);
        } catch (Exception e) {
            e.printStackTrace();
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

    public <T> JsonResponse<T> reciveMsg(Class<T> clazz) {
        try {
            JsonResponse msg = conectionManager.receiveMessage();
            msg = conectionManager.convertData(msg, clazz);
            return msg;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}