package es.dersuzzala.futmonguer.service;

import es.dersuzzala.futmonguer.model.Player;
import es.dersuzzala.futmonguer.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamCalculatorService {
    @Autowired
    PlayerService playerService;

    @Autowired
    ResultService resultService;

    public static Integer MIN_GK = 0;
    public static Integer MAX_GK = 0;
    public static Integer MIN_DF = 1;
    public static Integer MAX_DF = 2;
    public static Integer MIN_CC = 0;
    public static Integer MAX_CC = 2;
    public static Integer MIN_DL = 0;
    public static Integer MAX_DL = 2;
    public static final Integer BUDGET =  24_000_000;

    public void calculateTeams() throws IOException {
        List<Player> players = new ArrayList<>(playerService.getPlayers());
        calculateCombinations(players);
        System.out.println(players.size());
    }

    private void calculateCombinations(List<Player> players) {
        int teamSize = 4;
        int n = players.size();
        printCombination(players, n, teamSize);
    }


    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    void printCombination(List<Player> players, int n, int teamSize)
    {
        // A temporary array to store all combination one by one
        Player[] data=new Player[teamSize];

        Team team = new Team();
        team.setPlayers(new ArrayList<>());
        team.setBudget(BUDGET);
        combinationUtil(players, team, 0, n-1, 0, teamSize);
    }

    /* arr[]  ---> Input Array
data[] ---> Temporary array to store current combination
start & end ---> Staring and Ending indexes in arr[]
index  ---> Current index in data[]
r ---> Size of a combination to be printed */
    void combinationUtil(List<Player> players, Team team, int start,
                                int end, int index, int teamSize)
    {
        // Current combination is ready to be printed, print it
        if (index == teamSize)
        {
            if(isTeam(team) && validateTeam(team) && resultService.hasMinPoint(team)){
                resultService.addResult(team);
            }
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= teamSize-index; i++)
        {
            Team newTeam = new Team();
            newTeam.setPlayers(new ArrayList<>(team.getPlayers()));
            newTeam.addPlayer(players.get(i));
            newTeam.setBudget(team.getBudget()-players.get(i).getValue());
            if(newTeam.getBudget() > 0 && validateTeam(team)){
                combinationUtil(players, newTeam, i+1, end, index+1, teamSize);
            }
        }
    }

        private static boolean isTeam(Team team) {
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Portero")).count()< MIN_GK)
            return false;
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Defensa")).count()< MIN_DF)
            return false;
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Medio")).count()< MIN_CC)
            return false;
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Delantero")).count()< MIN_DL)
            return false;
        return true;
    }


    private static boolean validateTeam(Team team) {
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Portero")).count()> MAX_GK)
            return false;
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Defensa")).count()> MAX_DF)
            return false;
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Medio")).count()> MAX_CC)
            return false;
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Delantero")).count()> MAX_DL)
            return false;
        return true;
    }

}
