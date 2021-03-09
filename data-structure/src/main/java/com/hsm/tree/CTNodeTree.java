package com.hsm.tree;

import lombok.Data;

@Data
public class CTNodeTree {
    CTBox nodes[];//节点数
    private int root;//根节点的位置
    private int num;//结点数
}
@Data
class CTNode{
    int child;
    CTNode next;
}
@Data
class CTBox{
    Object data;
    CTNode firstChild;
}
