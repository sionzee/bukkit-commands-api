/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi

import cz.sionzee.commandsapi.interfaces.ICommandsAPI
import cz.sionzee.commandsapi.mappers.IArgumentMapper
import java.util.*

/**
 * CommandsAPI Instance for other plugins
 */
object CommandsAPI {

    private var m_commandExecutor : ICommandsAPI? = null
    private val registeredCommands : IdentityHashMap<Class<BaseCommand>, Any> = IdentityHashMap()

    /**
     * Registers @command in Bukkit interface
     */
    fun registerCommand(commandName: String, command: BaseCommand) : Boolean {
        return registerBaseCommand(commandName, command)
    }

    /**
     * Registers @command in Bukkit interface with argument mapper
     */
    fun registerCommand(commandName: String, command: BaseCommand, mapper: IArgumentMapper) : Boolean {
        return registerBaseCommand(commandName, command.setMapper(mapper))
    }

    /**
     * Creates a instance of default constructor for @command and registers it in Bukkit interface
     */
    fun <CMD : BaseCommand> registerCommand(commandName: String, command: Class<CMD>) : Boolean {
        return registerBaseCommand(commandName, command.newInstance())
    }

    /**
     * Creates a instance of default constructor for @command and registers it in Bukkit interface with argument mapper
     */
    fun <CMD : BaseCommand> registerCommand(commandName: String, command: Class<CMD>, mapper: IArgumentMapper) : Boolean {
        return registerBaseCommand(commandName, command.newInstance().setMapper(mapper))
    }

    private fun registerBaseCommand(commandName: String, command: BaseCommand): Boolean {
        if(m_commandExecutor?.registerBaseCommand(commandName, command)!!) {
            CommandsAPI.registeredCommands.put(command.javaClass, command)
        }
        return false
    }

    /**
     * Sets the api from Bungee\Bukkit plugin
     */
    fun setAPI(commandExecutor : ICommandsAPI) {
        m_commandExecutor = commandExecutor
    }
}
