package com.hsm.java.serialize.protobuf;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * Copyright © 2017 - 2022 深圳鲲鹏云智教育科技有限公司
 * TODO
 *
 * @author steven
 * @version 1.0
 * @date 2022/3/28 20:47
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        NettyMessage.NettyMessageProToBuff.Builder builder = NettyMessage.NettyMessageProToBuff.newBuilder();
        builder.setRequestId("qqq");
        builder.setMsgType(1);
        builder.setData("a");


        NettyMessage.NettyMessageProToBuff build = builder.build();
        log.info("build数据：{}" , build.toByteArray());
        NettyMessage.NettyMessageProToBuff nettyMessageProToBuff = NettyMessage.NettyMessageProToBuff.parseFrom(build.toByteArray());
        log.info("数据：{}" , JSON.toJSONString(nettyMessageProToBuff));

        JsonData jsonData = new JsonData();
        jsonData.setRequestId("q");
        jsonData.setMsgType(1);
        jsonData.setData("a");

        log.info("Jsondata：{}" , JSON.toJSONString(jsonData).getBytes(StandardCharsets.UTF_8));


        NettyMessage.Person.Builder person = NettyMessage.Person.newBuilder();
        person.setId(24);
        person.setName("wujingchao");
        person.setEmail("wujingchao92@gmail.com");
        NettyMessage.Person personBuild = person.build();
        log.info("personBuild数据：{}" , personBuild.toByteArray());
    }

}
