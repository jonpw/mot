package com.doober.mot.manager;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Listener implements IMqttMessageListener, MqttCallback{
	
	@Override
	public void messageArrived(String topic, MqttMessage message) {
		String [] strpos, bits;
		bits = topic.split("/");
		strpos = bits[bits.length-1].split(",");
		MotManager.datamap.put(bits[bits.length-1], message.toString());
		System.out.println("mA:"+topic+":"+message.toString());
	}
	
	@Override
	public void connectionLost (java.lang.Throwable cause)
	{
		System.out.println("Disconnected from MQTT");
	}
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken token)
	{
		System.out.println("Message delivered");
	}
	
/*	public class Updater implements Runnable {
		public BlockPos pos;
		
		public Updater (BlockPos pos) {
			this.pos = pos;
		}
		
		public void run() {
			World worldIn = Minecraft.getMinecraft().world;
	        worldIn.scheduleUpdate(this.pos, worldIn.getBlockState(this.pos).getBlock(), 4);

		}
	}*/
}
