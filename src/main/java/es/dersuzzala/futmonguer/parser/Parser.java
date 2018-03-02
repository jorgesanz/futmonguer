package es.dersuzzala.futmonguer.parser;

import es.dersuzzala.futmonguer.model.Player;
import es.dersuzzala.futmonguer.model.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Parser {


    public static void main(String[] args) throws IOException {

        Parser parser = new Parser();
        List<String> files = parser.getResourceFiles("/");
        //select #teamPlayers div

        List<Player> players = new ArrayList<>();
        files.remove("es");
        for(String fileName: files){
            File file = parser.getFile(fileName);
            Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");

            String team = fileName.replace(".html","");
            Elements playersElements = doc.getElementsByClass("cmPlayer");
            playersElements.forEach(playerElement -> {
                Player player = new Player();
                player.setTeam(team);
                player.setName(playerElement.select(".name").attr("title"));
                player.setPosition(playerElement.select(".name .pos").html());
                player.setPoints(Integer.parseInt(playerElement.select(".points").html()));
                player.setValue(Integer.parseInt(playerElement.select(".cost").html().toString().replaceAll("\\.",
                        "").replaceAll(" €","")));
                player.setOwner(playerElement.select(".owner").html());
                player.setState(playerElement.select(".time").html());
                players.add(player);
            });

        }
        List<Team> bestTeam = calculateBestTeam(players, new ArrayList<>(),100_000_000);

        System.out.println(players.size());
    }

    private static List<Team> calculateBestTeam(List<Player> players, List<Player> teamPlayers, Integer budget) {
        if (teamPlayers.size()==11){
            Team team = new Team();
            team.setPlayers(players);
            team.setPoints(players.stream().mapToInt(Player::getPoints).sum());
            System.out.println("obtained team with points "+team.getPoints());
            return Arrays.asList(team);
        }
        else{
            return  players.stream().map(player -> {
                List<Player> remainingPlayers = new ArrayList<>(players);
                remainingPlayers.remove(player);
                teamPlayers.add(player);
                Integer currentBudget = budget + player.getValue();
                return calculateBestTeam(remainingPlayers,teamPlayers,currentBudget);
            }).flatMap(results -> results.stream()).collect(toList());
        }
    }


    private File getFile(String fileName) {
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());

    }

    private List<String> getResourceFiles( String path ) throws IOException {
        List<String> filenames = new ArrayList<>();

        try(
                InputStream in = getResourceAsStream( path );
                BufferedReader br = new BufferedReader( new InputStreamReader( in ) ) ) {
            String resource;

            while( (resource = br.readLine()) != null ) {
                filenames.add( resource );
            }
        }

        return filenames;
    }

    private InputStream getResourceAsStream( String resource ) {
        final InputStream in
                = getContextClassLoader().getResourceAsStream( resource );

        return in == null ? getClass().getResourceAsStream( resource ) : in;
    }

    private ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


}
