package com.hsm.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 哈夫曼树
 */
public class HuffmanTree {
    public Node generateTree(Map<String, Integer> frequencyForData) {
        //1.优先队列，会根据泛型对象的compareTo方法进行排序
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        //2.将叶子结点放入到优先级队列中
        for (String str : frequencyForData.keySet()) {
            priorityQueue.add(new Node(str, frequencyForData.get(str)));
        }

        //将数据放入树种，最终的那棵树就是哈夫曼树
        while (priorityQueue.size() != 1) {
            Node minNode1 = priorityQueue.poll();
            Node minNode2 = priorityQueue.poll();
            priorityQueue.add(new Node(minNode1, minNode2, minNode1.freq + minNode2.freq));
        }
        //队列中最后一个就是生成的哈夫曼树
        return priorityQueue.poll();
    }

    public Map<String, String> encodeReal(Node root) {
        Map<String, String> encodingResult = new HashMap<>();
        encodeChar(root, "", encodingResult);
        return encodingResult;
    }

    private void encodeChar(Node node, String encoding, Map<String, String> encodingResult) {
        if (node.isLeaf) {
            encodingResult.put(node.data, encoding);
            return;
        }
        encodeChar(node.left, encoding + "0", encodingResult);
        encodeChar(node.right, encoding + "1", encodingResult);
    }

    public static void main(String[] args) {
        HuffmanTree huffmanTree = new HuffmanTree();

        Map<String, Integer> map = new HashMap<>();
        map.put("A", 27);
        map.put("B", 8);
        map.put("C", 15);
        map.put("D", 15);
        map.put("E", 30);
        map.put("F", 5);
        Node node = huffmanTree.generateTree(map);
        Map<String, String> encodeResult = huffmanTree.encodeReal(node);
        encodeResult.forEach((key,value)->{
            System.out.println(key + ":\t" + value);
        });
    }
}

/**
 * 哈夫曼树的结点
 */
class Node implements Comparable<Node> {
    String data;         //实际数据
    int freq;           //频数
    boolean isLeaf;     //是否叶子结点
    Node left, right;    //左右结点

    public Node(String data, int freq) {
        this.data = data;
        this.freq = freq;
        this.isLeaf = true;
    }

    public Node(Node left, Node right, int freq) {
        this.freq = freq;
        this.left = left;
        this.right = right;
        this.isLeaf = false;
    }

    @Override
    public int compareTo(Node o) {
        return this.freq - o.freq;
    }
}