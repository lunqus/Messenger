import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;

public class ChatClient extends JFrame {

    String loginName;

    JTextArea messages;
    JTextField sendMessage;

    JButton send;
    JButton logout;

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
