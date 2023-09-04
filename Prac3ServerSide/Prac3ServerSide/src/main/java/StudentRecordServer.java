import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentRecordServer {
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static Object recievedObject;
    private static String response="";

public StudentRecordServer(){
int port=8008;
    
        try {
            serverSocket=new ServerSocket(port,5);
        System.out.println("SERVER IS LISTENING ON PORT"+port);
        
        clientSocket=serverSocket.accept();
        System.out.println("client connection accepted"+clientSocket.getInetAddress().getHostAddress());
        } catch (IOException ex) {
            Logger.getLogger(StudentRecordServer.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public void getStreams(){
        try {
            out=new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in=new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(StudentRecordServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
}
    public void processClient() {
ArrayList <Student> studentRecord=new ArrayList(); 
studentRecord.add(new Student("joey","21300","50.5%"));
        while(true){
    try {
        recievedObject=in.readObject();
  
        if(recievedObject instanceof Student){
        Student newStudent=(Student) recievedObject;
        studentRecord.add(newStudent);
        System.out.println("Added student record "+newStudent);
        
        }else if( recievedObject instanceof String && ((String)recievedObject).equals("retrieve")){
        ArrayList clone=(ArrayList)studentRecord.clone();
        out.writeObject(clone);
        out.flush();
        System.out.println("list sent to client "+studentRecord);
        }
        else if( recievedObject instanceof String &&((String) recievedObject).equals("exit")){
        closeConnection();
        }
        else if( recievedObject instanceof String ){
               System.out.println(recievedObject);
               for(Student s:studentRecord){
               if(recievedObject.equals(s.getStudentName())){
               String name=s.getStudentName();
               String id=s.getStudentId();
               String score=s.getStudentScore();
               out.writeObject(name+" || "+id+" || "+score);
               }
               }  
}

    } catch (IOException ex) {
        Logger.getLogger(StudentRecordServer.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(StudentRecordServer.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        }
        
    }
 
   private static void closeConnection() {
        try {
            out.writeObject("exit");
            out.flush();
            in.close();
            out.close();
            serverSocket.close();
            clientSocket.close();
            System.out.println("server closed");
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(StudentRecordServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
    StudentRecordServer server=new StudentRecordServer();
    server.getStreams();
    server.processClient();
    }
}


