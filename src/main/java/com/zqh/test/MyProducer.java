package com.zqh.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author：zhengqh
 * @date 2020/4/3 13:35
 **/
public class MyProducer {
    // 定义交换机名称  第一次的时候服务端需要先启动声明这个交换机 否则会报错404
    private static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //连接ip
        connectionFactory.setHost("127.0.0.1");
        //连接端口
        connectionFactory.setPort(5672);
        //设置VHost
        connectionFactory.setVirtualHost("/");
        //设置用户
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //建立连接
        Connection connection = connectionFactory.newConnection();
        //创建消息通道
        Channel channel = connection.createChannel();
        //发送消息
        String msg = "MY FIRST TEST RABBITMQ";
        // String exchange, String routingKey, BasicProperties props, byte[] body
        channel.basicPublish(EXCHANGE_NAME,"zqh.test",null,msg.getBytes());

        channel.close();
        connection.close();
    }
}
