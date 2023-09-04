import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentRecordClient implements ActionListener{
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static JFrame frame;
    private static JTextField nameField;
    private static JTextField idField;
    private static JTextField scoreField,searchField;
    private static JTextArea recordsTextArea;
    private static JButton addButton;
    private static JButton retrieveButton;
    private static JButton exitButton;
    private static Socket socket;
private static JLabel id,score,name,search;
   private static String response = " ";
     private static Object recievedObject; 
     private static JButton searchButton;
   
       public StudentRecordClient(){
             try {
            socket = new Socket("localhost",8008);

        } catch (IOException ioe) {
            System.out.println("error connecting to server port"+ioe.getMessage());
        }

       }
    


    private void createAndShowGUI() {
frame=new JFrame();
nameField=new JTextField();
idField=new JTextField();
scoreField=new JTextField();
searchField=new JTextField();
recordsTextArea= new JTextArea(5, 40);
addButton = new JButton("add");
retrieveButton= new JButton("retrieve");
exitButton= new JButton("exit");
searchButton= new JButton("search");
id=new JLabel("enter id: ");
name=new JLabel("enter name: ");
score=new JLabel("enter score: ");
search=new JLabel("Search for name or id");

frame.setLayout(new GridLayout(7,2));
frame.add(name);
frame.add(nameField);
frame.add(id);
frame.add(idField);
frame.add(score);
frame.add(scoreField);
frame.add(search);
frame.add(searchField);
frame.add(addButton);
frame.add(searchButton);
frame.add(retrieveButton);
frame.add(exitButton);
frame.add(recordsTextArea);
addButton.addActionListener(this);
retrieveButton.addActionListener(this);
exitButton.addActionListener(this);
searchButton.addActionListener(this);


frame.setSize(400,400);
frame.setVisible(true);
frame.pack();

    }
   
  
    private static void addStudentRecord() {


        try {
            String name,id,score;
name=nameField.getText();
id=idField.getText();
score=scoreField.getText();
            Student s=new Student(name,id,score);
            out.writeObject(s);
        } catch (IOException ex) {
            Logger.getLogger(StudentRecordClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        nameField.setText("");
         idField.setText("");
         scoreField.setText("");
        
   }
 private void getStreams()  {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(StudentRecordClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  


    private static void retrieveStudentRecords() {
        try {
          
            out.writeObject("retrieve");
            out.flush();
//            response=(String)in.readObject();
//            recordsTextArea.append(response);
           ArrayList <Student> studentRecord=(ArrayList<Student>) in.readObject();
           //reading in an array
              displayStudentRecords(studentRecord);
        } catch (IOException ex) {
            Logger.getLogger(StudentRecordClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentRecordClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void search(String s){
        try {
            out.writeObject(s);
            out.flush();
             response=(String)in.readObject();
             recordsTextArea.setText("");
            recordsTextArea.append(response);
        } catch (IOException ex) {
            Logger.getLogger(StudentRecordClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentRecordClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


   private static void displayStudentRecords(ArrayList<Student> studentList) {
   for (Student student : studentList) {
 recordsTextArea.append(student.getStudentName()+ ", " + student.getStudentId()+ ", " + student.getStudentScore()+ "\n");
    }//use for each loop to append each variable
   }


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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentRecordClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addButton){
        
            addStudentRecord();
        }
        if(e.getSource()==searchButton){
        search(searchField.getText().toString());
        }
        if(e.getSource()==retrieveButton){
        recordsTextArea.setText("");
            retrieveStudentRecords();
        
        }
        if(e.getSource()==exitButton){
        closeConnection();
        }
    }

 
    public static void main(String[] args) {
StudentRecordClient s=new StudentRecordClient();
s.createAndShowGUI();
s.getStreams();
       
    }

 
}


