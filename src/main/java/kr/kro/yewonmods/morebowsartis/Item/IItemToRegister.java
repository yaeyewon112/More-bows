package kr.kro.yewonmods.morebowsartis.Item;

import net.minecraft.client.renderer.ItemModelMesher;

public interface IItemToRegister {
    void init();
    void clientInit(ItemModelMesher itemModelMesher);
}
