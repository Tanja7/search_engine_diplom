import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;
    private final BooleanSearchEngine engine;

    public Server(int port, BooleanSearchEngine engine) {
        this.port = port;
        this.engine = engine;
    }

    public void start() throws IOException {
        System.out.println("Starting server at " + port + "...");
        try (ServerSocket serverSocket = new ServerSocket(port)) { // стартуем сервер
            while (true) { //  принимаем подключения
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                    // обработка одного подключения
                    out.println("Введите слово для поиска:");
                    //прочитать запрос
                    String message = in.readLine();
                    //  запустить поиск и вернуть результат клиенту
                    var result = engine.search(message);
                    var searchResult = new SearchResult(result);
                    out.println(searchResult.toJson());
                } catch (IOException e) {
                    System.out.println("Не могу стартовать сервер");
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
