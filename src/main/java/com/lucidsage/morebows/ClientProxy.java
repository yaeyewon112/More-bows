package com.lucidsage.morebows;

import com.lucidsage.morebows.item.IItemToRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		for (IItemToRegister b : MoreBows.bows) { b.clientInit(itemModelMesher); }
	}
}