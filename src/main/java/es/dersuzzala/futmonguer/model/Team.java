package es.dersuzzala.futmonguer.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Team {

    public static Integer MIN_GK = 1;
    public static Integer MAX_GK = 1;
    public static Integer MIN_DF = 3;
    public static Integer MAX_DF = 5;
    public static Integer MIN_CC = 2;
    public static Integer MAX_CC = 6;
    public static Integer MIN_DL = 0;
    public static Integer MAX_DL = 4;
    private List<Player> players;
    private Integer points;
    private Integer budget;

    public void addPlayer(Player player){
        if(players == null) players = new ArrayList<>();
        players.add(player);
    }
}
