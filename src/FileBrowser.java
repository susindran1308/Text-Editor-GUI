import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileBrowser extends JPanel implements ActionListener {

    JLabel label = new JLabel("File List:");
    JButton newFile = new JButton("New File");
    JButton open = new JButton("Open");
    JTextField newFileTF = new JTextField(10);

    ButtonGroup bg;
    File directory;

    public FileBrowser(String dir)
    {
        directory = new File(dir);
        directory.mkdir();

        JPanel fileList = new JPanel(new GridLayout(directory.listFiles().length+3, 1));

        fileList.add(label);

        bg = new ButtonGroup();
        for(File file: directory.listFiles())
        {
            JRadioButton radio = new JRadioButton(file.getName());
            radio.setActionCommand(file.getName());
            bg.add(radio);
            fileList.add(radio);
        }
        JPanel newP = new JPanel();
        newP.add(newFileTF);
        newP.add(newFile);

        newFile.addActionListener(this);
        open.addActionListener(this);

        fileList.add(open);
        fileList.add(newP);

        add(fileList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Login login = (Login)getParent();
        if (e.getSource() == open)
        {
            login.add(new Editor( directory.getAbsolutePath() + "/" + bg.getSelection().getActionCommand()), "editor");
            login.cl.show(login, "editor");
        }
        if(e.getSource() == newFile)
        {
            String file = directory.getAbsolutePath() + "/" + newFileTF.getText() + ".txt";

            if(newFileTF.getText().length() > 0 && !(new File(file).exists()))
            {
                login.add(new Editor(file), "editor");
                login.cl.show(login, "editor");
            }
        }
        login.revalidate();
        login.repaint();
    }
}
