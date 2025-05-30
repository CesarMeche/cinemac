package co.edu.uptc.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import co.edu.uptc.structures.avltree.AVLTree;
import co.edu.uptc.structures.avltree.AVLTreeDeserializer;
import co.edu.uptc.structures.avltree.AVLTreeSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConectionManager {

    private Socket socket;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private ObjectMapper mapper;

    public ConectionManager(Socket socket) {
        this.socket = socket;

        // 👉 Inicializa ObjectMapper con soporte para LocalDateTime y tu AVLTree
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Registra los serializers/deserializers de AVLTree si es necesario
        SimpleModule module = new SimpleModule();
        module.addSerializer(AVLTree.class, new AVLTreeSerializer());
        module.addDeserializer(AVLTree.class, new AVLTreeDeserializer());
        this.mapper.registerModule(module);

        try {
            this.dataInput = new DataInputStream(this.socket.getInputStream());
            this.dataOutput = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(JsonResponse<?> message) throws IOException{
      
            String jsonMessage = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);
            dataOutput.writeUTF(jsonMessage);
            dataOutput.flush();
      
    }

    public <T> JsonResponse<T> receiveMessage(Class<T> clazz) throws IOException {
        String jsonMessage = dataInput.readUTF();
        // Para que Jackson deserialice JsonResponse<T> correctamente
        TypeReference<JsonResponse<T>> typeRef = new TypeReference<JsonResponse<T>>() {};
        return mapper.readValue(jsonMessage, typeRef);
    }

    public JsonResponse<?> receiveMessage() throws IOException {
        String jsonMessage = dataInput.readUTF();
        return mapper.readValue(jsonMessage, JsonResponse.class);
    }

    public <T> JsonResponse<T> convertData(JsonResponse<?> response, Class<T> classType) {
        try {
            // Convierte el campo "data" a JSON y luego a la clase deseada
            String jsonData = mapper.writeValueAsString(response.getData());
            T convertedData = mapper.readValue(jsonData, classType);
            return new JsonResponse<>(response.getStatus(), response.getMessage(), convertedData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            if (dataInput != null)
                dataInput.close();
            if (dataOutput != null)
                dataOutput.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
