package es.dersuzzala.futmonguer.service;

import es.dersuzzala.futmonguer.model.Player;
import es.dersuzzala.futmonguer.model.PositionRanking;
import es.dersuzzala.futmonguer.model.RankLine;
import es.dersuzzala.futmonguer.model.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class ResultService {

    public static Integer MAX_TEAMS = 200;

    private List<Team> results= new ArrayList<>();

    public void addResult(Team team){
        team.setPoints(team.getPlayers().stream().mapToInt(Player::getPoints).sum());
        if (results.size()>=MAX_TEAMS  && team.getPoints()> results.get(MAX_TEAMS-1).getPoints()){
            results.add(team);
            results = results.stream().sorted((f1, f2) -> Integer.compare(f2.getPoints(), f1.getPoints())).collect(Collectors.toList());
            results.remove(200);
            //System.out.println("obtained team with points "+team.getPoints()+" "+team.toString());
        }
        else if(results.size()<MAX_TEAMS){
            results.add(team);
            results = results.stream().sorted((f1, f2) -> Integer.compare(f2.getPoints(), f1.getPoints())).collect(Collectors.toList());
            //System.out.println("obtained team with points "+team.getPoints()+" "+team.toString());
        }
        System.out.println("best result "+ results.get(0).getPoints());
        System.out.println(results.get(0));
    }

    public boolean hasMinPoint(Team team) {
        if(results.size()<MAX_TEAMS) return true;
        Integer points = team.getPlayers().stream().mapToInt(Player::getPoints).sum();
        return points > results.get(MAX_TEAMS-1).getPoints();
    }

    public List<Team> getResults() {
        return results;
    }

    public List<RankLine> getBestPlayers(List<Team> bestTeams) {
        return bestTeams.stream().map(Team::getPlayers).flatMap(Collection::stream)
                .collect(groupingBy(Player::getName)).entrySet().stream()
                .map(entry ->
                {
                    RankLine rankLine = new RankLine();
                    rankLine.setPosition(entry.getValue().get(0).getPosition());
                    rankLine.setTeam(entry.getValue().get(0).getTeam());
                    rankLine.setPlayerName(entry.getKey());
                    rankLine.setTeams(entry.getValue().stream().count());
                    rankLine.setPrice(entry.getValue().get(0).getValue());
                    rankLine.setPoints(entry.getValue().get(0).getPoints());
                    return rankLine;
                }).sorted((f1, f2) -> Long.compare(f2.getTeams(), f1.getTeams())).collect(Collectors.toList());
    }

    public PositionRanking getBestPlayerByPosition(List<RankLine> rankLines){
        PositionRanking positionRanking = new PositionRanking();
        positionRanking.setDefenders(rankLines.stream().filter(player -> player.getPosition().equals("Defensa"))
                .map(elem->elem.toString()).collect(Collectors.toList()));
        positionRanking.setMidLiners(rankLines.stream().filter(player -> player.getPosition().equals("Medio")).map(elem->elem.toString()).collect
                (Collectors.toList()));
        positionRanking.setAtacants(rankLines.stream().filter(player -> player.getPosition().equals("Delantero")).map(elem->elem.toString())
                .collect(Collectors.toList()));
        positionRanking.setGoalKeepers(rankLines.stream().filter(player -> player.getPosition().equals("Portero")).map(elem->elem.toString()).collect
                (Collectors.toList()));
        positionRanking.setBestTeam(results.get(0).getPlayers().stream().map(Object::toString).collect(Collectors.toList()));
        return  positionRanking;
    }
}
