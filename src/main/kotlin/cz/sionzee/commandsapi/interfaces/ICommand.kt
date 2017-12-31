package cz.sionzee.commandsapi.interfaces

import java.lang.reflect.Method

interface ICommand {
    fun onCommand(args : Array<out String>) : Boolean
    fun onInvalidParameters(method: Method, args : Array<out String>)
    fun onInvalidSubcommand(subCommand : String, fullCommand : String)
}