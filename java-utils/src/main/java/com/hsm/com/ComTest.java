package com.hsm.com;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname ComTest
 * @Description com串口通信
 * @Date 2021/9/4 10:44
 * @Created by huangsm
 */
public class ComTest {
    /**
     * <com名称,SerialPort>串口通信map，存储串口名称与串口信息
     */
    private Map<String, SerialPort> comMap = new HashMap<>();
    /**
     * com口列表
     */
    private List<String> comList = new ArrayList<>();

    public ComTest() {
        //将所有的串口信息放入comlist,comMap中
        SerialPort[] commPorts = SerialPort.getCommPorts();
        for (SerialPort commPort : commPorts) {
            comList.add(commPort.getSystemPortName());
            comMap.put(commPort.getSystemPortName(), commPort);
            //监听所有串口通信的数据
            commPort.openPort();
            commPort.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                }

                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    byte[] newData = serialPortEvent.getReceivedData();
                    System.err.println(String.format("串口%s接收到数据大小：%s,串口数据内容:%s"
                            ,serialPortEvent.getSerialPort().getSystemPortName(),newData.length,new String(newData)));
                }
            });
        }
    }

    public static void main(String[] args) throws Exception {
        ComTest comTest = new ComTest();
        List<String> comList = comTest.comList;
        Map<String, SerialPort> comMap = comTest.comMap;
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("你的可以通信的串口列表");
            for (String comName : comList) {
                System.out.println("========" + comName + "========");
            }
            System.out.println("请输入你的串口：");
            String com = systemIn.readLine();
            if(!comList.contains(com)){
                System.out.println("输入的串口不正确：");
                continue;
            }
            System.out.println("输入你要发送的消息：");
            String msg = systemIn.readLine();
            SerialPort serialPort = comMap.get(com);
            serialPort.writeBytes(msg.getBytes(),msg.getBytes().length);
            //这里是让串口先接收到数据再重新发送数据
            Thread.sleep(1000);
        }

    }
}
