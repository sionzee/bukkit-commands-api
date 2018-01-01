<p align="center"><img alt="CommandsAPI Logo" src="/logo.png?raw=true"/></p>
<p align="center">
<a href="https://jitpack.io/#sionzeecz/bukkit-commands-api"><img alt="Release" src="https://jitpack.io/v/sionzeecz/bukkit-commands-api.svg"/></a>
<a href="https://travis-ci.org/sionzeecz/bukkit-commands-api"><img alt="Build Status" src="https://travis-ci.org/sionzeecz/bukkit-commands-api.svg?branch=master"/></a>
<a href="https://www.codacy.com/app/sionzeecz/bukkit-commands-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=sionzeecz/bukkit-commands-api&amp;utm_campaign=Badge_Grade"><img alt="Codacy Badge" src="https://api.codacy.com/project/badge/Grade/f3b13a74e9a64e46997bde6eb589f3cf"/></a>
<a href="https://jitpack.io/#sionzeecz/bukkit-commands-api"><img alt="Download Badge" src="https://jitpack.io/v/sionzeecz/bukkit-commands-api/month.svg"/></a>
</p>

-----

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

Visit WIKI for [getting-started](https://github.com/sionzeecz/bukkit-commands-api/wiki/Getting-Started).
