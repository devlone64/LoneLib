package dev.lone64.LoneLib.common.textarea.other;

import dev.lone64.LoneLib.common.textarea.BukkitTextarea;
import dev.lone64.LoneLib.common.util.nms.NmsVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static dev.lone64.LoneLib.common.util.string.ColorUtil.format;

public class TextareaManager {
    private static final Map<UUID, BukkitTextarea> RAON_TEXT_FIELD_MAP = new HashMap<>();

    public static void setKey(Player player, BukkitTextarea textarea) {
        RAON_TEXT_FIELD_MAP.put(player.getUniqueId(), textarea);
        if (textarea.getInitHandler() != null)
            textarea.getInitHandler().onHandle(player);
        var title = textarea.getTitle() != null ? textarea.getTitle() : "";
        var subtitle = textarea.getSubtitle() != null ? textarea.getSubtitle() : "";
        player.sendTitle(format(title), format(subtitle), textarea.getFadeIn(), 99999999, textarea.getFadeOut());
        if (textarea.isSlowness()) {
            if (NmsVersion.getCurrentVersion().isNewPotion())
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 99999999, 254, false, false));
            else player.addPotionEffect(new PotionEffect(PotionEffectType.getByName("SLOW"), 99999999, 254, false, false));
        }
        if (textarea.isBlindness()) player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999999, 254, false, false));
        if (textarea.isDelay()) Bukkit.getScheduler().runTaskLater(textarea.getPlugin(), () -> {
            if (!isKey(player)) return;
            var object = findKey(player);
            if (object.getCancelHandler() != null) {
                object.getCancelHandler().onHandle(player);
            }
            removeKey(player);
        }, 20 * 30);
    }

    public static void removeKey(Player player) {
        RAON_TEXT_FIELD_MAP.remove(player.getUniqueId());
        player.sendTitle("", "", 0, 0, 0);
        if (NmsVersion.getCurrentVersion().isNewPotion()) {
            if (player.hasPotionEffect(PotionEffectType.SLOWNESS))
                player.removePotionEffect(PotionEffectType.SLOWNESS);
        } else {
            if (player.hasPotionEffect(PotionEffectType.getByName("SLOW")))
                player.removePotionEffect(PotionEffectType.getByName("SLOW"));
        }
        if (player.hasPotionEffect(PotionEffectType.BLINDNESS))
            player.removePotionEffect(PotionEffectType.BLINDNESS);
    }

    public static BukkitTextarea findKey(Player player) {
        return RAON_TEXT_FIELD_MAP.get(player.getUniqueId());
    }

    public static boolean isKey(Player player) {
        return findKey(player) != null;
    }

    public static List<BukkitTextarea> getKeys() {
        return RAON_TEXT_FIELD_MAP.values().stream().toList();
    }
}