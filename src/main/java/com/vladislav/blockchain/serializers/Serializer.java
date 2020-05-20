package com.vladislav.blockchain.serializers;

public interface Serializer<T> {

    String serialize(T object);

}
