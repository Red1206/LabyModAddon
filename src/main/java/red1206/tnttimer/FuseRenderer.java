package red1206.tnttimer;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class FuseRenderer {
	public void renderFuse(EntityTNTPrimed tnt, double x, double y, double z, float partialTicks, boolean ct,
			boolean bg) {
		Minecraft mc = Minecraft.getMinecraft();
		RenderManager rm = mc.getRenderManager();
		float distance = tnt.getDistanceToEntity(rm.livingPlayer);
		if (distance <= 4096.0F) {
			double number = Math.max((tnt.fuse - partialTicks) / 20.0D, 0.0D);
			String str = new DecimalFormat("0.00").format(number);
			RenderManager renderManager = rm;
			float green = Math.min(tnt.fuse / 80.0F, 1.0F);
			Color color = new Color(1.0F - green, green, 0.0F);
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y + tnt.height + 0.5D, z);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(-rm.playerViewY, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(rm.playerViewX, 1.0F, 0.0F, 0.0F);
			float f = 0.016666668F * 1.6F;
			GlStateManager.scale(-f, -f, f);
			GlStateManager.disableLighting();
			GlStateManager.depthMask(false);
			GlStateManager.disableDepth();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			int i = 0;
			int j = rm.getFontRenderer().getStringWidth(str) / 2;
			GlStateManager.disableTexture2D();
			if (bg) {
				Tessellator tessellator = Tessellator.getInstance();
				WorldRenderer worldrenderer = tessellator.getWorldRenderer();
				worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
				worldrenderer.pos(-j - 1.0D, -1.0D + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
				worldrenderer.pos(-j - 1.0D, 8.0D + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
				worldrenderer.pos(j + 1.0D, 8.0D + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
				worldrenderer.pos(j + 1.0D, -1.0D + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
				tessellator.draw();
			}
			GlStateManager.enableTexture2D();
			rm.getFontRenderer().drawString(str, -j, i, ct ? color.getRGB() : 16777215);
			GlStateManager.enableDepth();
			GlStateManager.depthMask(true);
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.popMatrix();
		}
	}
}