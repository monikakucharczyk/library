package monika.library.client;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibraryClient {
    private static final int PORT = 9000;
    private static final String ADDR = "localhost";

    public static void main(String[] args) {
        Socket sock;
        try {
            sock = new Socket();

            InetAddress inetAddress = InetAddress.getByName(ADDR);
            SocketAddress socketAddress = new InetSocketAddress(inetAddress, PORT);
            sock.connect(socketAddress);

            System.out.format("Connecting to %s:%d...\n", ADDR, PORT);

            InputStream in = sock.getInputStream();
            OutputStream out = sock.getOutputStream();

            Scanner scanner = new Scanner(System.in);
            DataOutputStream serverOutput = new DataOutputStream(out);

            boolean shouldEnd = false;
            while (!shouldEnd) {
                if (in.available() == 0) {
                    continue;
                }
                while(in.available() > 0) {
                    System.out.write(in.read());
                }
                System.out.print('>');
                String command = scanner.nextLine();
                if (command.equals("exit")) {
                    shouldEnd = true;
                }
                serverOutput.writeBytes(command + "\n");
            }
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
