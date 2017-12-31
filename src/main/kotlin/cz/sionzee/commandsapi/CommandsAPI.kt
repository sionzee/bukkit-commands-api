/*
 * Copyright © 2017 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi

import cz.sionzee.commandsapi.mappers.IArgumentMapper
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.plugin.SimplePluginManager
import java.util.*

object CommandsAPI {

    private val registeredCommands : IdentityHashMap<Class<Command>, Any> = IdentityHashMap()

    /**
     * Registers @command in Bukkit interface
     */
    fun registerCommand(commandName: String, command: Command) : Boolean {
        return registerBaseCommand(commandName, command)
    }

    /**
     * Registers @command in Bukkit interface with argument mapper
     */
    fun registerCommand(commandName: String, command: Command, mapper: IArgumentMapper) : Boolean {
        return registerBaseCommand(commandName, command.setMapper(mapper))
    }

    /**
     * Creates a instance of default constructor for @command and registers it in Bukkit interface
     */
    fun <CMD : Command> registerCommand(commandName: String, command: Class<CMD>) : Boolean {
        return registerBaseCommand(commandName, command.newInstance())
    }

    /**
     * Creates a instance of default constructor for @command and registers it in Bukkit interface with argument mapper
     */
    fun <CMD : Command> registerCommand(commandName: String, command: Class<CMD>, mapper: IArgumentMapper) : Boolean {
        return registerBaseCommand(commandName, command.newInstance().setMapper(mapper))
    }

    private fun registerBaseCommand(commandName: String, command : Command) : Boolean {
        val commandMap : CommandMap? = (Bukkit.getPluginManager() as SimplePluginManager).getValueOfField("commandMap") as CommandMap?
        if(commandMap == null || commandMap.getCommand(commandName) != null) return false

        commandMap.register(commandName, object : org.bukkit.command.Command(commandName) {
            override fun execute(sender: CommandSender?, commandLabel: String?, args: Array<out String>?): Boolean {
                return command.executeCommand(sender as CommandSender, args as Array<out String>)
            }
        })

        registeredCommands.put(command.javaClass, command)
        return true
    }
}
