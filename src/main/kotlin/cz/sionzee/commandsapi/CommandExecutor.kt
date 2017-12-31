/*
 * Copyright © 2017 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi

import cz.sionzee.commandsapi.annotations.SubCommand
import cz.sionzee.commandsapi.interfaces.ICommand
import org.bukkit.command.CommandSender
import java.lang.reflect.Method

/**
 * CommandExecutor allows to execute Command
 */
class CommandExecutor(private val m_command: Command) {

    private var m_executing : Boolean = true
    private var m_continueInArg : Int = 0

    /**
     * Execute the command
     */
    fun execute(sender: CommandSender, args: Array<out String>, command: ICommand): Boolean {
        m_continueInArg = 0
        m_executing = true
        command.onCommand(args)
        if(!m_executing) return false

        if(args.isNotEmpty()) {
            val method : Method? = command.javaClass.declaredMethods.getByName(args[m_continueInArg])
            if(method == null) {
                val clazz : Class<*>? = command.javaClass.declaredClasses.getByName(args[m_continueInArg])//TODO: Method & Class not found.

                if(clazz == null) {
                    command.onInvalidSubcommand(args[m_continueInArg], args.joinToString(" "))
                    return true
                }

//                println(clazz.constructors.joinToString { it.name + "(" + it.parameters.joinToString { it.type.name ?: "null" } + ")" })
                if(clazz.constructors.isEmpty()) {
                    println("[CommandsAPI][TODO] Missing constructor for class ${clazz.name} (continues in executing");
                }
                val newInstance = clazz.constructors[0].newInstance(command) as ICommand
                execute(sender, copyOfRange(++m_continueInArg, args.indexSize, args), newInstance)
            } else {
                //TODO: method parse parameters and map them
                // m_continueInArg[0] = methodName
                //                [1] = parameter
                val methodArgs : Array<out String> = copyOfRange(++m_continueInArg, args.indexSize, args) // Cannot copy empty
                if(method.parameterCount != methodArgs.size) {
                    //TODO: Missing parameters / Too many parameters
                    //TODO: Continue in search or throw error message or?
                    println("Method requires " + method.parameterCount + " parameters and there are " + methodArgs.size + " parameters.");
                    println("[CommandsAPI][TODO] Method Parameter Count != Entered Parameter Count (stop executing)")
                    command.onInvalidParameters(method, methodArgs)
                    return false
                }

                val methodResultArgs = arrayOfNulls<Any>(method.parameterCount)

                //TODO: This method is final / no anyother execution after this
                method.parameters.forEachIndexed { index, parameter ->
                    run {
                        val enteredArgument = methodArgs[index]
                        if (parameter.type.isPrimitive) {
                            if (!enteredArgument.canBeConvertedToPrimitive(parameter.type)) {
                                //TODO: Invalid format of primitive type
                                println("[CommandsAPI][TODO] Invalid format of primitive type (continues in foreach)")
                            }
//                            println("[CommandsAPI] Method requires a primitive type.")
                            val value = enteredArgument.convertToPrimitive(parameter.type)
                            methodResultArgs[index] = value
                        } else {
//                            println("[CommandsAPI] Method requires a non-primitive type. (${parameter.type.name}) is enter paremeter string? ${String::class.java.isInstance(enteredArgument)}")
                            if(String::class.java.isInstance(enteredArgument)) {
                                methodResultArgs[index] = enteredArgument
                            } else {
                                if(m_command.getMapper().getMapper().containsKey(parameter.type))
                                    methodResultArgs[index] = m_command.getMapper().convertTo(parameter.type, enteredArgument)
                                else {
                                    //TODO: Missing mapper for Object!
                                    println("[CommandsAPI][TODO] Missing mapper for object (continues in foreach)")
                                }
                            }
                        }
                    }
                }
                println("[CommandsAPI] Invoking method " + method.name + "([" + method.parameters.joinToString { it.type.name } + "]) with args (" + methodResultArgs.joinToString { "[${it?.javaClass?.name}] ${it.toString()}" } + ")")
                if(methodResultArgs.isNotEmpty()) method.invoke(command, *methodResultArgs)
                else method.invoke(command)
            }

        }

        return true
    }

    companion object {
        /**
         * Copies values from @startIndex to @endIndex in array and in in illegal state it returns an empty array.
         */
        inline fun <reified T> copyOfRange(startIndex : Int, endIndex : Int, array: Array<out T>) : Array<out T> {
//        println("CopyOfRange from $startIndex to $endIndex in: ${array.joinToString()} RANGE IS ${endIndex - startIndex}")
            if(endIndex > array.indexSize) throw IllegalArgumentException("EndIndex > IndexSize")
            if(startIndex < 0) throw IllegalArgumentException("StartIndex < 0")
            if(startIndex > endIndex) return arrayOf()
            val size = endIndex - startIndex + 1
            val newArray : Array<out T?> = arrayOfNulls<T>(size)
            System.arraycopy(array, startIndex, newArray, 0, size)
            @Suppress("UNCHECKED_CAST") // IDE doesn't know about System.arraycopy
            return newArray as Array<out T>
        }
    }


    /**
     * Break execution of code
     */
    fun breakExecution(): Boolean {
        m_executing = false
        return true
    }

    /**
     * Skip execution to index of current scope
     */
    fun continueInArg(index: Int) {
        m_continueInArg = index
    }

    /**
     * Returns a list of available sub-commands
     */
    fun getAvailableCommands(): List<String> {
        //TODO: Add available commands
        return listOf()
    }
}

private fun Array<out Method>.getByName(methodName: String): Method? {
    this.iterator().forEach {
        if(it.getAnnotationsByType(SubCommand::class.java).isNotEmpty() && it.name.equals(methodName, true))
            return it
    }
    return null
}

private fun Array<out Class<*>>.getByName(className: String): Class<*>? {
    this.iterator().forEach {
        if(it.getAnnotationsByType(SubCommand::class.java).isNotEmpty() && (Command::class.java.isAssignableFrom(it) || ICommand::class.java.isAssignableFrom(it)) && it.simpleName.equals(className, true))
            return it
    }
    return null
}
