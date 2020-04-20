import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ChatServer {

    ArrayList<String> loginNames;
    ArrayList<Socket> clientSockets;

    ChatServer() throws IOException {

        loginNames = new ArrayList<>();
        clientSockets = new ArrayList<>();

        ServerSocket server = new ServerSocket(5217);
        while(true) {
            Socket clientSocket = server.accept();
            Client client = new Client(clientSocket);
        }
    }

    class Client extends Thread {

        Socket clientSocket;

        DataInputStream in;
        DataOutputStream out;

        int pos = 0;
        int i = 0;

         Client(Socket client) throws IOException {
            clientSocket = client;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            String loginName = in.readUTF();

            System.out.println("Login name: " + loginName);

            loginNames.add(loginName);
            clientSockets.add(clientSocket);

            start();
        }

        public void run() {
             while(true) {
                 try {
                     String msgFromCLIENT = in.readUTF();

                     StringTokenizer msgParts = new StringTokenizer(msgFromCLIENT);

                     String name = msgParts.nextToken();
                     String msgType = msgParts.nextToken();

                     StringBuffer messageBuffer = new StringBuffer();

                     while(msgParts.hasMoreTokens())
                         messageBuffer.append(" " + msgParts.nextToken());

                     final String message = messageBuffer.toString();


                     switch (msgType) {
                         case "LOGIN":
                             clientSockets.forEach(socket -> {
                                 notifyLogin(socket, name);
                             });
                             break;
                         case "LOGOUT":
                             clientSockets.forEach(socket -> {
                                 performLogout(socket,name);
                                 if(name.equals(loginNames.get(i++)))
                                     pos = i-1;
                             });
                             loginNames.remove(pos);
                             clientSockets.remove(pos);
                             break;
                         default:
                             clientSockets.forEach(socket -> {
                                 notifyMessage(socket, name, message);
                             });
                     }

                     if(msgType.equals("LOGOUT"))
                         break;

                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }

        }

    }


    public static void main(String[] args) throws IOException {
        new ChatServer();
    }

    private void performLogout(Socket socket, String name) {

        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(name + " has logged out");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyMessage(Socket socket, String name, String message) {

        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(name + ": " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyLogin(Socket socket, String name) {

        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(name + " has logged in");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
