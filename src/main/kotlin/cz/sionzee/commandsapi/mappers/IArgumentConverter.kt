/*
 * Copyright © 2017 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi.mappers

interface IArgumentConverter<T> {
    fun from(text: String) : T
}