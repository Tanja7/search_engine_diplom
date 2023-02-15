import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
        private static final int PORT = 8989;
        private static final String HOST = "127.0.0.1";

        public static void main(String[] args) {

            try (Socket socket = new Socket(HOST, PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) {
                Scanner scanner = new Scanner(System.in);
                System.out.println(in.readLine());
                out.println(scanner.nextLine());
                String message = in.readLine();
                while (message != null) {
                    System.out.println(message);
                    message = in.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
