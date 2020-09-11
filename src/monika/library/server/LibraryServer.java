package monika.library.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LibraryServer {
    private static final int PORT = 9000;
    private static final String ADDR = "localhost";

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.format("Server has started on %s:%d\n", ADDR, PORT);

            Library library = new Library();
            library.initLibrary();

            while (true) {
                Socket clientSocket = server.accept();
                System.out.println("# A client connected.");

                BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintStream clientOutput = new PrintStream(clientSocket.getOutputStream());

                CommandMenu commandProcessor = new CommandMenu(clientInput, clientOutput, library);
                commandProcessor.loop();

                clientInput.close();
                clientOutput.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
