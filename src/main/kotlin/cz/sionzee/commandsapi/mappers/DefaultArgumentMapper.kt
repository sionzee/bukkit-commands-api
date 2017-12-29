/*
 * Copyright © 2017 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi.mappers

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

open class DefaultArgumentMapper : IArgumentMapper {
    override fun init() {
        mapper.put(Player::class.java, { text : String -> Bukkit.getPlayer(text) } as IArgumentConverter<*>)
    }
    private val mapper = IdentityHashMap<Class<*>, IArgumentConverter<*>>()
    override fun getMapper(): Map<Class<*>, IArgumentConverter<*>> = mapper
}