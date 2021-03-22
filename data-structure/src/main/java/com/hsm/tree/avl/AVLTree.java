package com.hsm.tree.avl;

import com.hsm.tree.sorttree.SortTree;
import lombok.Getter;

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


    public void insert(AVLNode node, int data){
        //小于节点往左边插入
        if(data < node.data){
            if(node.left == null){
                node.left = new AVLNode(data);
                node.left.parent = node;
            }else{
                insert(node.left,data);
            }
            //其他，往右边插入
        }else{
            if(node.right == null){
                node.right = new AVLNode(data);
                node.right.parent = node;
            }else{
                insert(node.right,data);
            }
        }
    }

    public void insert(int data){
        if(root == null){
            root = new AVLNode(data);
            return;
        }
        insert(root,data);
    }
    /**
     * 先序遍历
     */
    public void preOrder(AVLNode node){
        if(node == null){
            return;
        }
        preOrder(node.left);
        System.out.print(node.data + "\t");
        preOrder(node.right);
    }
}
