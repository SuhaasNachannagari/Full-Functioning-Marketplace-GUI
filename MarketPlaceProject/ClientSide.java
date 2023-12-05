import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientSide extends Thread {
    private static int ind1 = 0;
    private static int ind2 = 0;
    public boolean checkIndexOption;
    public boolean checkIndexDoAgain;
    public int checkDoAgain; //1 for Yes, 2 for No
    public String username = null;
    private static Object gateKeeper = new Object();
    public void run() {
        Socket socket;
        BufferedReader bfr;
        PrintWriter pw;
        try {
            InetAddress ia = InetAddress.getByName("localhost");
            socket = new Socket(ia, 4242);
            bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int checkUser = Logs.runLogs();
        username = Logs.getUsername();
        //1
        pw.write(checkUser + "\n");
        pw.flush();
        //2
        pw.write(username + "\n");
        pw.flush();

        try {
            if (checkUser == 1) {
                String[] showOptions = {"1 - Delete", "2 - Edit", "3 - Create", "4 - View", "5 - Import/Export", "6 - Dashboard"};
                String option = (String) (JOptionPane.showInputDialog(null, "What do you want to do?",
                        "Choice", JOptionPane.QUESTION_MESSAGE, null, showOptions, showOptions[0]));
                if (option == null) {
                    throw Exception;
                }
                //1s
                pw.write(option + "\n");
                pw.flush();

                switch (option) {
                    case "1 - Delete":
                        Delete delete = new Delete();
                        delete.setSeller(username);
                        break;
                    default:
                        System.out.println("Please enter the correct number!");
                        checkIndexOption = false;
                        break;
                }
            }


            if (checkUser == 0) {

            }
        } catch (IOException e) {
        }

        if (checkDoAgain == 2) {
            System.out.println("Have a good day");
        }
    }
}
