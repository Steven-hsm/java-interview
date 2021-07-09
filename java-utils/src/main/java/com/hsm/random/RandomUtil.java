package com.hsm.random;

import java.util.Arrays;
import java.util.List;

/**
 * @Classname RandomUtil
 * @Description TODO
 * @Date 2021/7/9 20:21
 * @Created by huangsm
 */
public class RandomUtil {
    public static void main(String[] args) {
       /* List<String> list = Arrays.asList("0", "1","2","3","4","5","6","7","8","9"
                , "a","b","c","d","e","f","g","h","i"
                , "j","k","l","m","n","o","p","q","r"
                , "s","t","u","v","w","x","y","z");
        int begin = 0;
        while (begin<10000){
            String s = getString(list, begin);
            System.out.println("邀请码："+s);
            begin++;
        }*/

        String str = " 12 3 13 1 ";
        System.out.println(str.trim());;
    }
    private static String getString(List<String> list, int begin) {
        String a1;
        String a2;
        String a3;
        String a4;
        int i4 = (begin) % 36;
        int i3 = (begin/(36)) % 36;
        int i2 = (begin/(36*36)) % 36;
        int i1 = (begin/(36*36*36)) % 36;
        System.out.println(i1);
        System.out.println(i2);
        System.out.println(i3);
        System.out.println(i4);
        a1 = list.get(i1);
        a2 = list.get(i2);
        a3 = list.get(i3);
        a4 = list.get(i4);
        return a1+a2+a3+a4;
    }


}
