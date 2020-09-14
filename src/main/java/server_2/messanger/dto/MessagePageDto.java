package server_2.messanger.dto;

import com.fasterxml.jackson.annotation.JsonView;

import server_2.messanger.domain.messages.Message;
import server_2.messanger.domain.messages.Views;

import java.util.List;

@JsonView(Views.FullMessage.class)
public class MessagePageDto {

    private List<Message> messages;
    private int currentPage;
    private int totalPages;

    public MessagePageDto() {
    }

    public MessagePageDto(List<Message> messages, int currentPage, int totalPages) {
        this.messages = messages;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
