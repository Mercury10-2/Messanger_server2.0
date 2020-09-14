package server_2.messanger.service.messages;

import java.util.List;

import server_2.messanger.domain.messages.UserSubscription;
import server_2.messanger.domain.users.User;

public interface ProfileService {

	User changeSubscription(User channel, User subscriber);

	List<UserSubscription> getSubscribers(User channel);

	UserSubscription changeSubscriptionStatus(User channel, User subscriber);
    
}
