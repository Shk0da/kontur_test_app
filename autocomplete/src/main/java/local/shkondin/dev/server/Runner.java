package local.shkondin.dev.server;

import local.shkondin.dev.core.Searcher;

/**
 * Ранер сервера
 */
public class Runner {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Укажите номер порта и путь к тестовому файлу");
            System.exit(0);
        }

        int port = Integer.valueOf(args[0]);
        final Searcher searcher = Searcher.getInstance(String.valueOf(args[1]));

        AlphabetServer server = new AlphabetServer(port, searcher);
        new Thread(server).start();
    }
}
