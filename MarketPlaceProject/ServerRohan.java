import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerRohan {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(4242);
        Socket socket = serverSocket.accept();


        Logs login = new Logs();
        List<User> customers = login.loadFromCustomerFile();
        List<User> sellers = login.loadFromSellerFile();
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        // create an object output stream from the output stream so we can send an object through it
        outputStream.writeObject(customers);
        outputStream.writeObject(sellers);
        List<Object> details = (List<Object>) inputStream.readObject();
        String checker = details.get(2).toString();
        boolean exists;
        exists = !checker.equals("true");
        String sellerorcustomer = details.get(1).toString();
        User user = (User) details.get(0);
        if (!exists) {
            if (sellerorcustomer.equals("Seller")) {
                login.saveToSellerFile(user);
            } else {
                login.saveToCustomerFile(user);
            }
        }



        socket.close();
    }
}