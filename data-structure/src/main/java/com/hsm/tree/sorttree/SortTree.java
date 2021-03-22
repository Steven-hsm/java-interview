package com.hsm.tree.sorttree;

import com.hsm.tree.avl.AVLNode;
import lombok.Getter;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/22
 */
public class SortTree {
    @Getter
    private  SortTreeNode root;
    class SortTreeNode{
        private Integer data;
        private SortTreeNode left;
        private SortTreeNode right;

        public SortTreeNode(Integer data) {
            this.data = data;
        }
    }

    public void insert(SortTreeNode node, int data){
        //小于节点往左边插入
        if(data < node.data){
            if(node.left == null){
                node.left = new SortTreeNode(data);
            }else{
                insert(node.left,data);
            }
        //其他，往右边插入
        }else{
            if(node.right == null){
                node.right = new SortTreeNode(data);
            }else{
                insert(node.right,data);
            }
        }
    }

    public void insert(int data){
        if(root == null){
            root = new SortTreeNode(data);
            return;
        }
        insert(root,data);
    }

    /**
     * 先序遍历
     */
    public void preOrder(SortTreeNode node){
        if(node == null){
            return;
        }
        preOrder(node.left);
        System.out.print(node.data + "\t");
        preOrder(node.right);
    }

}
