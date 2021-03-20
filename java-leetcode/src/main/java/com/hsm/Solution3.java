package com.hsm;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @description:给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * @author: huangsm
 * @createDate: 2021/3/20
 */
public class Solution3 {

    public static void main(String[] args) {
        new Solution3().lengthOfLongestSubstring("abcdadr");
    }

    public int lengthOfLongestSubstring(String s) {
        // 记录字符上一次出现的位置
        Map<Character,Integer> map = new HashMap<>();
        int n = s.length();

        int result = 0;
        int start = 0; // 窗口开始位置
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
//            Integer last = Optional.of(map.get(c)).orElse(-1);
            Integer last = -1;
            if(map.containsKey(c)){
                last = map.get(c);
            }
            //滑动窗口的起始位置
            start = Math.max(start, last + 1);
            //取活动窗口能取到的最大值
            result   = Math.max(result, i - start + 1);
            map.put(c,i);
        }

        return result;
    }
}
