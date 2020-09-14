package server_2.messanger.domain.messages;

import com.fasterxml.jackson.annotation.JsonView;

import server_2.messanger.domain.BaseEntity;
import server_2.messanger.domain.users.User;

import javax.persistence.*;

@Entity
@Table
public class Comment extends BaseEntity {

    @Column(name = "text")
    @JsonView(Views.IdName.class)
    private String text;

    @ManyToOne
    @Column(name = "message")
    @JoinColumn(name = "message_id")
    @JsonView(Views.FullComment.class)
    private Message message;

    @ManyToOne
    @Column(name = "author")
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonView(Views.IdName.class)
    private User author;

    public Comment() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Message other = (Message) o;
        if (getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!getId().equals(other.getId()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return getId() == null ? 0 : getId().hashCode();
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

}
