package com.vladislav.blockchain.serializers;

import com.vladislav.blockchain.Block;
import com.vladislav.blockchain.pojo.Message;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BlockSerializer implements Serializer<Block> {

    private static volatile BlockSerializer instance;

    @Override
    public String serialize(Block block) {
        final StringBuilder builder = new StringBuilder();

        builder.append("Block:\n");
        if (block.getMinerNumber() != null) {
            builder.append("Created by miner # ").append(block.getMinerNumber()).append("\n");
        }
        builder.append("Id: ").append(block.getId()).append("\n");
        builder.append("Timestamp: ").append(block.getTimestamp().getEpochSecond()).append("\n");
        builder.append("Magic number: ").append(block.getMagic()).append("\n");
        builder.append("Hash of the previous block:\n").append(block.getPreviousBlockHash()).append("\n");
        builder.append("Hash of the block:\n").append(block.getBlockHash()).append("\n");

        builder.append("Block data:");
        final List<String> messages = block.getMessages().stream().map(Message::toString).collect(Collectors.toList());
        if (messages.isEmpty()) {
            builder.append(" no messages");
        } else {
            builder.append('\n');
            messages.forEach(builder::append);
        }

        return builder.toString();
    }

    public BlockSerializer() {
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
