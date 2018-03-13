package es.dersuzzala.futmonguer.model;

import lombok.Data;

@Data
public class RankLine {
    private String playerName;
    private String position;
    private String team;
    private Long points;
}
