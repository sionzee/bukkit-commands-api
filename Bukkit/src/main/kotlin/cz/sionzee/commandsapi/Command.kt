/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

@file:Suppress("PrivatePropertyName", "unused")

package cz.sionzee.commandsapi

import org.bukkit.block.CommandBlock
import org.bukkit.command.BlockCommandSender
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

/**
 * This class is a base class for commands. All command classes will inherits from this class.
 */
abstract class Command : BaseCommand() {

    private var m_sender: CommandSender? = null
    private var m_player: Player? = null
    private var m_commandBlock: CommandBlock? = null
    private val m_executor : CommandExecutor = CommandExecutor(this)

    /**
     * Returns true if sender is player
     */
    override val isPlayer: Boolean
        get() = m_sender is Player

    /**
     * Returns true if sender is console
     */
    override val isConsole: Boolean
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

    override fun executeCommand(sender: Any, args: Array<out String>): Boolean {
        this.m_sender = sender as CommandSender
        this.m_player = if (isPlayer) sender as Player else null
        this.m_commandBlock = if (isCommandBlock) (sender as BlockCommandSender).block as CommandBlock else null
        return m_executor.execute(sender, args, this)
    }


    /**
     * Returns true if sender has a permission
     */
    override fun hasPermission(permission: String) : Boolean = sender.hasPermission(permission)

    /**
     * Sends message to the sender
     */
    override fun sendMessage(message: String) = sender.sendMessage(message)

    /**
     * Join arguments by space separator and sends message to the sender
     */
    override fun sendMessage(vararg joiner: String) = sender.sendMessage(joiner.joinToString(" "))
}