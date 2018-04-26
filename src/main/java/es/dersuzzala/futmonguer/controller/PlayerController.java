package es.dersuzzala.futmonguer.controller;

import es.dersuzzala.futmonguer.model.PositionRanking;
import es.dersuzzala.futmonguer.model.Team;
import es.dersuzzala.futmonguer.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;

@RestController("/players")
public class PlayerController {

    @Autowired
    private ResultService resultService;

    @GetMapping
    public PositionRanking getPlayersRanking(){
        List<Team> bestTeams = resultService.getResults();
        return resultService.getBestPlayerByPosition(resultService.getBestPlayers(bestTeams));
    }


}
