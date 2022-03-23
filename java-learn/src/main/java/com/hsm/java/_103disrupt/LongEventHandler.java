package com.hsm.java._103disrupt;

import com.hsm.java._103disrupt.event.LongEvent;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ：杨过
 * @date ：Created in 2020/8/29
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description: 事件消费者
 **/
@Slf4j
public class LongEventHandler implements EventHandler<LongEvent> {
    private long serial = 0;

    public LongEventHandler(long serial){
        this.serial = serial;
    }

    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        log.info("消费者-{}:{}",this.serial,event.getValue());
    }

}
