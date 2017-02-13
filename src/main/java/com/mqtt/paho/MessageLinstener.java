package com.mqtt.paho;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mqtt.DataConvertUtil;

public class MessageLinstener implements IMqttMessageListener{

    public static final Logger LOG = LoggerFactory.getLogger(MessageLinstener.class);
    //private static final String URL ="tcp://127.0.0.1:61616";  
	//private static final String QUEUE_NAME ="myQueue";  
	 
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		    byte[] payload= message.getPayload();
		    //sendMessage(new String(payload));
		    LOG.debug("消息订阅成功，message=[{}],topic=[{}]",DataConvertUtil.byte2HexString(payload),topic);
	}
	
/*	public void sendMessage(String content){
		 Connection conn = null;
		 try{
			 ActiveMQConnectionFactory connFy = new ActiveMQConnectionFactory(URL);
			 conn = (Connection) connFy.createConnection();
			 conn.start();
			 Session session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
			 Destination ds = session.createQueue(QUEUE_NAME);
			 MessageProducer mp = session.createProducer(ds);
			 for(int i=0 ;i<100000;i++){
				 TextMessage message = session.createTextMessage(content);
				 message.setStringProperty("headname", "remoteB");
				 mp.send(message);
				 LOG.debug("MQTT消息订阅并存储到Activemq成功！");
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }*/
}
