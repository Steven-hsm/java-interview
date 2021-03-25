package com.hsm.tree.binary;

import lombok.Data;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/25
 */
@Data
public class BinaryTree<T extends BinaryNode> {
    /**
     * 树的根节点
     */
    private T root;


}
