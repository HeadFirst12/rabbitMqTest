package com.hy.rabbitmq.work.fair.send;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * 工作队列-公平-消息生产者
 */
public class Send {

    //定义队列名称
    private final static String QUEUE_NAME = "work-fair";

    public static void main(String[] argv) throws Exception {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);

        try (
                //连接工厂创建连接
                Connection connection = factory.newConnection();
                //创建信道
                Channel channel = connection.createChannel()) {
            /**
             * 绑定队列
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            for (int i = 0; i < 20; i++) {
                String message = "Hello World!"+i;
                //发送消息
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}

