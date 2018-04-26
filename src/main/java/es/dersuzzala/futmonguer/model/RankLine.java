package es.dersuzzala.futmonguer.model;

import lombok.Data;

@Data
public class RankLine {
    private String playerName;
    private String position;
    private String team;
    private Long teams;
    private Integer price;
    private Integer points;

    @Override
    public String toString() {
        return  playerName + " / " + team + " / " + new Double(price)/1000000d +" M€ / "+points+" points / included " +
                "in =" +
                teams +
                " teams";
    }
}


