import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.UnknownHostException;

public class Login {

    static void startChat(JFrame login, String loginName) {
        // System.out.println(loginName);

        try {
            ChatClient client = new ChatClient(loginName);
            login.setVisible(false);
            login.dispose();
        } catch (UnknownHostException e ) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        var login = new JFrame("Login"); // Window we're gonna be seeing
        var panel = new JPanel(); // Holds our components
        var loginName = new JTextField(20); // Field where enter name
        var enterBtn = new JButton("Login"); // Actual button

        panel.add(loginName); // Adding textfield for login name to panel
        panel.add(enterBtn); // Adding enter button to the panel

        login.setSize(400, 150);
        login.add(panel);
        login.setVisible(true);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        enterBtn.addActionListener(event -> {
            startChat(login, loginName.getText());
        });

        loginName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    startChat(login, loginName.getText());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

}
