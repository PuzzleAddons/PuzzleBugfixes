package com.github.puzzle.bugfix;

import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.ModInitializer;
import com.github.puzzle.game.PuzzleRegistries;
import com.github.puzzle.game.events.OnPreLoadAssetsEvent;
import com.github.puzzle.game.events.OnRegisterBlockEvent;
import com.github.puzzle.game.events.OnRegisterZoneGenerators;
import meteordevelopment.orbit.EventHandler;

public class PuzzleBugFix implements ModInitializer {

    @Override
    public void onInit() {
        PuzzleRegistries.EVENT_BUS.subscribe(this);

        Constants.LOGGER.info("Hello From INIT");
    }

    @EventHandler
    public void onEvent(OnRegisterBlockEvent event) {
    }

    @EventHandler
    public void onEvent(OnRegisterZoneGenerators event) {
    }

    @EventHandler
    public void onEvent(OnPreLoadAssetsEvent event) {
    }
}
