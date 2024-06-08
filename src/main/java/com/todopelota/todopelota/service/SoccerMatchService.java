package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.SoccerMatch;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.SoccerMatchRepository;
import com.todopelota.todopelota.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class SoccerMatchService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private SoccerMatchRepository soccerMatchRepository;

    @Autowired
    private UserRepository userRepository;


    public SoccerMatch createMatch(SoccerMatch soccerMatch) {
        return soccerMatchRepository.save(soccerMatch);
    }

    public SoccerMatch updateMatch(SoccerMatch soccerMatch) {
        return soccerMatchRepository.save(soccerMatch);
    }

    public Set<SoccerMatch> findMatchesByTournamentId(Long tournamentId) {
        return soccerMatchRepository.findByTournamentId(tournamentId);
    }

    public Optional<SoccerMatch> findMatchById(Long matchId) {
        return soccerMatchRepository.findById(matchId);
    }

    public void deleteMatch(Long matchId) {
        try {
            SoccerMatch match = soccerMatchRepository.findById(matchId)
                    .orElseThrow(() -> new NoSuchElementException("Match not found with id : " + matchId));
            soccerMatchRepository.delete(match);
            logger.info("Match with id: {} deleted", matchId);
        } catch (Exception e) {
            logger.error("Error deleting match with id: {}", matchId, e);
        }
    }

    public List<SoccerMatch> getMatchesForTomorrow() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tomorrow = now.plusDays(1);
        return soccerMatchRepository.findByDateBetween(
                now,
                tomorrow
        );
    }
}