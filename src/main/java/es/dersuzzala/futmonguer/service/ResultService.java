package es.dersuzzala.futmonguer.service;

import es.dersuzzala.futmonguer.model.Player;
import es.dersuzzala.futmonguer.model.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultService {

    public static Integer MAX_TEAMS = 100;

    private List<Team> results= new ArrayList<>();

    public void addResult(Team team){
        team.setPoints(team.getPlayers().stream().mapToInt(Player::getPoints).sum());
        if (results.size()>=MAX_TEAMS  && team.getPoints()> results.get(99).getPoints()){
            results.add(team);
            results = results.stream().sorted((f1, f2) -> Integer.compare(f2.getPoints(), f1.getPoints())).collect(Collectors.toList());
            results.remove(100);
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
}
