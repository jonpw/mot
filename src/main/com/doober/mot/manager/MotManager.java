package com.doober.mot.manager;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MotManager {

	public static String clientId;;
	public static String broker = "tcp://iot.eclipse.org:1883";
	public static MqttAsyncClient client;
	public static Map<String, String> datamap = new HashMap<String, String>();
	public static Listener listener;
	
	public MotManager (String clientId) {
		MotManager.clientId = clientId;
        String topic        = "mot/register";
        String content      = "New client";
        int qos             = 2;
        listener = new Listener();
        
        MemoryPersistence persistence = new MemoryPersistence();
        
        try {
            client = new MqttAsyncClient(broker, clientId, persistence);
            client.setCallback(listener);
            IMqttToken conToken;
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            conToken = client.connect(connOpts);
            conToken.waitForCompletion();
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            conToken = client.publish(topic, message);
            conToken.waitForCompletion();
            System.out.println("Registered to server");
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
	}

	public static void update(World world, BlockPos pos, String updStr) {
        try {
        	IMqttToken conToken;
			conToken = client.publish("mot/"+Integer.toString(pos.getX())+","+Integer.toString(pos.getY())+","+Integer.toString(pos.getZ()), new MqttMessage(updStr.getBytes()));
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("click!");
	}
	
	public static void register(World world, BlockPos pos) {
		String topic = "mot/"+Integer.toString(pos.getX())+","+Integer.toString(pos.getY())+","+Integer.toString(pos.getZ());
		try {
			IMqttToken conToken = client.subscribe(topic, 2, listener);
			System.out.println("subbed to "+topic);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean newUpdate(World world, BlockPos pos) {
		String strPos = Integer.toString(pos.getX())+","+Integer.toString(pos.getY())+","+Integer.toString(pos.getZ());
		System.out.println("newUpdate?:"+strPos);
		return datamap.containsKey(strPos);
	}
	
	public String getUpdate(BlockPos pos) {
		String strPos = Integer.toString(pos.getX())+","+Integer.toString(pos.getY())+","+Integer.toString(pos.getZ());
		String updStr = datamap.get(strPos);
		
		datamap.remove(strPos);
		return updStr;
	}
}
