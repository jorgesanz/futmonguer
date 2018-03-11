package es.dersuzzala.futmonguer.service;

import es.dersuzzala.futmonguer.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamCalculatorService {
    @Autowired
    PlayerService playerService;
    public void calculateTeams() throws IOException {
        List<Player> players = new ArrayList<>(playerService.getPlayers());
        calculateCombinations(players);
        System.out.println(players.size());
    }

    private void calculateCombinations(List<Player> players) {
        int teamSize = 3;
        int n = players.size();
        printCombination(players, n, teamSize);
    }


    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    void printCombination(List<Player> players, int n, int teamSize)
    {
        // A temporary array to store all combination one by one
        Player[] data=new Player[teamSize];

        // Print all combination using temprary array 'data[]'
        combinationUtil(players, data, 0, n-1, 0, teamSize);
    }

    /* arr[]  ---> Input Array
data[] ---> Temporary array to store current combination
start & end ---> Staring and Ending indexes in arr[]
index  ---> Current index in data[]
r ---> Size of a combination to be printed */
    void combinationUtil(List<Player> players, Player[] data, int start,
                                int end, int index, int teamSize)
    {
        // Current combination is ready to be printed, print it
        if (index == teamSize)
        {
            for (int j=0; j<teamSize; j++)
                System.out.print(data[j].getName()+" "+data[j].getPoints()+" ");
            System.out.println("");
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= teamSize-index; i++)
        {
            data[index] = players.get(i);
            combinationUtil(players, data, i+1, end, index+1, teamSize);
        }
    }

}
