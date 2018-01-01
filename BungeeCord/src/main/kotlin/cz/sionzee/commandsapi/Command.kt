/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

@file:Suppress("MemberVisibilityCanPrivate", "unused")

package cz.sionzee.commandsapi

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer

/**
 * This class is a base class for commands. All command classes will inherits from this class.
 */
abstract class Command : BaseCommand() {

    var m_sender : CommandSender? = null
    var m_player : ProxiedPlayer? = null
    private val m_executor : CommandExecutor = CommandExecutor(this)


    override fun executeCommand(sender: Any, args: Array<out String>): Boolean {
        m_sender = sender as CommandSender
        this.m_player = if (isPlayer) sender as ProxiedPlayer else null
        return m_executor.execute(sender, args, this)
    }

    override val isPlayer: Boolean
        get() = m_sender is ProxiedPlayer

    override val isConsole: Boolean
        get() = !isPlayer

    val player: ProxiedPlayer
        get() = if (isPlayer) m_player as ProxiedPlayer else null!!

    val sender: CommandSender
        get() = m_sender as CommandSender

    val console: CommandSender
        get() = m_sender as CommandSender

    override fun hasPermission(permission: String) = sender.hasPermission(permission)

    override fun sendMessage(message: String) = sender.sendMessage(message)

    override fun sendMessage(vararg joiner: String) = sender.sendMessage(joiner.joinToString(" "))
}