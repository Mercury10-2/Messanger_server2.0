package server_2.messanger.domain.messages;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.springframework.data.annotation.LastModifiedDate;

import server_2.messanger.domain.BaseEntity;
import server_2.messanger.domain.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class Message extends BaseEntity {

    @Column(name = "text")
    @JsonView(Views.IdName.class)
    private String text;

    @LastModifiedDate
    @Column(name = "updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    @JsonView(Views.FullMessage.class)
    private LocalDateTime updated;

    @ManyToOne
    @Column(name = "author")
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullMessage.class)
    private User author;

    @OneToMany(mappedBy = "message", orphanRemoval = true)
    @Column(name = "comments")
    @JsonView(Views.FullMessage.class)
    private List<Comment> comments;

    @Column(name = "link")
    @JsonView(Views.FullMessage.class)
    private String link;

    @Column(name = "linkTitle")
    @JsonView(Views.FullMessage.class)
    private String linkTitle;

    @Column(name = "linkDescription")
    @JsonView(Views.FullMessage.class)
    private String linkDescription;

    @Column(name = "linkCover")
    @JsonView(Views.FullMessage.class)
    private String linkCover;

    public Message() {
    }

    @Override
    public String toString() {
        return "{ id='" + getId() + "', text='" + getText() + "'}";
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

    public LocalDateTime getUpdated() {
        return this.updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkTitle() {
        return this.linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getLinkDescription() {
        return this.linkDescription;
    }

    public void setLinkDescription(String linkDescription) {
        this.linkDescription = linkDescription;
    }

    public String getLinkCover() {
        return this.linkCover;
    }

    public void setLinkCover(String linkCover) {
        this.linkCover = linkCover;
    }
}