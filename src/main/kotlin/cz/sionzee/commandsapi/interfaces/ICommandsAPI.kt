/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi.interfaces

import cz.sionzee.commandsapi.BaseCommand

interface ICommandsAPI {
    fun registerBaseCommand(commandName: String, command: BaseCommand): Boolean
}