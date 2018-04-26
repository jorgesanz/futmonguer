package es.dersuzzala.futmonguer.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Team {

    private List<Player> players;
    private Integer points;
    private Integer budget;

    public void addPlayer(Player player){
        if(players == null) players = new ArrayList<>();
        players.add(player);
    }
}
