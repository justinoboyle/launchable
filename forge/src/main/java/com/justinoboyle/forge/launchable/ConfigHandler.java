package com.justinoboyle.forge.launchable;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final CustomText CUSTOM_TEXT = new CustomText(BUILDER);

	public static class CustomText {
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> autojoinIP;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> jsonServer;
		static final String DEFAULT_TEXT = "localhost";

		CustomText(ForgeConfigSpec.Builder builder) {
			builder.push("IP address");
			autojoinIP = builder.comment("Insert the IP you want to autojoin")
					.translation("com.justinoboyle.launchable.ip")
					.defineList("text", Lists.newArrayList(DEFAULT_TEXT), o -> o instanceof String);
			jsonServer = builder.comment("Server configuration. Remove : to use other server.")
					.translation("com.justinoboyle.launchable.server")
					.defineList("text", Lists.newArrayList(":2060"), o -> o instanceof String);
			builder.pop();
		}

	}
	
	public static final ForgeConfigSpec spec = BUILDER.build();
}