package es.dersuzzala.futmonguer.service;

import es.dersuzzala.futmonguer.model.Player;
import es.dersuzzala.futmonguer.model.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {


    public static Integer MIN_SCORE = 182;

    public Collection<Player> getPlayers() throws IOException {
        List<String> files = getResourceFiles("/");
        //select #teamPlayers div

        List<Player> players = new ArrayList<>();
        files.remove("es");
        for(String fileName: files){
            File file = getFile(fileName);
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
                //player.setOwner(playerElement.select(".owner").html());
                player.setState(playerElement.select(".time").html());
                players.add(player);
            });

        }
        List<Team> teams = new ArrayList<>();
        Team team = new Team();
        team.setPlayers(new ArrayList<>());

        List<Player> filteredPlayers = players.stream().filter(player -> player.getPoints() > MIN_SCORE).collect(Collectors.toList());
        return filteredPlayers;
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
