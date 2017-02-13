package com.activeMQ.pointTopoint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SendMessageTest {
	 private static final String URL ="tcp://127.0.0.1:61616";  
	 private static final String QUEUE_NAME ="upLinkfilterRousetRequestQueue";  
	 private static final String STR = "Test MQ Message";
	 
	 public void sendMessage(){
		 Connection conn = null;
		 try{
			 ActiveMQConnectionFactory connFy = new ActiveMQConnectionFactory(URL);
			 conn = (Connection) connFy.createConnection();
			 conn.start();
			 Session session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
			 Destination ds = session.createQueue(QUEUE_NAME);
			 MessageProducer mp = session.createProducer(ds);
			 System.out.println(new SimpleDateFormat("YYYY-mm-dd HH:mm:ss").format(new Date()));
			 for(int i=0 ;i<100000;i++){
				 TextMessage message = session.createTextMessage(STR+i);
				 message.setStringProperty("headname", "remoteB");
				 mp.send(message);
			 }
			 System.out.println(new SimpleDateFormat("YYYY-mm-dd HH:mm:ss").format(new Date()));
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	 
	 public static void main(String[] args) {
		SendMessageTest smt = new SendMessageTest();
		smt.sendMessage();
	}
}
