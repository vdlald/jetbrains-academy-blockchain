package com.vladislav.blockchain;

import com.vladislav.blockchain.pojo.Message;
import com.vladislav.blockchain.pojo.Miner;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class CreateBlockCommand implements Callable<Block> {

    private final int blockId;
    private final String previousBlockHash;
    private final int countOfZeros;
    private final Collection<Message> messages;

    private final Miner miner;

    public int getBlockId() {
        return blockId;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public int getCountOfZeros() {
        return countOfZeros;
    }

    public CreateBlockCommand(
            int blockId,
            String previousBlockHash,
            int countOfZeros,
            Collection<Message> messages,
            Miner miner
    ) {
        this.messages = messages;
        Objects.requireNonNull(previousBlockHash);
        Objects.requireNonNull(miner);
        this.miner = miner;
        this.blockId = blockId;
        this.previousBlockHash = previousBlockHash;
        this.countOfZeros = countOfZeros;
    }

    public Block execute() {
        return miner.createBlock(previousBlockHash, countOfZeros, blockId, messages);
    }

    @Override
    public Block call() throws Exception {
        return execute();
    }
}
