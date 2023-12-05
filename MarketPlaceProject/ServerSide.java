import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSide {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4242);
        MarketplaceServer.readDataCustomer();
        MarketplaceServer.readDataSeller();

        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                // 1
                Thread thread = new WorkFlow(socket, pw, bfr);
                thread.start();
                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    public void run() {
                        System.out.println("Data saved");
                        MarketplaceServer.writeDataCustomer();;
                        MarketplaceServer.writeDataSeller();
                    }
                }, "Shutdown-thread"));
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }

    }
}

class WorkFlow extends Thread {
    Socket socket ;
    PrintWriter pw;
    BufferedReader bfr;
    private String username;
    public WorkFlow(Socket socket, PrintWriter pw, BufferedReader bfr) {
        this.socket = socket;
        this.pw = pw;
        this.bfr = bfr;
    }
    public void run() {
        try {
            String checkUser = bfr.readLine();
            String username = bfr.readLine();

            if (checkUser.equals("1")) {
                MarketplaceServer.setSeller(username);
                //1s
                String option = bfr.readLine();
                switch (option) {
                    case "1 - Delete":
                        MarketplaceServer.delete();
                        break;
                }
            }
            if (checkUser.equals("2")) {
                MarketplaceServer.setCustomer(username);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}