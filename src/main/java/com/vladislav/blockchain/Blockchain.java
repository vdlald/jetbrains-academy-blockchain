package com.vladislav.blockchain;

import com.vladislav.blockchain.exceptions.IncorrectBlock;
import com.vladislav.blockchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Blockchain {

    private final List<Block> blocks;
    private int countOfZeros;

    private volatile Block lastBlock;

    public Blockchain() {
        blocks = new ArrayList<>();
    }

    public boolean isValid() {
        Block block1 = blocks.get(0);
        for (int i = 1; i < blocks.size(); i++) {
            final Block block2 = blocks.get(i);
            if (!block1.getBlockHash().equals(block2.getPreviousBlockHash()))
                return false;
            block1 = block2;
        }
        return true;
    }

    public synchronized boolean accept(Block block) {
        Objects.requireNonNull(block);
        if (lastBlock == null) {
            if (!block.getPreviousBlockHash().equals("0") || block.getId() != 1) {
                throw new IncorrectBlock();
            }
            addBlock(block);
            return true;
        } else {
            if (checkPreviousBlockHash(block) && checkCountOfZeros(block) && block.getId() - 1 == lastBlock.getId()) {
                addBlock(block);
                return true;
            }
        }
        return false;
    }

    private boolean checkCountOfZeros(Block block) {
        return Utils.checkCountOfZeros(block, countOfZeros);
    }

    private boolean checkPreviousBlockHash(Block block) {
        return lastBlock.getBlockHash().equals(block.getPreviousBlockHash());
    }

    private void addBlock(Block block) {
        lastBlock = block;
        blocks.add(block);
    }

    public int getCountOfZeros() {
        return countOfZeros;
    }

    public synchronized void increaseCountOfZeros() {
        countOfZeros++;
    }

    public synchronized void decreaseCountOfZeros() {
        countOfZeros--;
    }

    public synchronized Block getLastBlock() {
        return lastBlock;
    }
}
