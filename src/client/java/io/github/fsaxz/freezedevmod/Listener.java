package io.github.fsaxz.freezedevmod;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;

public class Listener implements ClientPlayConnectionEvents.Join{

	@Override
	public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
		try {
			 ClientConnection connection = handler.getConnection();//获取客户端Connection对象
			 Field field = null;
			 for(Field f:connection.getClass().getFields()) {//获取 Channel field
				 if (f.getType() == Channel.class) {
					field = f;
					break;
				}
			 }
			 
			 field.setAccessible(true);              //解除访问限制
			 Channel channel = (Channel) field.get(connection); //转出Channel对象
			 ChannelPipeline pipeline = channel.pipeline();      //获取Pipeline对象
			 pipeline.replace("timeout", "timeout", new ReadTimeoutHandler(20,TimeUnit.MINUTES)); //设置超时(20min)
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

}
