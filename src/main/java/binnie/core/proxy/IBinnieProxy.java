package binnie.core.proxy;

import binnie.core.AbstractMod;
import binnie.core.proxy.IProxyCore;
import binnie.core.resource.BinnieResource;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

public interface IBinnieProxy extends IProxyCore {
   boolean isClient();

   boolean isServer();

   File getDirectory();

   void bindTexture(BinnieResource var1);

   void bindTexture(ResourceLocation var1);

   int getUniqueRenderID();

   void registerCustomItemRenderer(Item var1, IItemRenderer var2);

   void openGui(AbstractMod var1, int var2, EntityPlayer var3, int var4, int var5, int var6);

   boolean isSimulating(World var1);

   World getWorld();

   Minecraft getMinecraftInstance();

   boolean needsTagCompoundSynched(Item var1);

   Object createObject(String var1);

   void registerTileEntity(Class var1, String var2, Object var3);

   void createPipe(Item var1);

   boolean isDebug();

   void registerBlockRenderer(Object var1);

   IIcon getIcon(IIconRegister var1, String var2, String var3);
}
