package local.shkondin.dev.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import local.shkondin.dev.core.Searcher;

/**
 * Сервер словаря.
 */
public class AlphabetServer implements Runnable {

    private int serverPort;
    private final Searcher searcher;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    public AlphabetServer(int port, Searcher searcher) {
        this.serverPort = port;
        this.searcher = searcher;
        this.threadPool = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        openServerSocket();
        while (true) {
            try {
                Socket clientSocket = this.serverSocket.accept();
                threadPool.execute(new Worker(clientSocket, this.searcher));
            } catch (IOException e) {
                threadPool.shutdown();
                throw new RuntimeException("Ошибка подключения", e);
            }
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Невозможно использовать порт: " + this.serverPort, e);
        }
    }

}
