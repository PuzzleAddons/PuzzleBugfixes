package com.github.puzzle.bugfix.mixins.client;

import com.badlogic.gdx.Gdx;
import com.github.puzzle.core.loader.util.AnsiColours;
import finalforeach.cosmicreach.ClientZoneLoader;
import finalforeach.cosmicreach.Threads;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.UIObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameState.class)
public class GameStateSelfSwitch {

    @Shadow public static GameState currentGameState;
    @Unique private static final Logger puzzleLoader$LOGGER = LoggerFactory.getLogger("CosmicReach | GameState");

    // Added protection against switching to already loaded gamestate
    @Inject(method = "switchToGameState", at = @At("HEAD"), cancellable = true)
    private static void switchGameState(GameState gameState, CallbackInfo ci) {
        if (currentGameState.getClass().equals(gameState.getClass())) {
            ci.cancel();
            return;
        }

        Threads.runOnMainThread(() -> {
            if(currentGameState == null) {
                puzzleLoader$LOGGER.info("Switched to GameState: {}{}{}", AnsiColours.BLUE, gameState.getClass().getSimpleName(), AnsiColours.WHITE);
            } else {
                puzzleLoader$LOGGER.info("Switched from {}{}{} to {}{}{}", AnsiColours.BLUE, currentGameState.getClass().getSimpleName(), AnsiColours.WHITE, AnsiColours.BLUE, gameState.getClass().getSimpleName(), AnsiColours.WHITE);
            }
            if (currentGameState != null) {
                for(int i = 0; i < currentGameState.uiObjects.size; ++i) {
                    UIObject u = currentGameState.uiObjects.get(i);
                    u.deactivate();
                }

                currentGameState.switchAwayTo(gameState);
            }

            if (!gameState.isCreated()) {
                gameState.create();
            }

            currentGameState.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            currentGameState = gameState;
            currentGameState.firstFrame = true;
            currentGameState.onSwitchTo();
            if (ClientZoneLoader.currentInstance != null) {
                ClientZoneLoader.currentInstance.gameStateChanged = true;
            }

        });
        ci.cancel();
    }

}
