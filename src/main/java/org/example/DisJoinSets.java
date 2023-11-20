package org.example;

public interface DisJoinSets {
    void connect(int p, int q);

    boolean isConnected(int p, int q);

}
