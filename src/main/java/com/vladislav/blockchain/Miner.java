package com.vladislav.blockchain;

import com.vladislav.blockchain.utils.Utils;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Miner {

    private final int number;

    public Miner(int number) {
        this.number = number;
    }

    public Block createBlock(String previousBlockHash, int countOfZeros, int blockId) {
        Objects.requireNonNull(previousBlockHash);
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        Block block;

        do {
            block = new Block(
                    blockId,
                    Instant.now(),
                    previousBlockHash,
                    random.nextInt(100000, 1000000)
            );
            block.setMinerNumber(number);
        } while (!Utils.checkCountOfZeros(block, countOfZeros));

        return block;
    }

}
