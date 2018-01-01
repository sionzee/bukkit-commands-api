/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi

import org.bukkit.plugin.java.JavaPlugin

/**
 * The CommandsAPI for other plugins
 */
class CommandsAPIPlugin : JavaPlugin() {
    override fun onEnable() = BukkitCommandsAPI.init(this)
}