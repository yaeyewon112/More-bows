package com.lucidsage.morebows.item;

import net.minecraft.client.renderer.ItemModelMesher;

public interface IItemToRegister {
    void init();
    void clientInit(ItemModelMesher itemModelMesher);
}
