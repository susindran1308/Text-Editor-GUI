import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

public class Login extends JPanel implements ActionListener {

    JLabel userL = new JLabel("Username:");
    JTextField userTF = new JTextField();
    JLabel passL = new JLabel("Password:");
    JPasswordField passTF = new JPasswordField();
    JButton login = new JButton("login");
    JButton register = new JButton("register");

    JPanel loginP = new JPanel(new GridLayout(3,2));
    JPanel panel = new JPanel();

    CardLayout cl;

    Login()
    {
        setLayout(new CardLayout());
        loginP.add(userL);
        loginP.add(userTF);
        loginP.add(passL);
        loginP.add(passTF);
        loginP.add(login);
        loginP.add(register);

        login.addActionListener(this);
        register.addActionListener(this);

        panel.add(loginP);
        add(panel, "login");

        cl = (CardLayout)getLayout();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == register)
        {
            add(new Register(), "register");
            cl.show(this, "register");
        }
        else if(e.getSource() == login)
        {
            BufferedReader input;
            try {
                input = new BufferedReader(new FileReader("/media/susindran/Softwares&Tutorials/JAVA SAMPLES ZOHO/Text Editor GUI/src/passwords.txt"));
                String pass=null;
                String line;

                while ((line=input.readLine()) != null)
                {
                    StringTokenizer tokenizer = new StringTokenizer(line);

                    if(userTF.getText().equals(tokenizer.nextToken()))
                    {
                        pass = tokenizer.nextToken();
                    }
                }
                input.close();
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(new String(passTF.getPassword()).getBytes());
                byte[] byteData = md.digest();

                StringBuffer sb = new StringBuffer();
                for(int i=0; i<byteData.length; i++)
                {
                    sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
                }
                if (pass.equals(sb.toString()))
                {
//                    System.out.println("You have logged in");
                    add(new FileBrowser(userTF.getText()), "fb");
                    cl.show(this, "fb");
                }

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        Login login = new Login();
        frame.add(login);
        frame.setVisible(true);

    }
}
