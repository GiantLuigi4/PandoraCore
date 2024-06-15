package com.github.andrew0030.pandora_core;

import com.github.andrew0030.pandora_core.client.gui.screen.PaCoScreen;
import com.github.andrew0030.pandora_core.client.key.PaCoKeyMappings;
import com.github.andrew0030.pandora_core.events.ForgeClientTickEvent;
import com.github.andrew0030.pandora_core.mixin_interfaces.IPaCoParentScreenGetter;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class PandoraCoreClientForge {

    public static void init(IEventBus modEventBus, IEventBus forgeEventBus)
    {
        // Mod Event Bus
        modEventBus.addListener(PandoraCoreClientForge::clientSetup);
        // Forge Event Bus
        forgeEventBus.addListener(ForgeClientTickEvent::init);

        // Registers the PaCo KeyMappings
        PaCoKeyMappings.KEY_MAPPINGS.registerKeyBindings();
        // Registers Config Screen (Basically opens the PaCo screen if you press the config button in the Forge Mods Screen)
        MinecraftForge.registerConfigScreen(screen -> {
            if (screen instanceof IPaCoParentScreenGetter pacoParentScreenGetter)
                if (pacoParentScreenGetter.pandoraCore$getParentScreen() instanceof TitleScreen titleScreen)
                    return new PaCoScreen(titleScreen, screen);
            return new PaCoScreen(null, screen);
        });
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        // Common Module Initialization.
        PandoraCoreClient.init();
        event.enqueueWork(PandoraCoreClient::initThreadSafe);

        // Loader Module Initialization.
        // Nothing atm...
    }
}