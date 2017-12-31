/*
 * Copyright © 2017 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi

import cz.sionzee.commandsapi.interfaces.ICommand
import cz.sionzee.commandsapi.mappers.IArgumentMapper
import org.bukkit.block.CommandBlock
import org.bukkit.command.BlockCommandSender
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.lang.reflect.Method

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

    fun arg(index: Int): String = args[index]

    val isPlayer: Boolean
        get() = m_sender is Player

    val isConsole: Boolean
        get() = m_sender is ConsoleCommandSender

    val isCommandBlock: Boolean
        get() = m_sender is BlockCommandSender

    val sender: CommandSender
        get() = m_sender as CommandSender

    val console: CommandSender
        get() = sender

    val player: Player
        get() = m_player as Player

    val commandBlock: CommandBlock
        get() = m_commandBlock as CommandBlock

    internal fun executeCommand(sender: CommandSender, args: Array<out String>): Boolean {
        this.m_sender = sender
        this.m_player = if (isPlayer) sender as Player else null
        this.m_commandBlock = if (isCommandBlock) (sender as BlockCommandSender).block as CommandBlock else null
        return m_executor.execute(sender, args, this)
    }

    fun continueInArg(index: Int) {
        return m_executor.continueInArg(index)
    }

    fun breakExecution(): Boolean {
        return m_executor.breakExecution()
    }

    fun hasPermission(permission: String) = sender.hasPermission(permission)

    fun sendMessage(message: String) = sender.sendMessage(message)

    fun sendMessage(vararg joiner: String) = sender.sendMessage(joiner.joinToString(" "))

    fun isAnyInvalidArgument(): Boolean {
        //TODO: Invalid arguments.size > 0
        return true
    }

    fun requireArgument(argumentIndex: Int, vararg joiner: String): Boolean {
        if (m_args.size > argumentIndex) return false
        //TODO: Add to invalid arguments
        sendMessage(*joiner)
        return true
    }

    internal fun setMapper(mapper: IArgumentMapper): Command {
        return this
    }

    override fun onInvalidParameters(method: Method, args: Array<out String>) {
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
                methodArgs.withIndex().forEach {
                    val enteredValue = args[it.index]
                }
            }
        }

    }

    override fun onInvalidSubcommand(subCommand: String, fullCommand: String) {
        sendMessage("There is no subcommand", subCommand, "in your entered command:", fullCommand)
    }

    open fun getMapper(): IArgumentMapper = m_mapper as IArgumentMapper
}