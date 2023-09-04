
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 */
public class CotyClient  extends JFrame implements ActionListener {
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Socket socket;
 private static String response = " ";
 private static Object recievedObject;
    private JPanel panelNorth;
    private JPanel panelCenter, panelCenter1, panelCenter2;
    private JPanel panelSouth;
    
    //private JLabel lblLogo;
    private JLabel lblHeading;
            
    private JLabel lblCarFinalists;
    private JComboBox cboCarFinalists;
    
    private JButton btnVote, btnRetrieve, btnExit;
    private Font ft1, ft2, ft3;
    
    private static JTextArea recordsTextArea;

    
    public CotyClient() {
        super("Car of the Year Voting App");
        
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelCenter1 = new JPanel();
        panelCenter2 = new JPanel();
        panelSouth = new JPanel();
        
        lblHeading = new JLabel("Vote for your Car of the Year");

        lblCarFinalists = new JLabel("Finalists: ");
        cboCarFinalists = new JComboBox<>(new String[]{"Ford Ranger", "Audi A3", "BMW X3", "Toyota Starlet", "Suzuki Swift" });
        
        btnVote = new JButton("Vote");
        btnRetrieve = new JButton("Retrieve");
        btnExit = new JButton("Exit");
        
        ft1 = new Font("Arial", Font.BOLD, 32);
        ft2 = new Font("Arial", Font.PLAIN, 22);
        ft3 = new Font("Arial", Font.PLAIN, 24);
    }
    
    public void setGUI() {
        panelNorth.setLayout(new FlowLayout());
        panelCenter.setLayout(new GridLayout(2, 1));
        panelSouth.setLayout(new GridLayout(1, 3));
        panelCenter1.setLayout(new FlowLayout());
        panelCenter2.setLayout(new FlowLayout());
        
        //panelNorth.add(lblLogo);
        panelNorth.add(lblHeading);
        lblHeading.setFont(ft1);
        lblHeading.setForeground(Color.yellow);
        panelNorth.setBackground(new Color(0, 106, 255));
        
        lblCarFinalists.setFont(ft2);
        lblCarFinalists.setHorizontalAlignment(JLabel.RIGHT);
        cboCarFinalists.setFont(ft2);
        recordsTextArea = new JTextArea(10,40);
        recordsTextArea.setEditable(false);
        panelCenter1.add(lblCarFinalists);
        panelCenter1.add(cboCarFinalists);
        panelCenter2.add(new JScrollPane(recordsTextArea), BorderLayout.SOUTH);       
        panelCenter2.setBackground(new Color(36, 145, 255));
        
        panelCenter.add(panelCenter1);
        panelCenter.add(panelCenter2);
        
        btnVote.setFont(ft3);
        btnRetrieve.setFont(ft3);
        btnExit.setFont(ft3);
        panelSouth.add(btnVote);
        panelSouth.add(btnRetrieve);
        panelSouth.add(btnExit);
        
        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        btnVote.addActionListener(this);
        btnRetrieve.addActionListener(this);
        btnExit.addActionListener(this);
        
        this.setSize(900, 600);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    //complete the actionPerformed method so that one of the the 3 methods is
    // called depending on which button was clicked.
    public void actionPerformed(ActionEvent e) {
if(e.getSource()==btnVote){
castVote(cboCarFinalists.getSelectedItem().toString());
}
else if(e.getSource()==btnRetrieve){
retrieveStudentRecords();
}
else if(e.getSource()==btnExit){
closeConnection();
}
    }

    //Complete the method to connect to the server
    public static void connectToServer(){
        try {
            socket=new Socket("localhost",8008);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ioe) {
            System.out.println("error connecting to server port"+ioe.getMessage());
        }
    }
    
    //Complete the method to close the connection to the server and exit the application
    private static void closeConnection() {
try {
     out.writeObject("exit");
     out.flush();
       response = (String) in.readObject();
       if(response.equals("exit")){
            out.close();
            in.close();
            socket.close();
            System.exit(0);}
        } catch (IOException ioe) {
            System.out.println("ioe error in closing connections" + ioe.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("error finding class"+cnfe.getMessage());
        }
    }
    

    //Complete the method to cast a vote
    private static void castVote(String vote) {
        try {
            out.writeObject(vote);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(CotyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    //Complete the method to retrieve the list of cars and number of votes
    private static void retrieveStudentRecords() {
        try {
            out.writeObject("retrieve");
            out.flush();
            response=(String)in.readObject();
            recordsTextArea.append(response);
        } catch (IOException ex) {
            Logger.getLogger(CotyClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CotyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public static void main(String[] args) {
        new CotyClient().setGUI();
        connectToServer();
    }    
}
