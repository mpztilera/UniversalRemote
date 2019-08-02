package clayborn.universalremote.hooks.network;

import clayborn.universalremote.util.Util;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VanillaPacketInterceptorInjector {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientConnectedToServerEvent(ClientConnectedToServerEvent event)
	{
		Util.logger.info("Injecting vanilla packet interceptors...");

		if (event.getManager().channel().pipeline().get("universalremote_join_game_handler") != null)
			event.getManager().channel().pipeline().remove("universalremote_join_game_handler");
		if (event.getManager().channel().pipeline().get("universalremote_respawn_handler") != null)
			event.getManager().channel().pipeline().remove("universalremote_respawn_handler");
		event.getManager().channel().pipeline().addBefore("packet_handler", "universalremote_join_game_handler", new JoinGameInterceptor(event.getManager()));
		event.getManager().channel().pipeline().addBefore("packet_handler", "universalremote_respawn_handler", new RespawnInterceptor(event.getManager()));
	}

}
