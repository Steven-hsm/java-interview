package com.hsm.tree;

import lombok.Data;

@Data
class PTNode {
    private Object data;//节点数据
    private int parent;//双亲位置
}

@Data
public class PTNodeTree{
    private PTNode nodes[];//结点数组
    private int root;//根节点的位置
    private int num;//结点数
}
