package com.vladislav.blockchain.pojo;

import com.vladislav.blockchain.Block;
import com.vladislav.blockchain.utils.Utils;

import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Miner {

    private final int number;

    public Miner(int number) {
        this.number = number;
    }

    public Block createBlock(String previousBlockHash, int countOfZeros, int blockId, Collection<Message> messages) {
        Objects.requireNonNull(previousBlockHash);
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        Block block;

        do {
            block = new Block(
                    blockId,
                    Instant.now(),
                    previousBlockHash,
                    random.nextInt(100000, 1000000),
                    messages
            );
            block.setMinerNumber(number);
        } while (!Utils.checkCountOfZeros(block, countOfZeros));

        return block;
    }

}
