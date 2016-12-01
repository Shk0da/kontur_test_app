package local.shkondin.dev.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;

import local.shkondin.dev.core.Searcher;

/**
 * Воркер потока для работы со словарем
 */
public class Worker implements Runnable {

    private Socket clientSocket;
    private final Searcher searcher;

    public Worker(Socket clientSocket, Searcher searcher) {
        this.clientSocket = clientSocket;
        this.searcher = searcher;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.US_ASCII));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.US_ASCII));

            while (clientSocket.isConnected()) {
                String prefix = getPrefix(in.readLine());
                String result;
                if (prefix.isEmpty()) {
                    result = "Use the query: get <prefix> \n";
                } else {
                    synchronized (searcher) {
                        result = searcher.find(prefix);
                    }
                }

                out.write(result);
                out.newLine();
                out.flush();
            }
        } catch (SocketException e) {
            System.out.println("Потеряли подключение");
        } catch (InputMismatchException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Обрабатывает команды вида "get <prefix>".
     * @param prefix запрос от клиента
     * @return префикс из запроса
     */
    private static String getPrefix(String prefix) {
        String result = "";
        String[] parsePrefix = prefix.split(" ");

        if (parsePrefix.length == 2 && parsePrefix[0].equals("get")) {
            result = parsePrefix[1];
        }

        return result;
    }

}
