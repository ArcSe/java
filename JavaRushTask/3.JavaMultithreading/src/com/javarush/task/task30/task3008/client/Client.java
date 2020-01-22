package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.Connection;
import com.javarush.task.task30.task3008.ConsoleHelper;
import com.javarush.task.task30.task3008.Message;
import com.javarush.task.task30.task3008.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client {
     protected Connection connection;
     private volatile boolean clientConnected = false;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

     public void run(){
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();
        try{
            synchronized (this){
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            ConsoleHelper.writeMessage("Ошибка");
        }

        if(clientConnected){
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
        }
        else {
            ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");
        }

        while (true){
            String text = ConsoleHelper.readString();
            if(text.equals("exit") || !clientConnected){
               break;
            }
            if(shouldSendTextFromConsole()){
                sendTextMessage(text);
            }
        }
     }

     protected String getServerAddress(){
         ConsoleHelper.writeMessage("Введите адрес:");
         return ConsoleHelper.readString();
    }

    protected int getServerPort(){
        ConsoleHelper.writeMessage("Введите порт");
        return ConsoleHelper.readInt();
    }

    protected String getUserName()
    {
        ConsoleHelper.writeMessage("Введите имя");
         return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole(){
         return true;
    }

    protected SocketThread getSocketThread(){
         return new SocketThread();
    }

    protected void sendTextMessage(String text){
         String newText = text;
         try{
             connection.send(new Message(MessageType.TEXT, newText));
         }
         catch (IOException e){
             ConsoleHelper.writeMessage("Ошибка отправки");
             clientConnected = false;
         }
    }
    //---------------------------------------------------------------------------------------------------------------
    //Отвечает за поток, устанавливающий сокетное соединение и читающий сообщения сервера.
    public class SocketThread extends Thread{
        @Override
        public void run() {
            String address = getServerAddress();
            int port = getServerPort();
            try {
                Socket socket = new Socket(address, port);
                connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                notifyConnectionStatusChanged(false);
            }
        }

        //Вывод в консоль собщения
        protected void processIncomingMessage(String message){
            ConsoleHelper.writeMessage(message);
        }

        //Вывод в консоль информации о присоединении участника
        protected void informAboutAddingNewUser(String userName){
            ConsoleHelper.writeMessage(userName + " присоединился к чату.");
        }

        //Вывод в консоль информации об уходе участника
        protected void informAboutDeletingNewUser(String userName){
            ConsoleHelper.writeMessage(userName + " покинул беседу");
        }

        //Оповещение(пробуждение ожидающего) основной поток класса
        protected void notifyConnectionStatusChanged(boolean clientConnected){
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this){
                Client.this.notify();
            }
        }

        //Представление клиента серверу
        protected void clientHandshake() throws IOException, ClassNotFoundException{
            while (true){
                Message message = connection.receive();
                if(message.getType() == MessageType.NAME_REQUEST){
                    connection.send(new Message(MessageType.USER_NAME, getUserName()));
                }
                else if(message.getType() == MessageType.NAME_ACCEPTED){
                    notifyConnectionStatusChanged(true);
                    break;
                }
                else {
                    throw new IOException("Unexpected MessageType");
                }

            }
        }

        //Главный цикл обработки сообщений сервера
        protected void clientMainLoop() throws IOException, ClassNotFoundException{
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    processIncomingMessage(message.getData());
                } else if (message.getType() == MessageType.USER_ADDED) {
                    informAboutAddingNewUser(message.getData());
                } else if (message.getType() == MessageType.USER_REMOVED) {
                    informAboutDeletingNewUser(message.getData());
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }

        }

    }
}
