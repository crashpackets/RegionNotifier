![RegionNotifier](https://cdn.discordapp.com/attachments/1138361189546594304/1336048071569838180/RegionNotifier_Banner.png?ex=67a2633a&is=67a111ba&hm=a719df22d000fe418479c047ec8a3b881fc7b9597a695004b73168a79a6e7b68&)
# RegionNotifier 🏷️

![GitHub](https://img.shields.io/badge/License-MIT-green)

RegionNotifier is a **lightweight and customizable** Minecraft plugin that notifies players when they enter or exit specific world guard regions, enhancing gameplay awareness and interaction. 🚀

---

## Features ✨

- **Region Notifications**: Alerts players when entering or exiting defined regions. 📌
- **Customizable Messages**: Modify titles, subtitles, and action bar messages via `config.yml`. 🎨
- **Lightweight & Efficient**: Designed for minimal performance impact. ⚡
- **MiniMessage Support**: Use [MiniMessage](https://docs.advntr.dev/minimessage.html) for rich text formatting. 📝
- **Permissions Support**: Grant specific access to commands and notifications. 🔑

---

## Commands 🛠️

| Command              | Description                         | Permission               |
|----------------------|-----------------------------------|--------------------------|
| `/regionnotifier reload` | Reloads the plugin configuration. | `regionnotifier.reload` |

---

## Permissions 🔐

| Permission               | Description                            | Default |
|--------------------------|----------------------------------------|---------|
| `regionnotifier.reload`  | Allows reloading the configuration.    | `op`    |
| `regionnotifier.notify`  | Allows receiving region notifications. | `true`  |

---

## Configuration ⚙️

Modify `config.yml` to set up your regions and messages.

```yaml
regions:
  spawn:
    enter:
      title: "<gradient:green:blue>Welcome to Spawn!</gradient>"
      subtitle: "<gray>Enjoy your stay!</gray>"
      actionbar: "<gold>You entered the spawn area!</gold>"
      enabled: true
    exit:
      title: "<gradient:red:yellow>Leaving Spawn!</gradient>"
      subtitle: "<gray>Come back soon!</gray>"
      actionbar: "<gold>You left the spawn area!</gold>"
      enabled: true
```

## Messages 📩

```yaml
messages:
  no-permission: "<red>You don't have permission to use this command!</red>"
  reload-success: "<green>Configuration reloaded successfully!</green>"
```

---

## Installation 📥

1. Download the latest `RegionNotifier.jar` from the [Releases](https://github.com/your-repo/releases) page.
2. Place the `.jar` file in your server's `plugins` folder.
3. Restart your server.
4. Customize the `config.yml` as needed.

---

## License 📄
This project is licensed under the MIT License. See the LICENSE file for details.

## Support 💬
For issues or suggestions, open a ticket on GitHub or contact us via Discord.

---

# Enjoy using RegionNotifier! 🎉
