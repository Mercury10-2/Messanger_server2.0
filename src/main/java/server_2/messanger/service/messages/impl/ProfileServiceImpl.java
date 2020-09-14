package server_2.messanger.service.messages.impl;

import org.springframework.stereotype.Service;

import server_2.messanger.domain.messages.UserSubscription;
import server_2.messanger.domain.users.User;
import server_2.messanger.repository.UserRepository;
import server_2.messanger.repository.UserSubscriptionRepository;
import server_2.messanger.service.messages.ProfileService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userDetailsRepo;
    private final UserSubscriptionRepository userSubscriptionRepo;

    public ProfileServiceImpl(UserRepository userDetailsRepo, UserSubscriptionRepository userSubscriptionRepo) {
        this.userDetailsRepo = userDetailsRepo;
        this.userSubscriptionRepo = userSubscriptionRepo;
    }

    public User changeSubscription(User channel, User subscriber) {
        List<UserSubscription> subcriptions = channel.getSubscribers()
                .stream()
                .filter(subscription ->
                        subscription.getSubscriber().equals(subscriber)
                )
                .collect(Collectors.toList());

        if (subcriptions.isEmpty()) {
            UserSubscription subscription = new UserSubscription(channel, subscriber);
            channel.getSubscribers().add(subscription);
        } else {
            channel.getSubscribers().removeAll(subcriptions);
        }

        return userDetailsRepo.save(channel);
    }

    public List<UserSubscription> getSubscribers(User channel) {
        return userSubscriptionRepo.findByChannel(channel);
    }

    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        UserSubscription subscription = userSubscriptionRepo.findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());

        return userSubscriptionRepo.save(subscription);
    }
}
