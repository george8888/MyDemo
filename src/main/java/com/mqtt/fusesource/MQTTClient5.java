package com.mqtt.fusesource;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTClient5{
	public static final String SEVER_STR = "tcp://192.168.1.60:1883";
	public static final short KEEP_ALIVE = 30;
	private final static boolean CLEAN_START = false;  
	public final  static long RECONNECTION_ATTEMPT_MAX=6;  
    public final  static long RECONNECTION_DELAY=2000;  
    public final static int SEND_BUFFER_SIZE=2*1024*1024;//发送最大缓冲为2M
    public static final Logger LOG = LoggerFactory.getLogger(MQTTClient5.class);
    MQTT mqtt = null;
    BlockingConnection connection = null;
    
    public MQTTClient5() {
    	init();
	}
    public void init(){
     mqtt= new MQTT();
		try {
			//设置链接服务器数据
			mqtt.setHost(SEVER_STR);
			 //设置客户端唯一可识别的ID
			   mqtt.setClientId("460015214704243_P011000100013887");
			//连接前清空会话信息  
            mqtt.setCleanSession(CLEAN_START);  
            //设置重新连接的次数  
            mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);  
            //设置重连的间隔时间  
            mqtt.setReconnectDelay(RECONNECTION_DELAY);  
            //设置心跳时间  
            mqtt.setKeepAlive(KEEP_ALIVE);  
            //设置缓冲的大小  
            mqtt.setSendBufferSize(SEND_BUFFER_SIZE);
		}catch(Exception e){
			LOG.debug("MQTT初始化失败，exception:[{}]",e);
		}
    }
    
    public void subscribe(){
    	    if(mqtt == null){
    	    	LOG.error("MQTT发布失败，MQTT未初始化！");
    	    	return ;
    	    }
    	  try{
	            connection = mqtt.blockingConnection();
				connection.connect();
				
				  //服务器订阅接收消息主题
                String[] topic_strs = "$queue/NGTP".split("[ ]*@,@[ ]*");
                if (topic_strs == null || topic_strs.length == 0) {
                    throw new  Exception("MQTT_SERVER_EXCEPTION_TOPIC_IS_NULL");
                }
                final Topic[] topics = new Topic[topic_strs.length];
                for (int i = 0; i < topic_strs.length; i++) {
                    if (topic_strs[i] != null && topic_strs[i].trim().length() != 0) {
                        topics[i] = new Topic(topic_strs[i], QoS.EXACTLY_ONCE);
                    }
                }
                
				connection.subscribe(topics);
	              while(true){  
	                  //接收订阅的消息内容  
	                  Message message = connection.receive();  
	                  //获取订阅的消息内容   
	                  byte[] payload = message.getPayload();  
	                  LOG.debug("MQTTClient Message  Topic="+message.getTopic()+" Content :"+new String(payload));  
	                  //签收消息的回执  
	                  message.ack();
	              }  
    	    }catch(InterruptedException e){
    	    	LOG.debug("接收过程被打断",e);
    	    	e.printStackTrace();
    	    }catch (Exception e) {
    	    	e.printStackTrace();
			} 
    }
    
	public static void main(String[] args) {
			 MQTTClient5 client = new MQTTClient5();
			 client.init();
			 client.subscribe();
	}

}
