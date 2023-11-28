package org.example;

import java.util.*;

public class QuickUnionDS implements DisJoinSets {
    int[] parent;
    int[] size;

    public QuickUnionDS(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
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

    public int countSetsWithSizeGreaterThanOne() {
        int count = 0;
        for (int i = 0; i < parent.length; i++) {
            if (size[i] > 1 && parent[i] == i) {
                count++;
            }
        }
        return count;
    }

    public int countMax() {
        int max = 0;
        for (int i = 0; i < size.length; i++) {
            if (size[i] > max) {
                max = size[i];
            }
        }
        return max;
    }

    public HashMap<Integer, ArrayList<Integer>> parentMap() {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        ArrayList<Integer> listOfindexes = new ArrayList<>(1);
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }
        }
        for (int i = 0; i < parent.length; i++) {
            if (!map.containsKey(parent[i]) || map.get(parent[i]) == null) {
                listOfindexes = new ArrayList<>();
                listOfindexes.add(i);
            } else {
                listOfindexes = map.get(parent[i]);
                listOfindexes.add(i);
            }
            map.put(parent[i], listOfindexes);
        }
        return map;
    }

}