package es.dersuzzala.futmonguer.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class Parser {


    public static void main(String[] args) throws IOException {

        File input = new Parser().getFile("alaves-28-2.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
        Elements players = doc.getElementsByClass("cmPlayer");
        players.forEach(player -> {
            Elements playerName = player.select(".name");
            System.out.println(playerName.text());
        });
    }



    private File getFile(String fileName) {
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());

    }




}
