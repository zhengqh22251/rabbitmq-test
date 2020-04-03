package com.zqh.test;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author：zhengqh
 * @date 2020/4/3 13:50
 **/
public class MyConsumer {
    //声明交换机
    private static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";
    private static String QUEUE_NAME = "SIMPLE_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 连接ip
        connectionFactory.setHost("127.0.0.1");
        // 连接端口
        connectionFactory.setPort(5672);
        // 设置VHost
        connectionFactory.setVirtualHost("/");
        // 设置用户密码
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        // 建立连接
        Connection connection = connectionFactory.newConnection();
        //创建消费通道
        Channel channel =connection.createChannel();
        // 声明交换机
        // String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",false,false,null);
        // 声明队列
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        System.out.println("等待消息--------------");
        //绑定队列和交换机   路由键？
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"zqh.test");

        //创建消费者
        Consumer consumer =new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"UTF-8");
                System.out.println("收到："+msg);
                System.out.println("consumerTag："+consumerTag);
                System.out.println("DeliveryTag："+envelope.getDeliveryTag());
            }
        };

        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
