package io.github.fsaxz.freezedevmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class Main implements ClientModInitializer{

	@Override
	public void onInitializeClient() {
		ClientPlayConnectionEvents.JOIN.register(new Listener());
	}

}
