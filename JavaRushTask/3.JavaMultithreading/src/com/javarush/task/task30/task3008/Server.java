package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
- Сервер создает серверное сокетное соединение.
- В цикле ожидает, когда какой-то клиент подключится к сокету.
- Создает новый поток обработчик Handler, в котором будет происходить обмен сообщениями с клиентом.
- Ожидает следующее соединение.
 */

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    //Рассылка всем участникам чата сообщение
    public static void sendBroadcastMessage(Message message){
        for (Map.Entry<String, Connection> connection: connectionMap.entrySet()) {
            try {
                connection.getValue().send(message);
            }catch (IOException e){
                System.out.println("Ошибка в отправке сообщения!");
            }
        }
    }


    private static class Handler extends Thread{
        private Socket socket;

        public void run(){
            ConsoleHelper.writeMessage("Установленно новое соединение с удаленным адресом " + socket.getRemoteSocketAddress());
            String userName = null;
            try(Connection connection = new Connection(socket)){
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                notifyUsers(connection, userName);
                serverMainLoop(connection, userName);
            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом");
            }
            finally {
                if(userName != null){
                    connectionMap.remove(userName);
                    sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
                }
            }

        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    String newText = userName + ": " + message.getData();

                    sendBroadcastMessage(new Message(MessageType.TEXT, newText));

                } else {
                    ConsoleHelper.writeMessage("Нe правильный формат сообщения отличный от \"TEXT\"!");
                }
            }
        }

        //Рассылка всем участникам чата информацию об имени присоединившегося участника
        private void notifyUsers(Connection connection, String userName) throws IOException{
            for (Map.Entry<String, Connection> connections : connectionMap.entrySet()) {
                String name = connections.getKey();
                if(!name.equals(userName)) {
                    connection.send(new Message(MessageType.USER_ADDED, name));
                }

            }
        }

        // Метод, реализующий рукопожатие с клиентом, сохраняя имя нового клиента
        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            connection.send(new Message(MessageType.NAME_REQUEST));
            Message answer = connection.receive();
            String userName = answer.getData();
            if (answer.getType() != MessageType.USER_NAME || userName.isEmpty() || connectionMap.containsKey(userName)) {
                return serverHandshake(connection);
            }
            connectionMap.put(userName, connection);
            connection.send(new Message(MessageType.NAME_ACCEPTED));

            return userName;

        }

        public Handler(Socket socket) {
            this.socket = socket;
        }
    }
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt());
        System.out.println("Server has started");
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Handler handler = new Handler(clientSocket);
                handler.start();
            }
        }
        catch (IOException e) {
            System.out.println(" произошла ошибка.");
            serverSocket.close();
        }


    }
}
