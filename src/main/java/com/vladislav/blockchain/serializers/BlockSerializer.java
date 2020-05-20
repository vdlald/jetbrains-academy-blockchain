package com.vladislav.blockchain.serializers;

import com.vladislav.blockchain.Block;

public class BlockSerializer implements Serializer<Block> {

    private static volatile BlockSerializer instance;

    @Override
    public String serialize(Block block) {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Block:\n");
        if (block.getMinerNumber() != null) {
            stringBuilder.append("Created by miner # ").append(block.getMinerNumber()).append("\n");
        }
        stringBuilder.append("Id: ").append(block.getId()).append("\n");
        stringBuilder.append("Timestamp: ").append(block.getTimestamp().getEpochSecond()).append("\n");
        stringBuilder.append("Magic number: ").append(block.getMagic()).append("\n");
        stringBuilder.append("Hash of the previous block:\n").append(block.getPreviousBlockHash()).append("\n");
        stringBuilder.append("Hash of the block:\n").append(block.getBlockHash());

        return stringBuilder.toString();
    }

    private BlockSerializer() {
    }

    public static BlockSerializer getInstance() {
        BlockSerializer localInstance = instance;
        if (localInstance == null) {
            synchronized (BlockSerializer.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new BlockSerializer();
                }
            }
        }
        return localInstance;
    }
}
