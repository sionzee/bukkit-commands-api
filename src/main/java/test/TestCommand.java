package test;/*
 * Copyright © 2017 by Jan Trzicky, alias sionzee
 * All rights reserved.
 */

import cz.sionzee.commandsapi.Command;
import cz.sionzee.commandsapi.annotations.SubCommand;
import cz.sionzee.commandsapi.interfaces.ICommand;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.logging.Level;

@SuppressWarnings("MethodMayBeStatic")
public class TestCommand extends Command {

    public static void log(String message) {
        Bukkit.getLogger().log(Level.CONFIG, "UNIT:" + message);
    }

    @Override
    public boolean onCommand(@NotNull String[] args) {
        log("onCommand Executed");
        return false;
    }

    @SubCommand
    public void test() {
        log("onCommand Test()");
    }

    @SubCommand
    public void test2(String a) {
        log("onCommand Test2(" + a + ')');
    }

    @SubCommand
    public class Test3 implements ICommand {
        @Override
        public boolean onCommand(@NotNull String[] args) {
            log("Test3 Command executed, yaay.");
            return false;
        }

        @Override
        public void onInvalidParameters(@NotNull Method method, @NotNull String[] args) {
            TestCommand.super.onInvalidParameters(method, args);
        }

        @Override
        public void onInvalidSubcommand(@NotNull String subCommand, @NotNull String fullCommand) {
            TestCommand.super.onInvalidSubcommand(subCommand, fullCommand);
        }

        @SubCommand
        public class Test implements ICommand {
            @Override
            public boolean onCommand(@NotNull String[] args) {
                log("Test Command executed, yaay.");
                return false;
            }

            @Override
            public void onInvalidParameters(@NotNull Method method, @NotNull String[] args) {
                TestCommand.super.onInvalidParameters(method, args);
            }

            @Override
            public void onInvalidSubcommand(@NotNull String subCommand, @NotNull String fullCommand) {
                TestCommand.super.onInvalidSubcommand(subCommand, fullCommand);
            }
        }
    }

}
