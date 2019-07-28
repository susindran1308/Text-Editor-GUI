import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Editor extends JPanel implements ActionListener {

    File file;
    JButton save = new JButton("save");
    JButton savec = new JButton("save and close");

    JTextArea text = new JTextArea(20, 40);

    public Editor(String s)
    {
        file = new File(s);
        save.addActionListener(this);
        savec.addActionListener(this);

        if (file.exists())
        {
            try {
                BufferedReader input = new BufferedReader(new FileReader(file));
                String line;

                while ((line = input.readLine()) != null)
                {
                    text.append(line + "\n");
                }
                input.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        add(save);
        add(savec);
        add(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            FileWriter out = new FileWriter(file);
            out.write(text.getText());
            out.close();

            if (e.getSource() == savec)
            {
                Login login = (Login)getParent();
                login.cl.show(login, "fb");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
