package com.vladislav.blockchain;

import com.vladislav.blockchain.pojo.Chat;
import com.vladislav.blockchain.pojo.Miner;
import com.vladislav.blockchain.serializers.BlockSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main implements Runnable {

    private final ExecutorService executor;
    private final List<Miner> miners;
    private final Chat chat;

    public void run() {
    }

    private Main() {
        final int nThreads = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(nThreads);

        miners = new ArrayList<>(nThreads);
        for (int i = 0; i < nThreads; i++) {
            miners.add(new Miner(i + 1));
        }

        chat = new Chat(new Blockchain(), new BlockFactory(executor, miners), new BlockSerializer());
        chat.run();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
