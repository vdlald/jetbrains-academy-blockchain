package com.vladislav.blockchain;

import com.vladislav.blockchain.serializers.BlockSerializer;
import com.vladislav.blockchain.serializers.Serializer;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Sandbox implements Runnable {

    private final Blockchain blockchain;
    private final ExecutorService executor;
    private final int nThreads;
    private final List<Miner> miners;
    private final Serializer<Block> blockSerializer;

    public void run() {
        Block lastBlock = blockchain.getLastBlock();
        for (int i = 0; i < 5; i++) {
            int id;
            String previousBlockHash;

            if (lastBlock == null) {
                id = 1;
                previousBlockHash = "0";
            } else {
                id = lastBlock.getId() + 1;
                previousBlockHash = lastBlock.getBlockHash();
            }

            try {
                final Instant start = Instant.now();
                final Block block = executor.invokeAny(
                        miners.stream()
                                .map(miner -> new CreateBlockCommand(
                                        id,
                                        previousBlockHash,
                                        blockchain.getCountOfZeros(),
                                        miner
                                )).collect(Collectors.toList())
                );
                final Instant end = Instant.now();

                blockchain.accept(block);
                lastBlock = block;

                final long seconds = Duration.between(start, end).getSeconds();
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
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(blockchain);
    }

    private Sandbox() {
        blockchain = new Blockchain();
        nThreads = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(nThreads);
        blockSerializer = BlockSerializer.getInstance();

        miners = new ArrayList<>(nThreads);
        for (int i = 0; i < nThreads; i++) {
            miners.add(new Miner(i + 1));
        }
    }

    public static void main(String[] args) {
        new Sandbox().run();
    }
}
