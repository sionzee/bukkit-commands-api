/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi

import net.md_5.bungee.api.plugin.Plugin

/**
 * The BungeeCordPlugin
 */
class CommandsAPIPlugin : Plugin() {
    override fun onEnable() = BungeeCommandsAPI.init(this)
}