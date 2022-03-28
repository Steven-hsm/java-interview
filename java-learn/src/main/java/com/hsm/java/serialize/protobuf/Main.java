package com.hsm.java.serialize.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Copyright © 2017 - 2022 深圳鲲鹏云智教育科技有限公司
 * TODO
 *
 * @author steven
 * @version 1.0
 * @date 2022/3/28 20:47
 */
public class Main {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        NettyMessage.NettyMessageProToBuff.Builder builder = NettyMessage.NettyMessageProToBuff.newBuilder();
        builder.setRequestId("1231");
        builder.setMsgType(1);
        builder.setData("1231");
        NettyMessage.NettyMessageProToBuff build = builder.build();
        NettyMessage.NettyMessageProToBuff nettyMessageProToBuff = NettyMessage.NettyMessageProToBuff.parseFrom(build.toByteArray());


    }

}
