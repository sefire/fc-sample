package bel.tnp.resources;

import java.util.UUID;

/**
 * Created by Dmitry on 2/25/2017.
 */
public class Message {

    private String id = UUID.randomUUID().toString();
    private String content;

    Message() {
    }

    public Message(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
