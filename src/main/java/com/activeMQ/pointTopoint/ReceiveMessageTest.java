package com.activeMQ.pointTopoint;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;


public class ReceiveMessageTest {
 
	private static final String URL ="tcp://127.0.0.1:61616";  
	 private static final String QUEUE_NAME ="newUpLinkfilterRousetRequestQueue";  
	 static final String STR = "Hello ActiveMQ!";
	 
	 public void receiveMessage(){
		 Connection conn = null;
		 try{
			 ActiveMQConnectionFactory connFy = new ActiveMQConnectionFactory(URL);
			 conn = connFy.createConnection();
			 conn.start();
			 Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			 Destination ds  = session.createQueue(QUEUE_NAME);
			 MessageConsumer mc  = session.createConsumer(ds);
			 consumeMessagesAndClose(conn, session, mc);
			 
		 }catch(Exception e ){
			 e.printStackTrace();
		 }
	 }
	 
	 protected void consumeMessagesAndClose(Connection connection,Session session, MessageConsumer consumer)  
			 throws JMSException {  
		      System.out.println(new SimpleDateFormat("YYYY-mm-dd HH:mm:ss").format(new Date()));
			  for (int i = 0; i < 1;) {  
			   Message message = consumer.receive(1);  
			   if (message != null) {  
			    i++;  
			    onMessage(message);  
			   }  
			  }  
             System.out.println(new SimpleDateFormat("YYYY-mm-dd HH:mm:ss").format(new Date()));
			  System.out.println("Closing connection");  
			  consumer.close();  
			  session.close();  
			  connection.close();  
			 } 
	 
	 public void onMessage(Message message) {  
		  try {  
		   if (message instanceof TextMessage) {  
		    TextMessage txtMsg = (TextMessage) message;  
		    String msg = txtMsg.getText();  
		    System.out.println("Received: " + msg);  
		   }else if(message instanceof ObjectMessage){
			    System.out.println("------Received ObjectMessage------");
			    ObjectMessage msg = (ObjectMessage) message;
			   // VcaMessage jmsObject = (VcaMessage) msg.getObject();
			    //System.out.println(jmsObject.getNgtpMsgId() + "__" + jmsObject.getByteMsgContent() + "__" + jmsObject.getNgtpDownMsgBase64());
		   }
		  } catch (Exception e) {  
		   e.printStackTrace();  
		  } 
	 }
	 
	 public static void main(String[] args) {
		ReceiveMessageTest rmt = new ReceiveMessageTest();
		rmt.receiveMessage();
	}
}
