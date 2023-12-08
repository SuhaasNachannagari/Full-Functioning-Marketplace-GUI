import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class ClientRohan {
    public static void main(String[] args) throws IOException,
            UnknownHostException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        Socket socket = null;
        socket = new Socket("localhost", 4242);



        // get the input stream from the connected socket
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        // create a DataInputStream so we can read data from it.
        List<User> customer = (List<User>) inputStream.readObject();
        List<User> seller = (List<User>) inputStream.readObject();
        Logs login = new Logs();
        List<Object> details = login.LogIn(customer,seller);//0th index is the user, 1st index is seller or customer, 2nd index tells you whether the account already exists.
        outputStream.writeObject(details);
        User user = (User) details.get(0);
        boolean exists;
        exists = !details.get(2).equals("true");
        String username = user.getUsername();
        String sellerorcustomer = details.get(1).toString();



        socket.close();
    }
}