/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi.interfaces

/**
 * Interface for Plugin Executor
 */
interface ICommandExecutor {
    /**
     * Executor of CommandsAPI
     */
    fun execute(sender: Any, args: Array<out String>, command: ICommand): Boolean
}