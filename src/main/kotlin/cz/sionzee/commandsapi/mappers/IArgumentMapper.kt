/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi.mappers

/**
 * Interface for creating own mapper
 */
interface IArgumentMapper {
    /**
     * Returns the mapper instance
     */
    fun getMapper() : Map<Class<*>, IArgumentConverter<*>>

    /**
     * Returns a value convertor based on class
     */
    fun <V> convertTo(key : Class<V>, arg : String) : V {
        return getMapper()[key]?.from(arg) as V
    }

    /**
     * Called when mapper have to be initialized with values inside
     */
    fun init()
}