
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class ClientGui extends JFrame implements ActionListener{
    
     private JLabel clientlbl = new JLabel("enter text");
    private JButton button = new JButton("exit");
    private JTextField textfield = new JTextField(20);
   public JTextArea textarea = new JTextArea(5, 40);
    private JPanel top = new JPanel();
    private JPanel bottom = new JPanel();

    public ClientGui() {
      super("ClientSide");
    }
public void setGui(){
setLayout(new BorderLayout());
        top.setLayout(new FlowLayout());
        top.add(clientlbl);
        top.add(textfield);
        top.add(button);
        button.addActionListener(this);
        button.setVisible(false);
        bottom.setLayout(new FlowLayout());
        bottom.add(textarea);
        this.add(top, BorderLayout.NORTH);
        this.add(bottom, BorderLayout.SOUTH);
      this.setVisible(true);
                this.setSize(600, 400);
        textfield.addActionListener(this);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
}
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == textfield) {
            ClientApp a=new ClientApp();
            a.sendData("gfgfgfgf");
            a.communicate();
            textfield.setText("");
        }
    }
      public static void main(String[] args) {
   ClientGui g=new ClientGui();
   g.setGui();
   ClientApp a=new ClientApp();
a.ClientApp();
    }
}
