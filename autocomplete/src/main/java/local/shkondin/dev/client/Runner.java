package local.shkondin.dev.client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Консольный клиент для сервера словаря.
 */
public class Runner {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Укажите адрес и номер порта");
            System.exit(0);
        }

        String host = args[0];
        int port = Integer.valueOf(args[1]);

        Socket fromServer = new Socket(host, port);
        BufferedReader inUser = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fromServer.getOutputStream(), StandardCharsets.US_ASCII));
        BufferedReader in = new BufferedReader(new InputStreamReader(fromServer.getInputStream(), StandardCharsets.US_ASCII));

        while (true) {
            String query = inUser.readLine();
            out.write(query);
            out.newLine();
            out.flush();

            String answer;
            while (!(answer = in.readLine()).isEmpty()) {
                System.out.println(answer);
            }
        }
    }
}
