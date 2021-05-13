package com.scrat.background;

import com.scrat.background.module.queue.QueueData;
import com.scrat.background.module.queue.MyQueueSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitMQTests {
    @Autowired
    private MyQueueSender myQueueSender;

    @Test
    void sendSimpleMsg() {
        myQueueSender.sendSimpleMsg(new QueueData<String>().setType(123).setData("Hello"));
    }

    @Test
    void sendPriorityMsg() {
        myQueueSender.sendPriorityMsg(new QueueData<String>().setType(1).setData("1"), 1);
        myQueueSender.sendPriorityMsg(new QueueData<String>().setType(1).setData("8"), 8);
        myQueueSender.sendPriorityMsg(new QueueData<String>().setType(1).setData("2"), 2);
        myQueueSender.sendPriorityMsg(new QueueData<String>().setType(1).setData("9"), 9);
        myQueueSender.sendPriorityMsg(new QueueData<String>().setType(1).setData("3"), 3);
        myQueueSender.sendPriorityMsg(new QueueData<String>().setType(1).setData("10"), 10);
    }
}
