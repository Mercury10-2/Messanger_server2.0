package server_2.messanger.domain.messages;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserSubscriptionId implements Serializable {

    @JsonView(Views.Id.class)
    private String channelId;

    @JsonView(Views.Id.class)
    private String subscriberId;

    public UserSubscriptionId() {
    }

    public UserSubscriptionId(String channelId, String subscriberId) {
        this.channelId = channelId;
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "{ channelId='" + getChannelId() + "', subscriberId='" + getSubscriberId() + "'}";
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getSubscriberId() {
        return this.subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

}
