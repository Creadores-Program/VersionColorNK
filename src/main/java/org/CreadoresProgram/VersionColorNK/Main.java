package org.CreadoresProgram.VersionColorNK;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.network.DataPacket;
import cn.nukkit.network.protocol.SetTitlePacket;
import cn.nukkit.network.protocol.TextPacket;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;
import java.util.Map;

public class Main extends PluginBase implements Listener {

    private static final char ESC = TextFormat.ESCAPE;

    private static final class ColorRule {
        final int minProtocol;
        final String fallback;

        ColorRule(int minProtocol, String fallback) {
            this.minProtocol = minProtocol;
            this.fallback = fallback;
        }
    }

    private static final Map<Character, ColorRule> RULES = new HashMap<>(16);

    static {
        RULES.put(TextFormat.MINECOIN_GOLD.getChar(), new ColorRule(407, TextFormat.GOLD.toString()));

        RULES.put(TextFormat.MATERIAL_QUARTZ.getChar(), new ColorRule(560, TextFormat.WHITE.toString()));
        RULES.put(TextFormat.MATERIAL_IRON.getChar(), new ColorRule(560, TextFormat.WHITE.toString()));
        RULES.put(TextFormat.MATERIAL_NETHERITE.getChar(), new ColorRule(560, TextFormat.DARK_GRAY.toString()));
        RULES.put(TextFormat.MATERIAL_REDSTONE.getChar(), new ColorRule(560, TextFormat.DARK_RED.toString()));
        RULES.put(TextFormat.MATERIAL_COPPER.getChar(), new ColorRule(560, TextFormat.GOLD.toString()));
        RULES.put(TextFormat.MATERIAL_GOLD.getChar(), new ColorRule(560, TextFormat.YELLOW.toString()));
        RULES.put(TextFormat.MATERIAL_EMERALD.getChar(), new ColorRule(560, TextFormat.DARK_GREEN.toString()));
        RULES.put(TextFormat.MATERIAL_DIAMOND.getChar(), new ColorRule(560, TextFormat.AQUA.toString()));
        RULES.put(TextFormat.MATERIAL_LAPIS.getChar(), new ColorRule(560, TextFormat.BLUE.toString()));
        RULES.put(TextFormat.MATERIAL_AMETHYST.getChar(), new ColorRule(560, TextFormat.LIGHT_PURPLE.toString()));

        RULES.put(TextFormat.MATERIAL_RESIN.getChar(), new ColorRule(748, TextFormat.GOLD.toString()));
        RULES.put(TextFormat.PARTY_BLUE.getChar(), new ColorRule(748, TextFormat.AQUA.toString()));
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDataPacketSend(DataPacketSendEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }

        int protocol = player.protocol;
        DataPacket packet = event.getPacket();

        if (packet instanceof TextPacket) {
            processTextPacket((TextPacket) packet, protocol);
        } else if (packet instanceof SetTitlePacket) {
            processTitlePacket((SetTitlePacket) packet, protocol);
        }
    }

    private void processTextPacket(TextPacket packet, int protocol) {
        if (packet.message != null) {
            String translated = translate(packet.message, protocol);
            if (translated != null) {
                packet.message = translated;
            }
        }
        if (packet.source != null) {
            String translatedSource = translate(packet.source, protocol);
            if (translatedSource != null) {
                packet.source = translatedSource;
            }
        }
    }

    private void processTitlePacket(SetTitlePacket packet, int protocol) {
        if (packet.text != null) {
            String translated = translate(packet.text, protocol);
            if (translated != null) {
                packet.text = translated;
            }
        }
    }

    private String translate(String input, int protocol) {
        int firstEsc = input.indexOf(ESC);
        if (firstEsc == -1) {
            return null;
        }

        StringBuilder sb = null;
        int len = input.length();
        int lastCopied = 0;

        for (int i = firstEsc; i < len - 1; i++) {
            if (input.charAt(i) == ESC) {
                char code = Character.toLowerCase(input.charAt(i + 1));
                ColorRule rule = RULES.get(code);
                if (rule != null && protocol < rule.minProtocol) {
                    if (sb == null) {
                        sb = new StringBuilder(len + 8);
                    }
                    sb.append(input, lastCopied, i);
                    sb.append(rule.fallback);
                    i++;
                    lastCopied = i + 1;
                }
            }
        }

        if (sb == null) {
            return null;
        }
        sb.append(input, lastCopied, len);
        return sb.toString();
    }
}
