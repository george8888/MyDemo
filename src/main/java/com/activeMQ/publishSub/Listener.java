package com.activeMQ.publishSub;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class Listener implements MessageListener {  
	   
    public void onMessage(Message message) {  
        try {  
        	TextMessage txtMsg =  (TextMessage) message;  
		    String msg = txtMsg.getText();  
            System.out.println("消息内容为："+msg);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
} 