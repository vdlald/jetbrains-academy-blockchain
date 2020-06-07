package com.vladislav.blockchain.pojo;

import com.vladislav.blockchain.Block;
import com.vladislav.blockchain.BlockFactory;
import com.vladislav.blockchain.Blockchain;
import com.vladislav.blockchain.serializers.BlockSerializer;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class Chat implements Runnable {

    private final Blockchain blockchain;
    private final Collection<Message> messages;
    private final BlockFactory blockFactory;
    private final BlockSerializer blockSerializer;

    public Chat(Blockchain blockchain, BlockFactory blockFactory, BlockSerializer blockSerializer) {
        this.blockchain = blockchain;
        this.blockFactory = blockFactory;
        this.blockSerializer = blockSerializer;
        messages = new LinkedList<>();
    }

    public void accept(Message message) {
        synchronized (messages) {
            messages.add(message);
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            final ArrayList<Message> localMessages;
            synchronized (messages) {
                localMessages = new ArrayList<>(messages);
                messages.clear();
            }

            final Block block;
            final Instant begin = Instant.now();
            if (blockchain.getLastBlock() == null) {
                block = blockFactory.getEmptyBlock();
            } else {
                block = blockFactory.getBlockWithData(
                        localMessages, blockchain.getLastBlock(), blockchain.getCountOfZeros()
                );
            }
            final Instant end = Instant.now();
            final long seconds = Duration.between(begin, end).getSeconds();

            blockchain.accept(block);

            System.out.printf(
                    "%s\nBlock was generating for %d seconds\n", blockSerializer.serialize(block), seconds
            );

            if (seconds < 15) {
                blockchain.increaseCountOfZeros();
                System.out.printf("N was increased to %d\n\n", blockchain.getCountOfZeros());
            } else if (seconds > 60) {
                blockchain.decreaseCountOfZeros();
                System.out.printf("N was decreased to %d\n\n", blockchain.getCountOfZeros());
            } else {
                System.out.println("N stays the same");
            }
            accept(new Message("World", "hello " + i));
        }
    }
}
