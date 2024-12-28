package com.github.puzzle.bugfix;

import finalforeach.cosmicreach.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {

    public static final String MOD_ID = "puzzle-loader-bugfix";
    public static final Identifier MOD_NAME = Identifier.of(MOD_ID, "Puzzle Loader BugFix");
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME.getName());

}
