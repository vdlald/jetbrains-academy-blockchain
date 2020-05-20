package com.vladislav.blockchain.utils;

import com.vladislav.blockchain.Block;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Objects;

public class Utils {

    public static String applySha256(String input) {
        Objects.requireNonNull(input);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkCountOfZeros(Block block, int countOfZeros) {
        Objects.requireNonNull(block);
        for (char c : block.getBlockHash().substring(0, countOfZeros).toCharArray()) {
            if (c != '0')
                return false;
        }
        return true;
    }

    private Utils() {
    }
}
