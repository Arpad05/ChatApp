package ca.qc.bdeb.inf203.shared;

import java.io.Serializable;

public class Message implements Serializable {
    public static final String separator = "/";
    private User sender;
    private String message;

    public Message(User sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFormattedMsg() {
        return sender.getNom() + separator + sender.getId() + separator + message;
    }

    @Override
    public String toString() {
        return sender.getNom() + ": " + message;
    }
}
