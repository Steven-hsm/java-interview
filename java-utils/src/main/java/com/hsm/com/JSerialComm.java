package com.hsm.com;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * @Classname JSerialComm
 * @Description TODO
 * @Date 2021/9/4 9:42
 * @Created by huangsm
 */
public class JSerialComm {
    public static void main(String[] args) {
       /* snyReturnData();
        writeTest();*/
        SerialPort[] comPorts = SerialPort.getCommPorts();
        for (SerialPort comPort : comPorts) {
            System.out.println(comPort.getPortDescription());
            System.out.println(comPort.getDescriptivePortName());
            System.out.println(comPort.getSystemPortName());
        }

    }

    /*
     *  测试向com口发送数据
     * */
    private static void writeTest(){
        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING,10000,100000);
        String string="0000";
        comPort.writeBytes(string.getBytes(),string.getBytes().length);
    }
    /*
    测试收到数据
    * */
    private static void snyReturnData(){
        SerialPort comPort = SerialPort.getCommPorts()[1];
        comPort.openPort();
        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }
            @Override
            public void serialEvent(SerialPortEvent event)
            {
                byte[] newData = event.getReceivedData();
                System.out.println("Received data of size: " + newData.length);
                for (int i = 0; i < newData.length; ++i)
                    System.out.print((char)newData[i]);
                System.out.println("\n");
            }
        });
    }
}