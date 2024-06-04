# EventCore

[![download](https://img.shields.io/github/downloads/VertrauterDavid/EventCore/total?style=for-the-badge)](https://github.com/VertrauterDavid/EventCore/releases/latest)
![license](https://img.shields.io/github/license/VertrauterDavid/EventCore?style=for-the-badge)
![stars](https://img.shields.io/github/stars/VertrauterDavid/EventCore?style=for-the-badge)
![forks](https://img.shields.io/github/forks/VertrauterDavid/EventCore?style=for-the-badge)

<hr>

### Installation
1. Download jar from [here](https://github.com/VertrauterDavid/EventCore/releases/latest)
2. Put the jar in your plugins folder
3. Restart your server (not reload)

<hr>

### Future updates
- Possibility to save multiple kits
- Possibility to create teams (for tournaments or similar)
- Possibility to host events directly on a survival server but still get a event kit
- More ingame config options
- Integrated fast world reset system
- Integrated scoreboard system to minimize plugins on the server
- Placeholder Support in Messages

<hr>

<details>
    <summary><h3 style="display: inline;">Commands</h3></summary>

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
| `/spawn`                       | Teleport to the spawn                                   |

</details>

<hr>

<details>
    <summary><h3 style="display: inline;">Permissions</h3></summary>

| Permissions           |                                                                                        |
|-----------------------|:---------------------------------------------------------------------------------------|
| `event.bypass`        | Disables protect while not started (break blocks, place blocks, interact, hit players) |
| `event.command`       | Use /event                                                                             |
| `event.spawn`         | Use /spawn                                                                             |

</details>

<hr>

<details>
    <summary><h3 style="display: inline;">Placeholders</h3></summary>

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

</details>
