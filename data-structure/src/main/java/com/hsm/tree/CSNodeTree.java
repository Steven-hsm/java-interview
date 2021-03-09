package com.hsm.tree;

import lombok.Data;

@Data
public class CSNodeTree {
    Object data; //数据元素
    CSNodeTree firstChild;//第一个孩子
    CSNodeTree rightsib;//右兄弟
}
