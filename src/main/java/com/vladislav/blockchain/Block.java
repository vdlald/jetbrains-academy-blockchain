package com.vladislav.blockchain;

import com.vladislav.blockchain.pojo.Message;
import com.vladislav.blockchain.utils.Utils;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class Block {

    private final int id;
    private final Instant timestamp;
    private final String previousBlockHash;
    private final int magic;
    private final Collection<Message> messages;

    // p.s: To complete a task
    private transient Integer minerNumber;

    public Block(int id, Instant timestamp, String previousBlockHash, int magic) {
        Objects.requireNonNull(previousBlockHash);
        Objects.requireNonNull(timestamp);
        this.id = id;
        this.timestamp = timestamp;
        this.previousBlockHash = previousBlockHash;
        this.magic = magic;
        messages = Collections.emptyList();
    }

    public Block(
            int id, Instant timestamp, String previousBlockHash, int magic, Collection<Message> messages
    ) {
        this.id = id;
        this.timestamp = timestamp;
        this.previousBlockHash = previousBlockHash;
        this.magic = magic;
        this.messages = Collections.unmodifiableCollection(messages);
    }

    public int getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public int getMagic() {
        return magic;
    }

    // p.s: To complete a task
    public Integer getMinerNumber() {
        return minerNumber;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    // p.s: To complete a task
    public void setMinerNumber(int minerNumber) {
        this.minerNumber = minerNumber;
    }

    public String getBlockHash() {
        return Utils.applySha256(
                new StringBuilder()
                        .append(id)
                        .append(timestamp)
                        .append(previousBlockHash)
                        .append(magic)
                        .append(messages)
                        .toString()
        );
    }
}
