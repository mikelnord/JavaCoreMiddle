import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame {
    private final JTextArea jta;
    private final JTextField textField;

    public MyWindow() {
        setTitle("Test Window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 400, 400);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        add(panel1, BorderLayout.CENTER);
        jta = new JTextArea();
        JScrollPane jsp = new JScrollPane(jta);
        panel1.add(jsp);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(panel2, BorderLayout.SOUTH);
        textField = new JTextField(25);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addText();
            }
        });
        JButton jButton = new JButton("Go");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addText();
            }
        });

        panel2.add(textField);
        panel2.add(jButton);

        setVisible(true);
    }

    private void addText() {
        if (jta.getText().equals(""))
            jta.setText(textField.getText());
        else
            jta.setText(jta.getText() + '\n' + textField.getText());
        textField.setText("");

    }

}
