package org.example;

import java.util.Arrays;

public class QuickUnionDS implements DisJoinSets {
    public int[] parent;
    public int[] size;

    public QuickUnionDS(int n) {
        parent = new int[n];
        size = new int[n];
        Arrays.fill(size, 1);
        //  size[0] = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int p) {
        if (p == parent[p]) {
            return p;
        } else {
            parent[p] = find(parent[p]);
            return parent[p];
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(parent);
    }

    @Override
    public void connect(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) {
            return;
        }
        if (size[i] < size[j]) {
            parent[i] = j;
            size[j] += size[i];
        } else {
            parent[j] = i;
            size[i] += size[j];
        }
    }
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}