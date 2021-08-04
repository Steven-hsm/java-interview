package com.hsm.brain.utils;

import com.hsm.brain.model.po.UserPO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;

/**
 * @Classname JWTUtils
 * @Description jwt工具类
 * @Date 2021/8/4 16:57
 * @Created by huangsm
 */
public class JWTUtils {
    private static final String SING_KEY = Base64.getEncoder().encodeToString("JWTUtils".getBytes());

    public static String createToken(UserPO user, Date expireDate) {
        // jwt
        return Jwts.builder()
                //主题名称
                .setSubject(user.getOpenId())
                //自定义时间
                .claim("openId", user.getOpenId())
                .setExpiration(expireDate)
                //签名算法和秘钥
                .signWith(SignatureAlgorithm.HS512, SING_KEY)
                .compact();
    }

    public static Claims parseToken(String Token){
        return Jwts.parser().setSigningKey(SING_KEY).parseClaimsJws(Token).getBody();
    }


}
