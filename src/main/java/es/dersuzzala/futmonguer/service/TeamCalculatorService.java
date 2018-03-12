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

    public void calculateTeams() throws IOException {
        List<Player> players = new ArrayList<>(playerService.getPlayers());
        calculateCombinations(players);
        System.out.println(players.size());
    }

    private void calculateCombinations(List<Player> players) {
        int teamSize = 11;
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
        team.setBudget(200_000_000);
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
            if(isTeam(team) && resultService.hasMinPoint(team)){
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
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Portero")).count()< Team.MIN_GK)
            return false;
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Defensa")).count()< Team.MIN_DF)
            return false;
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Medio")).count()< Team.MIN_CC)
            return false;
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Delantero")).count()< Team.MIN_DL)
            return false;
        return true;
    }


    private static boolean validateTeam(Team team) {
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Portero")).count()> Team.MAX_GK)
            return false;
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Defensa")).count()> Team.MAX_DF)
            return false;
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Medio")).count()> Team.MAX_CC)
            return false;
        if(team.getPlayers().stream().filter(player -> player.getPosition().equals("Delantero")).count()> Team.MAX_DL)
            return false;
        return true;
    }

}
