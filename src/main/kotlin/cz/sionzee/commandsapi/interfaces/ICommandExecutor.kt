/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi.interfaces

interface ICommandExecutor {
    fun execute(sender: Any, args: Array<out String>, command: ICommand): Boolean
}