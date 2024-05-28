package com.todopelota.todopelota.service;

import com.todopelota.todopelota.model.SoccerMatch;
import com.todopelota.todopelota.model.User;
import com.todopelota.todopelota.repository.SoccerMatchRepository;
import com.todopelota.todopelota.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private SoccerMatchService soccerMatchService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private SoccerMatchRepository soccerMatchRepository;


    @Scheduled(cron = "0 * * * * ?")
    public void sendMatchNotifications() {
        List<SoccerMatch> matches = soccerMatchService.getMatchesForTomorrow();
        for (SoccerMatch match : matches) {
            if (!match.isNotificationSent()) {
                List<Optional<User>> allUsers = new ArrayList<>();
                for (String username : match.getAllUsers()) {
                    Optional<User> user = userRepository.findByUsername(username);
                    allUsers.add(user);
                }
                logger.info("users got: {}", allUsers.size());
                for (Optional<User> user : allUsers) {
                    String subject = "Soccer Match Reminder";
                    String text = "You have a soccer match scheduled for tomorrow at " + match.getLocation() + ".";
                    emailService.sendEmail(user.get().getEmail(), subject, text);
                }
                match.setNotificationSent(true);
                soccerMatchRepository.save(match);
            }
        }
    }
}
