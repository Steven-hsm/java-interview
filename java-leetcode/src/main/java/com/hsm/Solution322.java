package com.hsm;

/**
 * @Classname Solution322
 * @Description 零钱兑换
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额-1。
 * <p>
 * 你可以认为每种硬币的数量是无限的。
 * @Date 2021/4/30 15:36
 * @Created by huangsm
 */
public class Solution322 {
    public static void main(String[] args) {
        System.out.println(new Solution322().coinChange(new int[]{1, 2, 5},11));
    }

    public int coinChange(int[] coins, int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            //尝试去其中的一个去尝试
            int result = coinChange(coins, amount - coins[i]);
            if(result == -1){
                //如果是-1，表示减的数据减多了，那么本次尝试失败，进行下一次尝试
                continue;
            }
            //否则就是小于0,min表示上次的结果，result + 1 表示当前的结果
            min = min < (result +1) ? min : (result +1);
        }
        return min != Integer.MAX_VALUE ? min : -1 ;
    }

}
