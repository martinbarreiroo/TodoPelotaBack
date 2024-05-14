package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.Position;
import com.todopelota.todopelota.model.SoccerMatch;
import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.PositionRepository;
import com.todopelota.todopelota.repository.SoccerMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class PositionService {
    @Autowired
    private SoccerMatchRepository soccerMatchRepository;

    @Autowired
    private PositionRepository positionRepository;



    public void updatePosition(User user, Tournament tournament) {
        // Find the position of the user in the tournament
        Position position = positionRepository.findByUserIdAndTournamentId(user.getId(), tournament.getId());

        // Set the points of the position to 0
        position.setPoints(0);

        // Get all matches in the tournament
        Set<SoccerMatch> soccerMatches = soccerMatchRepository.findByTournamentId(tournament.getId());

        // Iterate over each match
        for (SoccerMatch match : soccerMatches) {
            // Check if the user's team is team1 or team2 in the match
            if (match.getTeam1().contains(user.getUsername())) {
                // If user's team is team1, add the points of team1 to the total points
                position.setPoints(position.getPoints() + match.getTeam1Points());
            } else if (match.getTeam2().contains(user.getUsername())) {
                // If user's team is team2, add the points of team2 to the total points
                position.setPoints(position.getPoints() + match.getTeam2Points());
            }
        }

        // Save the updated position
        positionRepository.save(position);
    }

    public void savePosition(Position position) {
        positionRepository.save(position);
    }
}
