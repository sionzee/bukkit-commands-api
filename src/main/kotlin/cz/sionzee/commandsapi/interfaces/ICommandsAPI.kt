/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi.interfaces

import cz.sionzee.commandsapi.BaseCommand

/**
 * Interface for Plugin Command API
 */
interface ICommandsAPI {
    /**
     * Base register for commands
     */
    fun registerBaseCommand(commandName: String, command: BaseCommand): Boolean
}