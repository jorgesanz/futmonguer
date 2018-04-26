package es.dersuzzala.futmonguer.model;

import lombok.Data;

import java.util.List;

@Data
public class PositionRanking {
    private List<String> goalKeepers;
    private List<String> defenders;
    private List<String> midLiners;
    private List<String> atacants;

    private List<String> bestTeam;
}
