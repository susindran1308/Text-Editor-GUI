import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

public class Register extends JPanel implements ActionListener {

    JLabel userL = new JLabel("Username:");
    JTextField userTF = new JTextField();
    JLabel passL = new JLabel("Password:");
    JPasswordField passTF = new JPasswordField();
    JLabel passLC = new JLabel("Confirm Password:");
    JPasswordField passC = new JPasswordField();

    JButton register = new JButton("register");
    JButton back = new JButton("back");

    Register()
    {
        JPanel loginP = new JPanel();
        loginP.setLayout(new GridLayout(4,2));

        loginP.add(userL);
        loginP.add(userTF);
        loginP.add(passL);
        loginP.add(passTF);
        loginP.add(passLC);
        loginP.add(passC);
        loginP.add(register);
        loginP.add(back);
        register.addActionListener(this);
        back.addActionListener(this);
        add(loginP);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back)
        {
            Login login = (Login)getParent();
            login.cl.show(login, "login");
        }
        else if(e.getSource() == register && passTF.getPassword().length != 0 && passC.getPassword().length != 0)
        {
            String pass = new String(passTF.getPassword());
            String confirm = new String(passC.getPassword());

            if (pass.equals(confirm))
            {
                try {
                    BufferedReader input = new BufferedReader(new FileReader("/media/susindran/Softwares&Tutorials/JAVA SAMPLES ZOHO/Text Editor GUI/src/passwords.txt"));
                    String line;
                    while((line = input.readLine()) != null)
                    {
                        StringTokenizer tokenizer = new StringTokenizer(line);
                        if(userTF.getText().equals(tokenizer.nextToken()))
                        {
                            System.out.println("User already exists");
                            return;
                        }

                    }
                    input.close();

                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    md.update(pass.getBytes());
                    byte[] byteData = md.digest();

                    StringBuffer sb = new StringBuffer();
                    for(int i=0; i<byteData.length; i++)
                    {
                        sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
                    }

                    BufferedWriter output = new BufferedWriter(new FileWriter("/media/susindran/Softwares&Tutorials/JAVA SAMPLES ZOHO/Text Editor GUI/src/passwords.txt", true));
                    output.write(userTF.getText() + " " + sb.toString() + "\n");
                    output.close();

                    Login login = (Login)getParent();
                    login.cl.show(login, "login");

                } catch (FileNotFoundException ex) {
                    System.out.println(new File("").getAbsolutePath());
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
