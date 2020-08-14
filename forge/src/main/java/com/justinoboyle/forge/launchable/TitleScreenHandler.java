package com.justinoboyle.forge.launchable;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConnectingScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

@Mod.EventBusSubscriber(modid = LaunchableMod.MOD_ID, value = Dist.CLIENT)
public class TitleScreenHandler implements ISelectiveResourceReloadListener {

    @SubscribeEvent
    public static void openMainMenu(final GuiScreenEvent.InitGuiEvent.Post event) {
    	
        if (event.getGui() instanceof MainMenuScreen)
        	init();

        if (event.getGui() instanceof MultiplayerScreen) 	
            Minecraft.getInstance().shutdown();
       
    }

    public static void init() {
    	ServerData data = new ServerData(ConfigHandler.CUSTOM_TEXT.autojoinIP.get().get(0), ConfigHandler.CUSTOM_TEXT.autojoinIP.get().get(0), false);
    	Minecraft mc = Minecraft.getInstance();
    	mc.displayGuiScreen(new ConnectingScreen(mc.currentScreen, mc, data));
    }

    public static void registerReloadListener() {
        ((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(new TitleScreenHandler());
    }

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager, @Nonnull Predicate<IResourceType> resourcePredicate) {
        init();
    }
}