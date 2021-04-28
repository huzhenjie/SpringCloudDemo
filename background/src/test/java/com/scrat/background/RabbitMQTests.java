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
    void testSend() {
        myQueueSender.send(new QueueData<String>().setType(123).setData("fuck"));
    }
}
