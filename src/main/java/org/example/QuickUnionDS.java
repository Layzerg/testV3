package org.example;

import java.util.*;

public class QuickUnionDS implements DisJoinSets {
    ArrayList<Integer> parent;
    ArrayList<Integer> size;

    public QuickUnionDS(int n) {
        parent = new ArrayList<>();
        size = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            parent.add(i);
            size.add(1);
        }
    }

    public int find(int p) {
        if (p == parent.get(p)) {
            return p;
        } else {
            parent.set(p, find(parent.get(p)));
            return parent.get(p);
        }
    }

    @Override
    public void connect(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) {
            return;
        }
        if (size.get(i) < size.get(j)) {
            parent.set(i, j);
            size.set(j, size.get(j) + size.get(i));
        } else {
            parent.set(j, i);
            size.set(i, size.get(i) + size.get(j));
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    public int countSetsWithSizeGreaterThanOne() {
        int count = 0;
        for (int i = 0; i < parent.size(); i++) {
            if (size.get(i) > 1 && parent.get(i) == i) {
                count++;
            }
        }
        return count;
    }

    public int countMax() {
        int max = 0;
        for (int i = 0; i < size.size(); i++) {
            if (size.get(i) > max) {
                max = size.get(i);
            }
        }
        return max;
    }
        public HashMap<Integer, ArrayList<Integer>> parentMap() {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        ArrayList<Integer> listOfindexes;
        for(int i = 0; i< parent.size(); i++){
            if(parent.get(i)!=i){
                parent.set(i, find(parent.get(i)));
            }
        }
        for (int i = 0; i < parent.size(); i++) {
            if (!map.containsKey(parent.get(i)) || map.get(parent.get(i)) == null) {
                listOfindexes = new ArrayList<>();
                listOfindexes.add(i);
            } else {
                listOfindexes = map.get(parent.get(i));
                listOfindexes.add(i);
            }
            map.put(parent.get(i), listOfindexes);
        }
        return map;
    }

}