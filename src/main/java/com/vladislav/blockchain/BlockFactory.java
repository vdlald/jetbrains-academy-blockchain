package com.vladislav.blockchain;

import com.vladislav.blockchain.pojo.Message;
import com.vladislav.blockchain.pojo.Miner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class BlockFactory {

    private final ExecutorService executor;
    private final List<Miner> miners;

    public BlockFactory(ExecutorService executor, List<Miner> miners) {
        this.executor = executor;
        this.miners = miners;
    }

    public Block getEmptyBlock() {
        return createBlock(1, "0", 0, Collections.emptyList());
    }

    public Block getBlockWithData(Collection<Message> messages, Block lastBlock, int countOfZeros) {
        return createBlock(lastBlock.getId()+1, lastBlock.getBlockHash(), countOfZeros, new ArrayList<>(messages));
    }

    private Block createBlock(
            int blockId, String previousBlockHash, int countOfZeros, Collection<Message> messages
    ) {
        try {
            return executor.invokeAny(
                    miners.stream()
                            .map(miner -> new CreateBlockCommand(
                                    blockId,
                                    previousBlockHash,
                                    countOfZeros,
                                    messages,
                                    miner
                            )).collect(Collectors.toList())
            );
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

}
