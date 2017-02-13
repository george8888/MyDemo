package com.mqtt.paho;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mqtt.DataConvertUtil;

public class MQTTServer {

	public static final String SEVER_STR = "tcp://10.178.4.21:1883";
	public static final short KEEP_ALIVE = 30;
	private final static boolean CLEAN_SESSION = true;  
	private final static boolean IS_RETAIN = false;  
	private final static int TIME_OUT =1000;
    public static final Logger LOG = LoggerFactory.getLogger(MQTTServer.class);
    private static final String CLIENT_ID = UUID.randomUUID().toString();
    private MqttConnectOptions mqttOption = new MqttConnectOptions();
    private MqttClient mqttClient = null;
    public MQTTServer() {
          init();
    }
    
    public void init(){
    	mqttOption.setCleanSession(CLEAN_SESSION);
    	mqttOption.setConnectionTimeout(TIME_OUT);
    	mqttOption.setKeepAliveInterval(KEEP_ALIVE);
    	try {
    		mqttClient = new MqttClient(SEVER_STR, CLIENT_ID,new MemoryPersistence());
			mqttClient.connect(mqttOption);
			LOG.debug("mqtt初始化成功");
		} catch (MqttException e) {
			LOG.debug("mqtt初始化连接失败，请检查配置，broker:[{}],cleanSession:[{}],timeOut:[{}]");
			e.printStackTrace();
		}
    }
    
    public void publish(){
    	int count = 0;
    	try {
    		while(count<1){
    			String topic = "NGTP";
    			String msg = "01107E02503031313030303130303034333134327E03007E0400D87E05027E06017E0742B245167E08207E0900003D7E100705469901E7F54E000000000000000000000000000000000000000000000000000000000500000705469901E7F54EC87E20000000010064700101";
    			MqttMessage mm = new MqttMessage(DataConvertUtil.hexStringToBytes(msg));
    			mm.setQos(2);
                mm.setRetained(IS_RETAIN);
    			mqttClient.publish(topic, mm);
    			LOG.debug("mqtt发布成功,topic=[{}],msgContent={}",topic,msg);
    			count++;
    			try {
					Thread.sleep(1000*count);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
		} catch (MqttException e) {
			LOG.debug("mqtt订阅失败，topic:[{}]");
			e.printStackTrace();
		}
    }
    
    
    public static void main(String[] args) { 
    	MQTTServer server = new MQTTServer();
    	server.publish();
	}
    
}
