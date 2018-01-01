/*
 * Copyright © 2018 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

package cz.sionzee.commandsapi.annotations

/**
 * SubCommand annotations which indicated subcommand function and class
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class SubCommand(val ignoreOvergrowthArgs : Boolean = false, val permission : String = "", val childPermission : String = "")