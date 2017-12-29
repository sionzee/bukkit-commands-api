/*
 * Copyright © 2017 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi

import java.lang.reflect.Field
import javax.lang.model.element.Modifier

fun Field.removeFinalModifier() : Boolean {
    val modifiersField = Field::class.java.getDeclaredField("modifiers")
    modifiersField.isAccessible = true
    val isFinal = this.modifiers and Modifier.FINAL.ordinal == Modifier.FINAL.ordinal
    modifiersField.setInt(this, (this.modifiers and Modifier.FINAL.ordinal.inv()))
    return isFinal
}

fun Field.restoreFinalModifier() {
    val modifiersField = Field::class.java.getDeclaredField("modifiers")
    modifiersField.isAccessible = true
    modifiersField.setInt(this, (this.modifiers and Modifier.FINAL.ordinal))
}

fun Any.getValueOfField(fieldName: String): Any? {
    var value : Any? = null
    val field : Field? = this.javaClass.getDeclaredField(fieldName)
    if(field != null) {
        field.isAccessible = true
        val restore = field.removeFinalModifier()
        value = field.get(this)
        if(restore) field.restoreFinalModifier()
        field.isAccessible = false
    }
    return value
}

val Array<out Any>.indexSize
    get() = size - 1

fun String.convertToPrimitive(type: Class<*>): Any {
    return when(type.name) {
        "double" -> toDouble()
        "boolean" -> return if(equals("true", true)) true else if (equals("false", true)) false else if(equals("1")) true else if(equals("0")) false else false
        "int" -> toInt()
        "byte" -> toByte()
        "short" -> toShort()
        "long" -> toLong()
        "float" -> toFloat()
        "chat" -> get(0)
        else -> {
            return false
        }
    }
}

fun String.canBeConvertedToPrimitive(type : Class<*>) : Boolean {
    return when(type.name) {
        "double" -> toDoubleOrNull() != null
        "boolean" -> equals("true", true) || equals("false", true) || equals("1") || equals("0")
        "int" -> toIntOrNull() != null
        "byte" -> toByteOrNull() != null
        "short" -> toShortOrNull() != null
        "long" -> toLongOrNull() != null
        "float" -> toFloatOrNull() != null
        "chat" -> length > 0
        else -> {
            return false
        }
    }
}