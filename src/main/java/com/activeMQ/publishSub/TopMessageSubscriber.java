package com.activeMQ.publishSub;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class  TopMessageSubscriber{
	private ActiveMQConnectionFactory factory;
	private Connection connection;
	private Session session;
	
    public TopMessageSubscriber() throws JMSException {  
        factory = new ActiveMQConnectionFactory("mqtt+nio://192.168.1.100:1883");  
        connection = factory.createConnection();  
        connection.start();  
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
    }  
	
   public Session getSession() {  
       return session;  
   }  
   
   protected void colse() throws JMSException {
		session.close();
		connection.close();
	}
   
   public static void main(String[] args) throws JMSException {  
       TopMessageSubscriber subscriber = new TopMessageSubscriber();  
       Destination destination = subscriber.getSession().createTopic("NGTP");  
       MessageConsumer messageConsumer = subscriber.getSession().createConsumer(destination);  
       //messageConsumer.receive();
       messageConsumer.setMessageListener(new Listener());
       //subscriber.getSession().setMessageListener(new Listener());
       subscriber.colse();
   }

}
