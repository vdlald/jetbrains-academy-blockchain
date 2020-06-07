package com.vladislav.blockchain.pojo;

public class Message {

    private final String from;
    private final String data;

    public String getFrom() {
        return from;
    }

    public String getData() {
        return data;
    }

    public Message(String from, String data) {
        this.from = from;
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", from, data);
    }
}
