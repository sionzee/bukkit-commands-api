/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi

import cz.sionzee.commandsapi.interfaces.ICommandsAPI
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.plugin.SimplePluginManager
import org.bukkit.plugin.java.JavaPlugin

object BukkitCommandsAPI : ICommandsAPI {

    private var temp : JavaPlugin? = null
    private val m_plugin : JavaPlugin
        get() = temp as JavaPlugin


    fun init(plugin : JavaPlugin) {
        temp = plugin
        CommandsAPI.setAPI(this)
    }


    override fun registerBaseCommand(commandName: String, command : BaseCommand) : Boolean {
        val commandMap : CommandMap? = (Bukkit.getPluginManager() as SimplePluginManager).getValueOfField("commandMap") as CommandMap?
        if(commandMap == null || commandMap.getCommand(commandName) != null) return false

        commandMap.register(commandName, object : org.bukkit.command.Command(commandName) {
            override fun execute(sender: CommandSender?, commandLabel: String?, args: Array<out String>?): Boolean {
                return command.executeCommand(sender as CommandSender, args as Array<out String>)
            }
        })
        return true
    }

}