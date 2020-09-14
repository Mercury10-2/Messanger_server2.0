package server_2.messanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import server_2.messanger.domain.messages.UserSubscription;
import server_2.messanger.domain.messages.UserSubscriptionId;
import server_2.messanger.domain.users.User;

import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {
    
    List<UserSubscription> findBySubscriber(User user);

    List<UserSubscription> findByChannel(User channel);

    UserSubscription findByChannelAndSubscriber(User channel, User subscriber);
}
