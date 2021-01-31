package io.github.ytg1234.template.testmod

import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@JvmField
val logger: Logger = LogManager.getLogger("TestMod");

object TestMod : ModInitializer {
    override fun onInitialize() {
        logger.info("Test mod onInitialize");
    }
}

