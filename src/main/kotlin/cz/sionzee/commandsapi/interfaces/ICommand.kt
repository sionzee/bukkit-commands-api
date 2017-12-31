package cz.sionzee.commandsapi.interfaces

import java.lang.reflect.Method

/**
 * Interface for sub commands of type class
 */
interface ICommand {
    /**
     * Called when subcommand is invoked
     */
    fun onCommand(args : Array<out String>) : Boolean

    /**
     * Called when subcommand has got invalid parameters
     */
    fun onInvalidParameters(method: Method, args : Array<out String>)

    /**
     * Called when subcommand does not exists.
     */
    fun onInvalidSubcommand(subCommand : String, fullCommand : String)
}