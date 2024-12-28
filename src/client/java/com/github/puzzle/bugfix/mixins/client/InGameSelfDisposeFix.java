package com.github.puzzle.bugfix.mixins.client;

import com.badlogic.gdx.Gdx;
import finalforeach.cosmicreach.audio.GameMusicManager;
import finalforeach.cosmicreach.gamestates.*;
import finalforeach.cosmicreach.ui.UI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGame.class)
public class InGameSelfDisposeFix extends GameState {

    // Fix UI interception bug
    @Inject(method = "render", at = @At("TAIL"))
    private void e(CallbackInfo ci) {
        if (!UI.renderUI) {
            Gdx.gl.glActiveTexture(33984);
            Gdx.gl.glBindTexture(3553, 0);
        }
    }

    // Added protection against self disposing
    @Inject(method = "switchAwayTo", at = @At("HEAD"), cancellable = true)
    private void switchAwayTo(GameState gameState, CallbackInfo ci) {
        if (!(gameState instanceof PauseMenu)
                && !(gameState instanceof LoadingGame)
                && !(gameState instanceof YouDiedMenu)
                && !(gameState instanceof ChatMenu)
                && !(gameState instanceof InGame)
        ) {
            IN_GAME.unloadWorld();
            this.uiObjects.clear();
            GameMusicManager.removeRequiredTag("in_game");
            GameMusicManager.setSecondsUntilNextPlay(1.0F, 30.0F);
        }
        ci.cancel();
    }


}
