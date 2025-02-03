package wiki.thesarthakdev.regionnotifier.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MessageUtils {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static void sendRegionMessages(Audience audience, String title, String subtitle, String actionbar) {
        if (title != null && !title.isEmpty()) {
            Component titleComponent = miniMessage.deserialize(title);
            Component subtitleComponent = subtitle != null ? miniMessage.deserialize(subtitle) : Component.empty();
            audience.showTitle(net.kyori.adventure.title.Title.title(titleComponent, subtitleComponent));
        }

        if (actionbar != null && !actionbar.isEmpty()) {
            audience.sendActionBar(miniMessage.deserialize(actionbar));
        }
    }
}