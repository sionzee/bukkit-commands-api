/*
 * Copyright © 2017 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi.mappers

/**
 * Convertor from key to value
 */
interface IArgumentConverter<T> {
    /**
     * Converts string to T
     */
    fun from(text: String) : T
}