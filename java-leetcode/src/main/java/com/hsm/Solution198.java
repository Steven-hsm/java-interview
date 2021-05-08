package com.hsm;

import java.util.Arrays;

/**
 * @Classname Solution198
 * @Description 打家劫舍
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
 * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * <p>
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
 * @Date 2021/4/30 14:40
 * @Created by huangsm
 */
public class Solution198 {
    /**
     * 思路： 共有n间房，第i间房的现金为num[i],偷到第i的总金额为sum[i]
     * 只有一家店 sum[1] = num[1]
     * 有两家店 sum[2] = max(num[1],num[2]) 领家的只能偷一家
     * 有三家店，
     * 第三家店偷了，第二家店不能偷了，sum[3] = sum[1] + num[3]
     * 第三家不偷，肯定要偷第二家才能利益最大化 sum[3] = sum[2]
     * 最终结果 sum[3] = max（ sum[1] + num[3]，sum[2]）
     * <p>
     * 最终结果 sum[i] = max(sum[i-2] + num[i],sum[i-1])
     */
    public static void main(String[] args) {
        System.out.println(new Solution198().rob(new int[]{2, 7, 9, 3, 1}));
    }

    /**
     * 递归方式会超出时间限制
     * @param nums
     * @return
     */
    /*public int rob(int[] nums) {
        return robSum(nums, nums.length-1);
    }
*/
    public int robSum(int[] nums, int i) {
        if (i == 0) return nums[i];
        if (i == 1) return max(nums[i - 1], nums[i]);
        return max(robSum(nums, i - 2) + nums[i], robSum(nums, i - 1));
    }

    public int max(int value1,int value2) {
        return value1 > value2 ? value1 : value2;
    }

    public int rob(int[] nums) {
        int sum[] = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if(i == 0){
                sum[i] = nums[i];
            }else if (i == 1){
                sum[i] = max(nums[i - 1], nums[i]);
            }else{
                sum[i] = max(sum[i-2] + nums[i],sum[i-1]);
            }
        }
        return sum[nums.length -1];
    }
}
