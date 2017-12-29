package cz.sionzee.commandsapi.interfaces

import sun.management.MethodInfo

interface ICommand {
    fun onCommand(args : Array<out String>) : Boolean
    fun onInvalidParameters(methodInfo: MethodInfo, args : Array<out String>)
}