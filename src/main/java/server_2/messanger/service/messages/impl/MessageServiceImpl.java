package server_2.messanger.service.messages.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import server_2.messanger.domain.messages.Message;
import server_2.messanger.domain.messages.UserSubscription;
import server_2.messanger.domain.messages.Views;
import server_2.messanger.domain.users.User;
import server_2.messanger.dto.EventType;
import server_2.messanger.dto.MessagePageDto;
import server_2.messanger.dto.MetaDto;
import server_2.messanger.dto.ObjectType;
import server_2.messanger.repository.MessageRepository;
import server_2.messanger.repository.UserSubscriptionRepository;
import server_2.messanger.service.messages.MessageService;
import server_2.messanger.util.WsSender;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMG_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final MessageRepository messageRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final BiConsumer<EventType, Message> wsSender;

    public MessageServiceImpl(MessageRepository messageRepository, UserSubscriptionRepository userSubscriptionRepository, WsSender wsSender) {
        this.messageRepository = messageRepository;
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.FullMessage.class);
    }

    private void fillMeta(Message message) throws IOException {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);
        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());
            matcher = IMG_REGEX.matcher(url);
            message.setLink(url);
            if (matcher.find())
                message.setLinkCover(url);
            else if (!url.contains("youtu")) {
                MetaDto meta = getMeta(url);
                message.setLinkCover(meta.getCover());
                message.setLinkTitle(meta.getTitle());
                message.setLinkDescription(meta.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements title = doc.select("meta[name$=title],meta[property$=title]");
        Elements description = doc.select("meta[name$=description],meta[property$=description]");
        Elements cover = doc.select("meta[name$=image],meta[property$=image]");
        return new MetaDto(getContent(title.first()),
                           getContent(description.first()),
                           getContent(cover.first()));
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }

    public void delete(Message message) {
        messageRepository.delete(message);
        wsSender.accept(EventType.REMOVE, message);
    }

    public Message update(Message messageFromDb, Message message) throws IOException {
        messageFromDb.setText(message.getText());
        fillMeta(messageFromDb);
        Message updatedMessage = messageRepository.save(messageFromDb);
        wsSender.accept(EventType.UPDATE, updatedMessage);
        return updatedMessage;
    }

    public Message create(Message message, User user) throws IOException {
        message.setCreated(LocalDateTime.now());
        fillMeta(message);
        message.setAuthor(user);
        Message updatedMessage = messageRepository.save(message);
        wsSender.accept(EventType.CREATE, updatedMessage);
        return updatedMessage;
    }

    public MessagePageDto findForUser(Pageable pageable, User user) {
        List<User> channels = userSubscriptionRepository.findBySubscriber(user).stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());
        channels.add(user);
        Page<Message> page = messageRepository.findByAuthorIn(channels, pageable);
        return new MessagePageDto(page.getContent(),
                                  pageable.getPageNumber(),
                                  page.getTotalPages()
        );
    }
}