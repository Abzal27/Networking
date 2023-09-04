
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;


public class CotyServer {
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static Object recievedObject;

public CotyServer(){
int port=8008;

        try {
            serverSocket=new ServerSocket(port);
              System.out.println("SERVER IS LISTENING ON PORT"+port);
            clientSocket=serverSocket.accept();
            System.out.println("client connection accepted"+clientSocket.getInetAddress().getHostAddress());
        } catch (IOException ioe) {
           System.out.println("error with connections");
        }
}

public void getStreams(){
        try {
            out=new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in=new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ioe) {
           System.out.println("error getting streams");
        }
}

    public void processClient() {
        ArrayList<VotingPoll> votingRecords = new ArrayList<>();
        votingRecords.add(new VotingPoll("Ford Ranger", 0));
        votingRecords.add(new VotingPoll("Audi A3", 0));
        votingRecords.add(new VotingPoll("BMW X3", 0));
        votingRecords.add(new VotingPoll("Toyota Starlet", 0));
        votingRecords.add(new VotingPoll("Suzuki Swift", 0));
        //ArrayList<VotingPoll> lstVotingResults;
        String arrayAsString;
            while (true) {
            try {
                recievedObject=in.readObject();
                if(recievedObject instanceof String && ((String)recievedObject).equals("retrieve"))
                {
                      arrayAsString=votingRecords.toString();
          out.writeObject(arrayAsString);
          out.flush();
                }
                else if(recievedObject instanceof String && ((String)recievedObject).equals("exit"))
                {
                    out.writeObject("exit");
                    out.flush();
                closeConnection();
                }else{
                for(int i=0;i<votingRecords.size();i++){
                if(votingRecords.get(i).getCar().equals(recievedObject)){
                votingRecords.get(i).VotesIncrease();
                
                }        
                }
                }
                
            } catch (IOException ioe) {
                System.out.println("error with client processing");
            } catch (ClassNotFoundException cnfe) {
              System.out.println("class not found");
            }
            }
    }

    private static void closeConnection() {
        try {
            out.writeObject("Exit");
            in.close();
            out.close();
            serverSocket.close();
            clientSocket.close();
            System.out.println("server closed");
            System.exit(0);
        } catch (IOException ioe) {
           System.out.println("error closing server");
        }
    }

    public static void main(String[] args) {
CotyServer c=new CotyServer();
c.getStreams();
c.processClient();
    }   
}