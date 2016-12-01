package local.shkondin.dev.core;

import org.junit.*;

public class SearcherTest {

    @Test
    public void testFindNum2() throws Exception {
        Searcher searcher = Searcher.getInstance("src/main/resources/test2.in");

        String assertKNeed = "kanojo\n" +
                "kare\n" +
                "korosu\n" +
                "karetachi\n";
        String findK = searcher.find("k");
        Assert.assertEquals(assertKNeed, findK);

        String assertKaNeed = "kanojo\n" +
                "kare\n" +
                "karetachi\n";
        String findKa = searcher.find("ka");
        Assert.assertEquals(assertKaNeed, findKa);

        String assertKarNeed = "kare\n" +
                "karetachi\n";
        String findKar = searcher.find("kar");
        Assert.assertEquals(assertKarNeed, findKar);
    }

    @Test
    public void testFindNum3() throws Exception {
        Searcher searcher = Searcher.getInstance("src/main/resources/test3.in");

        String assertNeed = "abd\n" +
                "abs\n" +
                "abg\n" +
                "abf\n" +
                "abc\n" +
                "abe\n";
        String find = searcher.find("ab");
        Assert.assertEquals(assertNeed, find);
    }

}