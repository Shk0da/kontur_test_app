package local.shkondin.dev.console;

import java.util.Scanner;

import local.shkondin.dev.core.Searcher;

/**
 * Консольный ранер
 */
public class Runner {

    private static final Searcher searcher = Searcher.getInstance("");

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String find = searcher.find(input.next());
        System.out.println(find);
    }
}
