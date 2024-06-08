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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public void sendConfirmationEmail(String email, String username) {
        String subject = "Account Confirmation";
        String text = "Hello " + username + ",\n\n" +
                "Thank you for signing up for TodoPelota! If you did not sign up for TodoPelota, please ignore this email.";
        emailService.sendEmail(email, subject, text);
    }

    public void alertDeletedMatchToParticipants(String email, String username, String matchLocation, String matchDate) {
        String subject = "Match Cancellation";
        String text = "Hello " + username + ",\n\n" +
                "The match you were scheduled to play in" + " " + matchLocation + " " + "at " + matchDate + ", has been cancelled. If you have any questions, please contact the organizer.";
        emailService.sendEmail(email, subject, text);
    }


    @Scheduled(cron = "0 * * * * ?")
    public void sendMatchNotifications() {
        List<SoccerMatch> matches = soccerMatchService.getMatchesForTomorrow();
        LocalDate currentDate = LocalDate.now();
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
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    String matchTime = match.getDate().toLocalTime().format(formatter);
                    String matchDay = (match.getDate().toLocalDate().isEqual(currentDate)) ? "today" : "tomorrow";
                    String text = "You have a soccer match scheduled for " + matchDay + ", " + matchTime + " at " + match.getLocation() + ".";
                    emailService.sendEmail(user.get().getEmail(), subject, text);
                }
                match.setNotificationSent(true);
                soccerMatchRepository.save(match);
            }
        }
    }
}
