import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends JFrame {

    String loginName;

    JTextArea messages;
    JTextField sendMessage;

    JButton send;
    JButton logout;

    DataInputStream in;
    DataOutputStream out;

    public ChatClient(String loginName) throws UnknownHostException, IOException {
        super(loginName);
        this.loginName = loginName;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // super.windowClosing(e);
            }
        });

        messages = new JTextArea(18,50);
        messages.setEditable(false);
        sendMessage = new JTextField(50);

        send = new JButton("Send");
        logout = new JButton("Logout");

        send.addActionListener(event -> {
            try {
                if(sendMessage.getText().length() > 0)
                    out.writeUTF(loginName + " DATA " + sendMessage.getText());

                sendMessage.setText("");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        logout.addActionListener(event -> {
            try {
                if(sendMessage.getText().length() > 0)
                    out.writeUTF(loginName + " LOGOUT ");

                System.exit(1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Socket socket = new Socket("127.0.0.1", 5217);

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        out.writeUTF(loginName);

        setup();
    }

    private void setup() {
        setSize(600,400);

        JPanel panel = new JPanel();
        panel.add(new JScrollPane(messages));

        panel.add(sendMessage);
        panel.add(send);
        panel.add(logout);
        add(panel);

        setVisible(true);
    }
}
