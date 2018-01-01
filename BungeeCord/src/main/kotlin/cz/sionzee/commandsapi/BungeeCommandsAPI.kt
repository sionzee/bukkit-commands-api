/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi

import cz.sionzee.commandsapi.interfaces.ICommandsAPI
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Plugin

object BungeeCommandsAPI : ICommandsAPI {

    private var temp : Plugin? = null
    private val m_plugin : Plugin
        get() = temp as Plugin

    /**
     * Inits the BungeeCommandsAPI
     */
    fun init(plugin : Plugin) {
        temp = plugin
        CommandsAPI.setAPI(this)
    }

    /**
     * Registers command with CommandsAPI
     */
    override fun registerBaseCommand(commandName: String, command : BaseCommand) : Boolean {
        m_plugin.proxy.pluginManager.registerCommand(m_plugin, object : net.md_5.bungee.api.plugin.Command(commandName) {
            override fun execute(sender: CommandSender?, args: Array<out String>?) {
                command.executeCommand(sender as CommandSender, args as Array<out String>)
            }
        })
        return true
    }
}