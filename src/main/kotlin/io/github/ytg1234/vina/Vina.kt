package io.github.ytg1234.vina

import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@JvmField
val logger: Logger = LogManager.getLogger("Vina")

object Vina : ModInitializer {
	override fun onInitialize() {
		logger.info("Vina")
	}
}
