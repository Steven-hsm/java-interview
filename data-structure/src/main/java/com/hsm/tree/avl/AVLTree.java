package com.hsm.tree.avl;

import com.hsm.tree.sorttree.SortTree;
import lombok.Data;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/22
 */
public class AVLTree {
    /**
     * 根节点
     */
    @Getter
    private AVLNode root;


    public void insert(AVLNode node, int data) {
        //小于节点往左边插入
        if (data < node.data) {
            if (node.left == null) {
                node.left = new AVLNode(data);
                node.left.parent = node;
            } else {
                insert(node.left, data);
            }
            //其他，往右边插入
        } else {
            if (node.right == null) {
                node.right = new AVLNode(data);
                node.right.parent = node;
            } else {
                insert(node.right, data);
            }
        }

        //计算平衡因子
        node.balance = calcBalance(node);

        // 左子树高，应该右旋
        if (node.balance >= 2) {
            // 右孙高，先左旋
            if (node.left.balance == -1) {
                left_rotate(node.left);
            }
            right_rotate(node);
        }
        // 右子树高，左旋
        if (node.balance <= -2) {
            // 左孙高，先右旋
            if (node.right.balance == 1) {
                right_rotate(node.right);
            }
            left_rotate(node);
        }
        //调整之后，重新计算平衡因子和树的深度
        node.balance = calcBalance(node);
        node.depth = calcDepth(node);
    }

    private void right_rotate(AVLNode right) {
    }

    private int calcDepth(AVLNode node) {
        int left_depth;
        int right_depth;
        //左子树深度
        if (node.left != null) {
            left_depth = node.left.depth;
        } else {
            left_depth = 0;
        }
        //右子树深度
        if (node.right != null) {
            right_depth = node.right.depth;
        } else {
            right_depth = 0;
        }
        return Math.max(left_depth, right_depth) + 1;
    }

    private void left_rotate(AVLNode left) {

    }

    private int calcBalance(AVLNode node) {
        int left_depth;
        int right_depth;
        //左子树深度
        if (node.left != null) {
            left_depth = node.left.depth;
        } else {
            left_depth = 0;
        }
        //右子树深度
        if (node.right != null) {
            right_depth = node.right.depth;
        } else {
            right_depth = 0;
        }
        return left_depth - right_depth;
    }

    public void insert(int data) {
        if (root == null) {
            root = new AVLNode(data);
            return;
        }
        insert(root, data);
    }

    /**
     * 先序遍历
     */
    public void preOrder(AVLNode node) {
        if (node == null) {
            return;
        }
        preOrder(node.left);
        System.out.print(node.data + "\t");
        preOrder(node.right);
    }

    /**
     * 平衡二叉树节点
     */
    class AVLNode {
        public int data;//保存节点数据
        public int depth;//保存节点深度
        public int balance;//是否平衡
        public AVLNode parent;//指向父节点
        public AVLNode left;//指向左子树
        public AVLNode right;//指向右子树

        public AVLNode(int data) {
            this.data = data;
            depth = 1;
            balance = 0;
            left = null;
            right = null;
        }
    }

    @Data
    class PrintNode {
        private int data;
        private int printInterval;
    }

    int left = 0;

    public void printSimple(AVLNode root) {
        Map<Integer, List<PrintNode>> map = new HashMap<>();
        int index = 1;
        insertMap(root, 1, map, 0);
        map.values().forEach(printNodes -> {
            System.out.println(printNodes.stream().map(PrintNode::getData).collect(Collectors.toList()));
        });
    }

    public void printGraph(AVLNode root) {
        Map<Integer, List<PrintNode>> map = new HashMap<>();
        int index = 1;
        insertMap(root, 1, map, 0);
        map.values().forEach(printNodes -> {
            int alreadyPrint = 0;
            for (int i = 0; i < printNodes.size(); i++) {
                PrintNode printNode = printNodes.get(i);
                for (int j = alreadyPrint; j < printNode.printInterval-left; j++) {
                    System.out.print("\t");
                    alreadyPrint++;
                }
                System.out.print(printNode.data);
            }
            System.out.println();
        });
    }

    public void insertMap(AVLNode node, int index, Map<Integer, List<PrintNode>> map, int printInterval) {
        List<PrintNode> list = Optional.ofNullable(map.get(index)).orElse(new ArrayList<>());
        PrintNode printNode = new PrintNode();
        printNode.setData(node.data);
        printNode.setPrintInterval(printInterval);
        list.add(printNode);
        map.put(index, list);

        index++;
        if (node.left != null) {
            left = Math.min(left,printInterval - 1);
            insertMap(node.left, index, map, printInterval - 1);
        }
        if (node.right != null) {
            insertMap(node.right, index, map, printInterval + 1);
        }
    }
}
