/*
 * Copyright © 2017 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

@file:Suppress("unused", "MemberVisibilityCanPrivate", "PrivatePropertyName")

package cz.sionzee.commandsapi

import cz.sionzee.commandsapi.interfaces.ICommand
import cz.sionzee.commandsapi.mappers.IArgumentMapper
import org.bukkit.block.CommandBlock
import org.bukkit.command.BlockCommandSender
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.lang.reflect.Method

/**
 * This class is a base class for commands. All command classes will inherits from this class.
 */
abstract class Command : ICommand {

    private var m_sender: CommandSender? = null
    private var m_player: Player? = null
    private var m_commandBlock: CommandBlock? = null
    private var m_args: Array<out String> = emptyArray()
    private var m_mapper: IArgumentMapper? = null
    private val m_executor: CommandExecutor = CommandExecutor(this)

    val args: Array<out String>
        @JvmName("args")
        get() = m_args

    /**
     * Returns argument by index based on current scope
     */
    fun arg(index: Int): String = args[index]

    /**
     * Returns true if sender is player
     */
    val isPlayer: Boolean
        get() = m_sender is Player

    /**
     * Returns true if sender is console
     */
    val isConsole: Boolean
        get() = m_sender is ConsoleCommandSender

    /**
     * Returns true if sender is command block
     */
    val isCommandBlock: Boolean
        get() = m_sender is BlockCommandSender

    /**
     * Returns sender
     */
    val sender: CommandSender
        get() = m_sender as CommandSender

    /**
     * Returns sender as Console (its same as sender)
     */
    val console: CommandSender
        get() = sender

    /**
     * Returns sender as Player
     */
    val player: Player
        get() = if (isPlayer) m_player as Player else null!!

    /**
     * Returns sender as CommandBlock
     */
    val commandBlock: CommandBlock
        get() = if (isCommandBlock) m_commandBlock as CommandBlock else null!!

    internal fun executeCommand(sender: CommandSender, args: Array<out String>): Boolean {
        this.m_sender = sender
        this.m_player = if (isPlayer) sender as Player else null
        this.m_commandBlock = if (isCommandBlock) (sender as BlockCommandSender).block as CommandBlock else null
        return m_executor.execute(sender, args, this)
    }

    /**
     * Skip code execution to specific index
     */
    fun continueInArg(index: Int) {
        return m_executor.continueInArg(index)
    }

    /**
     * Break execution of code, api will not look for another methods/classes
     */
    fun breakExecution(): Boolean {
        return m_executor.breakExecution()
    }

    /**
     * Returns true if sender has a permission
     */
    fun hasPermission(permission: String) = sender.hasPermission(permission)

    /**
     * Sends message to the sender
     */
    fun sendMessage(message: String) = sender.sendMessage(message)

    /**
     * Join arguments by space separator and sends message to the sender
     */
    fun sendMessage(vararg joiner: String) = sender.sendMessage(joiner.joinToString(" "))

    /**
     * Returns all available commands
     */
    fun getAvailableCommands() : List<String> {
        return m_executor.getAvailableCommands()
    }

    /**
     * Returns true if anyone of `requireArgument` returns true
     */
    fun isAnyInvalidArgument(): Boolean {
        //TODO: Invalid arguments.size > 0
        return true
    }

    /**
     * Sends a player message about missing argument
     */
    fun requireArgument(argumentIndex: Int, vararg joiner: String): Boolean {
        if (m_args.size > argumentIndex) return false
        //TODO: Add to invalid arguments
        sendMessage(*joiner)
        return true
    }

    /**
     * Sets overridable mapper
     */
    internal fun setMapper(mapper: IArgumentMapper): Command {
        this.m_mapper = mapper
        return this
    }

    override fun onInvalidParameters(method: Method, args: Array<out String>) {
        //TODO: Finish onInvalidParameters

        val methodArgs = method.parameters.map { it.type }.toTypedArray()

        when {
            args.size > methodArgs.size -> {
                val additionalArgs = CommandExecutor.copyOfRange(methodArgs.size, args.indexSize, args)
                sendMessage("Invalid arguments [", additionalArgs.joinToString { it }, "]")
            }
            args.size < methodArgs.size -> {
                val missingArgs = CommandExecutor.copyOfRange(methodArgs.size - args.size - 1, methodArgs.size - 1, methodArgs)
                sendMessage("Missing arguments [", missingArgs.withIndex().joinToString { method.parameters[it.index].name }, "]")
            }
            else -> {
                //TODO: When is entered invalid parameter
                methodArgs.withIndex().forEach {
                    val enteredValue = args[it.index]
                }
            }
        }

    }

    override fun onInvalidSubcommand(subCommand: String, fullCommand: String) {
        //TODO: Finish onInvalidSubcommand
        sendMessage("There is no subcommand", subCommand, "in your entered command:", fullCommand)
    }

    /**
     * Returns overridable mapper
     */
    open fun getMapper(): IArgumentMapper = m_mapper as IArgumentMapper
}