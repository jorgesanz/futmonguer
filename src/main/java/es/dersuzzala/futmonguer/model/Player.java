package es.dersuzzala.futmonguer.model;

import lombok.Data;

@Data
public class Player {

    private String name;
    private String team;
    private String position;
    private String owner;
    private String value;
    private String points;

}
