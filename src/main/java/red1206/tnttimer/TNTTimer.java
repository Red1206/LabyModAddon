package red1206.tnttimer;

import java.util.List;

import net.labymod.api.LabyModAddon;
import net.labymod.api.events.RenderEntityEvent;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement.IconData;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class TNTTimer extends LabyModAddon {
	private boolean enabled;
	private boolean ct;
	private boolean bg;
	private FuseRenderer fr = new FuseRenderer();

	public void onEnable() {
		getApi().registerForgeListener(new FuseRenderer());
		getApi().getEventManager().register(new RenderEntityEvent() {
			public void onRender(Entity entity, double x, double y, double z, float partialTicks) {
				if (enabled && Minecraft.getMinecraft().isGuiEnabled() && entity instanceof EntityTNTPrimed) {
					fr.renderFuse((EntityTNTPrimed) entity, x, y, z, partialTicks, ct, bg);
				}
			}
		});
	}

	public void onDisable() {
	}

	public void loadConfig() {
		enabled = getConfig().has("enable") ? getConfig().get("enabled").getAsBoolean() : true;
		ct = getConfig().has("ct") ? getConfig().get("ct").getAsBoolean() : true;
		bg = getConfig().has("bg") ? getConfig().get("bg").getAsBoolean() : true;
	}

	protected void fillSettings(List<SettingsElement> list) {
		list.add(new BooleanElement("Enabled", this, new IconData(Material.EMERALD), "enabled", enabled));
		list.add(new BooleanElement("Color Text", this,
				new IconData(
						new ResourceLocation("minecraft", "labymod/textures/settings/modules/ping_coloredping.png")),
				"ct", ct));
		list.add(new BooleanElement("Text Background", this, new IconData(
				new ResourceLocation("minecraft", "labymod/textures/settings/modules/scoreboard_background.png")),
				"bg", bg));
	}
}