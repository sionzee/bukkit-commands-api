<p align="center"><img alt="CommandsAPI Logo" src="/logo.png?raw=true"/></p>

-----
[![Release](https://jitpack.io/v/sionzeecz/bukkit-commands-api.svg)](https://jitpack.io/#sionzeecz/bukkit-commands-api)
[![Build Status](https://travis-ci.org/sionzeecz/bukkit-commands-api.svg?branch=master)](https://travis-ci.org/sionzeecz/bukkit-commands-api)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f3b13a74e9a64e46997bde6eb589f3cf)](https://www.codacy.com/app/sionzeecz/bukkit-commands-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=sionzeecz/bukkit-commands-api&amp;utm_campaign=Badge_Grade)
[![Download Badge](https://jitpack.io/v/sionzeecz/bukkit-commands-api/total.svg)](https://jitpack.io/#sionzeecz/bukkit-commands-api)

Commands API for Bukkit plugins. Also for BungeeCord.
## What is CommandsAPI?
Almost every plugin uses commands for managing features and itself. And there are many states what needs to be handled by developer.
Example: When a player enter too many arguments or when player miss some arguments.
Also if player is entered a correct argument (number, simple text, etc...)
Otherwise when you created too many plugins, you don't want to every time register it in plugin.yml or look if command handles all states.
This API make everything easier.

## How to include CommandsAPI (Gradle, Maven, SBT, Leiningen)
Include this API via [JitPack repository](https://jitpack.io/#sionzeecz/bukkit-commands-api)


## Features
* Not needed to register command in plugin.yml
* Handling states when is argument overgrowth or missing
* Mapping string to object (eg. string to Player)
* Handling incorrect type of entered argument (number, string, pattern)
* Supports BungeeCord

### Comparision
Using Bukkit API
```java
class BukkitApiCommand extends JavaPlugin implements CommandExecutor {
    public void onEnable() {
        getCommand("test").setCommandExecutor(this);
    }
     
     public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!cmd.getName().equalsIgnoreCase("test")) return false;
        if(args.length > 0) {
            String arg0 = args[0];
            if(arg0.equalsIgnoreCase("add")) {
                if(args.length < 2) {
                    sender.sendMessage("Please enter a player name.");
                    return true;
                }
                String arg1 = args[1];
                Player player = Bukkit.getPlayer(arg1);
                if(player == null) {
                    sender.sendMessage("Player " + arg1 + " is not online");
                    return true;
                }
                player.sendMessage("Hi!");
            } else {
                sender.sendMessage("Please enter a valid action. [add, del, edit]");
            }
        } else sender.sendMessage("Please enter an action.");
      
        return true; 
     }   
}
```
Using CommandsAPI
```java
class CommandApiCommand extends JavaPlugin {
    public void onEnable() {
        CommandsAPI.INSTANCE.registerCommand("test", TestCommand.class);
    }
}

class TestCommand extends cz.sionzee.commandsapi.Command {
     public boolean onCommand(@NotNull String[] args) {
         if(args.length < 1) {
             sendMessage("Please enter an action");
         }
     }
     
     @cz.sionzee.commandsapi.SubCommand
     public void add(Player player) {
         if(player == null) {
             sendMessage("Player", arg(0), "is not online.");
             return;
         }
         
         player.sendMessage("Hi!");
     }
}
```

Maybe you ask where is handling when parameter <player> is not entered. Like `/test add`.
You can override methods `onInvalidParameters` and `onInvalidSubcommand` if you want to handle incorrect state, but there is a default implementation for these methods.
You can find it in ``src/main/kotlin/cz/sionzee/commandsapi/Command.kt`` It is in methods `onInvalidParameters` and `onInvalidSubcommand`. 
