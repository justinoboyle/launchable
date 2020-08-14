package com.justinoboyle.forge.launchable;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.justinoboyle.forge.launchable.util.ZipUtil;

import net.minecraft.network.ServerStatusResponse;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.versions.forge.ForgeVersion;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("launchable")
public class LaunchableMod {
	// Directly reference a log4j logger.
	public static final String MOD_ID = "launchable";

	private static final File MODS_FOLDER = new File("mods/");
	private static final File CONFIG_FOLDER = new File("config/");

	public LaunchableMod() {

		try {
			new File("static/").mkdir();
			ZipUtil.zipFolder(MODS_FOLDER.toPath(), new File("static/mods.zip").toPath());
			ZipUtil.zipFolder(CONFIG_FOLDER.toPath(), new File("static/config.zip").toPath());
			String s = "loader:forge version:" + ForgeVersion.getVersion() + " group:" + ForgeVersion.getGroup()
					+ " spec:" + ForgeVersion.getSpec() + " status:" + ForgeVersion.getStatus() + " target:"
					+ ForgeVersion.getTarget();
			Files.write(Paths.get(new File("static/version.txt").getAbsolutePath()), s.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		modBus.addListener(this::setupClient);
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.spec);

	}

	@SubscribeEvent
	public void serverTick(TickEvent.ServerTickEvent event) {
		if (event.phase != TickEvent.Phase.START)
			return;

		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		ServerStatusResponse response = ServerLifecycleHooks.getCurrentServer().getServerStatusResponse();

		response.setServerDescription(new StringTextComponent(new File(".").getAbsolutePath() + " / " + server.getMOTD()
				+ "\n\n\n\n§0launchable=" + ConfigHandler.CUSTOM_TEXT.jsonServer.get().get(0)));
	}

	private void setupClient(final FMLClientSetupEvent event) {
		TitleScreenHandler.registerReloadListener();
	}

}
