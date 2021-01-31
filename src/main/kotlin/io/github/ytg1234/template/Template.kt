package io.github.ytg1234.template

import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@JvmField
val logger: Logger = LogManager.getLogger("Template")

object Template : ModInitializer {
	override fun onInitialize() {
		logger.info("Template")
	}
}
