package kr.kro.yewonmods.morebowsartis;

import kr.kro.yewonmods.morebowsartis.Item.IItemToRegister;
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