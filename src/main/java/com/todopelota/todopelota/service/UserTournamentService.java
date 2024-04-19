package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.Tournament;
import com.todopelota.todopelota.model.User;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class UserTournamentService {

    public Set<Tournament> getTournamentsByUser(User user) {
        return user.getTournaments();
    }
}