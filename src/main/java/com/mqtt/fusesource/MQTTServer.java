package com.mqtt.fusesource;

import java.net.URISyntaxException;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import com.mqtt.DataConvertUtil;

public class MQTTServer{

	public static final String SEVER_STR = "tcp://10.178.9.88:1883";
	public static final short KEEP_ALIVE = 30;
	private final static boolean CLEAN_START = true;  
	public final  static long RECONNECTION_ATTEMPT_MAX=6;  
    public final  static long RECONNECTION_DELAY=2000;  
    public final static int SEND_BUFFER_SIZE=2*1024*1024;//发送最大缓冲为2M
    MQTT mqtt = null;
    BlockingConnection connection = null;
    volatile int count=0;  
    
    public  static Topic[] topics = {  
            new Topic("abcdefg", QoS.EXACTLY_ONCE)  
            };  
    
    
    public void init(){
        mqtt= new MQTT();
   		try {
   			   //设置链接服务器数据
   			   mqtt.setHost(SEVER_STR);
   			   //设置客户端唯一可识别的ID
   			  mqtt.setClientId("TestMqtt1");
   			  //连接前清空会话信息  
               mqtt.setCleanSession(CLEAN_START);  
               //设置重新连接的次数  
               mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);  
               //设置重连的间隔时间  
               mqtt.setReconnectDelay(RECONNECTION_DELAY);  
               //设置心跳时间  
               mqtt.setKeepAlive(KEEP_ALIVE);  
               mqtt.setUserName("admin");
               mqtt.setPassword("password");
               //设置缓冲的大小  
               mqtt.setSendBufferSize(SEND_BUFFER_SIZE);
               
   		}catch(URISyntaxException e){
   			System.out.println("MQTT初始化失败");
   			e.printStackTrace();
   		}
       }
       
       public void publish(){
       	    if(mqtt == null){
       	        System.out.println("MQTT发布失败，MQTT未初始化！");
       	    	return ;
       	    }
       	  try{
   	            connection = mqtt.blockingConnection();
   				connection.connect();
                while(true){  
                 count++;  
                 //订阅的主题  
                 String topic="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                 //主题的内容  
                 String message="mqtt消息测试  "+count;  
                 connection.publish(topic, message.getBytes(), QoS.EXACTLY_ONCE, false);  
                 System.out.println("MQTTServer Message  Topic="+topic+"  Content :"+ message);  
                 //Thread.sleep(count*1000);
               }
             
       	    }catch(InterruptedException e){
       	    	System.out.println("发布过程被打断");
       	    	e.printStackTrace();
       	    }catch (Exception e) {
       	    	e.printStackTrace();
   			}
       }
       
   	public static void main(String[] args) {
   		     final MQTTServer server = new MQTTServer();
   		     server.init();
   		     server.publish();
   		     //server.publish();
   		   /* for(int i=0;i<1;i++){
   		    	 Thread d = new Thread(server);
   		    	 d.start();
   		   }*/
   	}

	/*public void run() {
		Thread currentT = Thread.currentThread();
		LOG.debug("name:"+currentT.getName()+" state:"+currentT.getState()+" class:"+currentT.getClass());
		publish();
	}*/
			 
}
