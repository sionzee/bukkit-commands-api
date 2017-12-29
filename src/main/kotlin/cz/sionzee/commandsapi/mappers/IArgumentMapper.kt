/*
 * Copyright © 2017 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi.mappers

interface IArgumentMapper {
    fun getMapper() : Map<Class<*>, IArgumentConverter<*>>
    fun <V> convertTo(key : Class<V>, arg : String) : V {
        return getMapper()[key]?.from(arg) as V
    }
    fun init()
}