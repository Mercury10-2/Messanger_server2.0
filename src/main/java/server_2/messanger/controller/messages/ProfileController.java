package server_2.messanger.controller.messages;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import server_2.messanger.domain.messages.UserSubscription;
import server_2.messanger.domain.messages.Views;
import server_2.messanger.domain.users.User;
import server_2.messanger.service.messages.ProfileService;

import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController {

    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(@AuthenticationPrincipal User subscriber, @PathVariable("channelId") User channel) {
        if (subscriber.equals(channel))
            return channel;
        else
            return service.changeSubscription(channel, subscriber);
    }

    @GetMapping("get-subscribers/{channelId}")
    @JsonView(Views.IdName.class)
    public List<UserSubscription> subscribers(@PathVariable("channelId") User channel) {
        return service.getSubscribers(channel);
    }

    @PostMapping("change-status/{subscriberId}")
    @JsonView(Views.IdName.class)
    public UserSubscription changeSubscriptionStatus(@AuthenticationPrincipal User channel,
                                                     @PathVariable("subscriberId") User subscriber) {
        return service.changeSubscriptionStatus(channel, subscriber);
    }
}
