
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class ClientApp  {

    private Socket server;
   
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String response = " ";
    

    public void ClientApp() {
        try {
            server = new Socket("localhost",8008);

        } catch (IOException ioe) {
            System.out.println("error connecting to server port"+ioe.getMessage());
        }

        

    }

    public void sendData(String msg) {

        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException ioe) {
            System.out.println("io exeption in sending data" + ioe.getMessage());
        }

    }

    private void getStreams() throws IOException {
        out = new ObjectOutputStream(server.getOutputStream());
        in = new ObjectInputStream(server.getInputStream());
    }

    public void communicate() {

        do {

            try {
                response = (String)in.readObject();
                ClientGui g=new ClientGui();
             g.textarea.append(response+"\n");

            } catch (IOException ioe) {
                System.out.println("io exeption in communication" + ioe.getMessage());
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Class not found" + cnfe.getMessage());
            }
        } while (!response.equalsIgnoreCase("terminate"));
        closeConnection();
    }

    public void closeConnection() {
        try {
            out.close();
            in.close();
            server.close();
        } catch (IOException ioe) {
            System.out.println("ioe error in closing connections" + ioe.getMessage());
        }
    }

  

}
