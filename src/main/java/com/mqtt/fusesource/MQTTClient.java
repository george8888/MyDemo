package com.mqtt.fusesource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mqtt.DataConvertUtil;

public class MQTTClient{
	public static final String SEVER_STR = "tcp://192.168.1.60:1883";
	public static final short KEEP_ALIVE = 30;
	private final static boolean CLEAN_START = false;  
	public final  static long RECONNECTION_ATTEMPT_MAX=6;  
    public final  static long RECONNECTION_DELAY=2000;  
    public final static int SEND_BUFFER_SIZE=2*1024*1024;//发送最大缓冲为2M
    public static final Logger LOG = LoggerFactory.getLogger(MQTTServer.class);
    public static final String FILE_PATH ="D:\\test\\Test.txt";
    MQTT mqtt = null;
    BlockingConnection connection = null;
    FileOutputStream fos = null;
    OutputStreamWriter osw  = null;
    public MQTTClient() {
    	init();
	}
    
    
    public void init(){
     mqtt= new MQTT();
		try {
			//设置链接服务器数据
			mqtt.setHost(SEVER_STR);
			 //设置客户端唯一可识别的ID
			 mqtt.setClientId("c3a8fa54-a286-46a1-b955-07be38c64bb6");
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
            
            try {
				fos = new FileOutputStream(new File(FILE_PATH));
				osw = new OutputStreamWriter(fos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			
		}catch(URISyntaxException e){
			LOG.debug("MQTT初始化失败，exception:[{}]",e);
		}
    }
    
    public void subscribe(){
    	    if(mqtt == null){
    	    	LOG.error("MQTT发布失败，MQTT未初始化！");
    	    	return ;
    	    }
    	    
    	    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	    String now = sf.format(new Date());
    	    StringBuffer sb = new StringBuffer(); 
    	  try{
    		  
    		   for(int i=0;i<10;i++){
    			   connection = mqtt.blockingConnection();
   				   connection.connect();
   				  LOG.debug("第"+i+"次connect成功，connectFlag={},Date={}",connection.isConnected(),now);
   				  sb.append("第"+i+"次connect成功，connectFlag="+connection.isConnected()+",Date="+now+"\r\n");
   				//服务器订阅接收消息主题
                   String[] topic_strs = "P011000700000186/NGTP".split("[ ]*@,@[ ]*");
                   if (topic_strs == null || topic_strs.length == 0) {
                       throw new  Exception("MQTT_SERVER_EXCEPTION_TOPIC_IS_NULL");
                   }
                   final Topic[] topics = new Topic[topic_strs.length];
                   for (int j = 0; i < topic_strs.length; i++) {
                       if (topic_strs[i] != null && topic_strs[i].trim().length() != 0) {
                           topics[i] = new Topic(topic_strs[i], QoS.EXACTLY_ONCE);
                       }
                   }
                   connection.subscribe(topics);
                   //接收订阅的消息内容  
                   Message message = connection.receive();  
                   //获取订阅的消息内容   
                   byte[] payload = message.getPayload();  
                   String content = DataConvertUtil.byte2HexString(payload) +"\r\n";
                   sb.append("第"+i+"次接收成功，topic="+topics[0]+"   content="+content +"   Date="+now+"\r\n");
                   LOG.debug("MQTTClient Message  Topic="+message.getTopic()+" Content :"+DataConvertUtil.byte2HexString(payload));  
                   //签收消息的回执  
                   message.ack();
                   LOG.debug("第"+i+"次subscribe成功，topic={}",topics[0]);
                   sb.append("第"+i+"次subscribe成功，topic="+topics[0]+"\r\n");
                   Thread.sleep(2000);
                   LOG.debug("第"+i+"次休眠，休眠时间={}s",2);
                   sb.append("第"+i+"次休眠，休眠时间="+2+"s\r\n");
                   connection.disconnect();
                   LOG.debug("第"+i+"次disconnect成功，connectFlag={},Date={}",connection.isConnected(),now);
                   sb.append("第"+i+"次disconnect成功，connectFlag="+connection.isConnected()+",Date="+now+"\r\n");
                   
                   
                   osw.write(sb.toString());
                   osw.flush();
    		   }
	            
    	    }catch(InterruptedException e){
    	    	LOG.debug("接收过程被打断",e);
    	    	e.printStackTrace();
    	    }catch (Exception e) {
    	    	e.printStackTrace();
			}
    }
    
	public static void main(String[] args) {
		  new MQTTClient().subscribe();
	}
}
