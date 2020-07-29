package edu.byu.model.domain;

import java.io.Serializable;
import java.util.Objects;

public class Status implements Comparable<Status>, Serializable {
    public User author;
    public String message;
    public String timestamp;

    public Status () { }

    public Status(User author, String message, String timestamp){
        this.author = author;
        this.message = message;
        this.timestamp = timestamp;
    }

    public User getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return author.getAlias().equals(status.getAuthor().getAlias())
                && message.equals(status.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthor().getAlias());
    }

    @Override
    public String toString() {
        return "Status{" +
                "Author Alias='" + author.getAlias() + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public int compareTo(Status status) {
        return this.author.getAlias().compareTo(status.getAuthor().getAlias());
    }
}
