package com.github.puzzle.bugfix;

import com.github.puzzle.core.loader.launch.provider.mod.entrypoint.impls.ClientModInitializer;

public class PuzzleBugFixClient implements ClientModInitializer {

    @Override
    public void onInit() {
        Constants.LOGGER.info("Hello From INIT");
    }

}
