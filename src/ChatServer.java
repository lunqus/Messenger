import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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

         Client(Socket client) throws IOException {
            clientSocket = client;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            String loginName = in.readUTF();

            System.out.println("Login name: " + loginName);

            loginNames.add(loginName);
            clientSockets.add(clientSocket);


        }
    }

    public static void main(String[] args) throws IOException {
        new ChatServer();
    }
}
