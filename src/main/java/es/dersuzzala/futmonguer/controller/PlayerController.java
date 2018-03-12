package es.dersuzzala.futmonguer.controller;

import es.dersuzzala.futmonguer.model.Player;
import es.dersuzzala.futmonguer.model.RankLine;
import es.dersuzzala.futmonguer.model.Team;
import es.dersuzzala.futmonguer.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@RestController("/players")
public class PlayerController {

    @Autowired
    private ResultService resultService;

    @GetMapping
    public List<RankLine> getPlayersRanking(){
        List<Team> bestTeams = resultService.getResults();
        return bestTeams.stream().map(Team::getPlayers).flatMap(Collection::stream)
                .collect(groupingBy(Player::getName)).entrySet().stream()
                .map(entry ->
                {
                    RankLine rankLine = new RankLine();
                    rankLine.setPlayerName(entry.getKey());
                    rankLine.setPoints(entry.getValue().stream().count());
                    return rankLine;
                }).collect(Collectors.toList());

    }

}
