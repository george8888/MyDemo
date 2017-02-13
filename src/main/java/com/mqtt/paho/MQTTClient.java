package com.mqtt.paho;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTClient {
	//tcp://10.178.4.21:1883
	public static final String SEVER_STR = "tcp://10.131.175.48:1883";
	public static final short KEEP_ALIVE = 30;
	private final static boolean CLEAN_SESSION = true;  
	private final static int TIME_OUT = 123444;
    public static final Logger LOG = LoggerFactory.getLogger(MQTTServer.class);
    private static final String CLIENT_ID = "460015214704232_A011000100015389";
    private MqttConnectOptions mqttOption = new MqttConnectOptions();
    private MqttClient mqttClient = null;
    public MQTTClient() {
          init();
    }
    
    public void init(){
    	mqttOption.setCleanSession(CLEAN_SESSION);
    	mqttOption.setConnectionTimeout(TIME_OUT);
    	mqttOption.setKeepAliveInterval(KEEP_ALIVE);
    	try {
			mqttClient = new MqttClient(SEVER_STR, CLIENT_ID,new MemoryPersistence());
			mqttClient.connect(mqttOption);
			LOG.debug("mqtt初始化成功,clientId={}",mqttClient.getClientId());
		} catch (MqttException e) {
			LOG.debug("mqtt初始化连接失败，请检查配置，broker:[{}],cleanSession:[{}]",SEVER_STR,CLEAN_SESSION);
			e.printStackTrace();
		}
    	
    
    }
    
    public void subcribe(){
    	try {
    		String topic = "$queue/topic";
			mqttClient.subscribe(topic, 2 , new MessageLinstener());
			LOG.debug("mqtt订阅成功，topic:[{}]",topic);
		} catch (MqttException e) {
			LOG.debug("mqtt订阅失败，topic:[{}]");
			e.printStackTrace();
		}
    }
    
    
    public static void main(String[] args) {
    	new MQTTClient().subcribe();
	}
}
