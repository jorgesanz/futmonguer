package es.dersuzzala.futmonguer.model;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    private List<Player> players;
    private Integer points;
}
