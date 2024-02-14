# EventCore
Minecraft Event Server System with tons of useful commands and features


### Commands
| Command                        | Action                                                  |
|--------------------------------|:--------------------------------------------------------|
| `/event start`                 | Start the event                                         |
| `/event stop <winner>`         | Stop the event                                          |
| `/event drop`                  | Drop with the commands defined in the config.yml        |
| `/event autoBorder <on / off>` | Toggle AutoBorder                                       |
| `/event settings`              | Settings GUI                                            |
| `/event kickspec`              | Kick all spectators                                     |
| `/event kickall`               | Kick all players (exclude players with `event.command`) |
| `/event clearall`              | Clear all player inventories                            |
| `/kit <player>`                | Give a player the saved kit                             |
| `/kit *`                       | Give all players the saved kit                          |
| `/revive <player>`             | Revive a player                                         |
| `/revive *`                    | Revive all players who are not in gamemode 0            |
| `/announce <message>`          | Announce a message                                      |


### Permissions
| Permissions     |                                                                                        |
|-----------------|:---------------------------------------------------------------------------------------|
| `event.bypass`  | Disables protect while not started (break blocks, place blocks, interact, hit players) |
| `event.command` | Use /event                                                                             |


### Placeholders
| Placeholder          | Description                                       | Example |
|:---------------------|:--------------------------------------------------|:--------|
| `%eventcore_total%`  | Total players online                              | 12      |
| `%eventcore_alive%`  | Total players alive (players in gamemode 0)       | 4       |
| `%eventcore_kills%`  | Kills of the player                               | 6       |
| `%eventcore_deaths%` | Deaths of the player                              | 3       |
| `%eventcore_kd%`     | K/D of the player                                 | 2.00    |
| `%eventcore_totems%` | Totem count of the player                         | 8       |
| `%eventcore_border%` | Current border size of the world the player is on | 30      |
| `%eventcore_ping%`   | Ping of the player                                | 18ms    |
| `%eventcore_tps%`    | Server TPS (via [Spark](https://spark.lucko.me/)) | 20.00   |
