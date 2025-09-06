package org.CreadoresProgram.VersionColorNK;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.event.Listener;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.network.protocol.TextPacket;
import cn.nukkit.network.protocol.SetTitlePacket;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.Player;
public class Main extends PluginBase implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void onDataPacketSendEvent(DataPacketSendEvent event){
        if(event.isCancelled()){
            return;
        }
        if(event.getPlayer().protocol > 419){
            return;
        }
        if(event.getPacket() instanceof TextPacket){
            this.processPacket((TextPacket) event.getPacket());
        }else if(event.getPacket() instanceof SetTitlePacket){
            this.processPacket((SetTitlePacket) event.getPacket());
        }
    }
    private String processText(String message){
        return message
            .replace(TextFormat.MATERIAL_QUARTZ.toString(), TextFormat.WHITE.toString())
            .replace(TextFormat.MATERIAL_IRON.toString(), TextFormat.WHITE.toString())
            .replace(TextFormat.MATERIAL_NETHERITE.toString(), TextFormat.GRAY.toString())
            .replace(TextFormat.MATERIAL_REDSTONE.toString(), TextFormat.DARK_RED.toString())
            .replace(TextFormat.MATERIAL_COPPER.toString(), TextFormat.GOLD.toString())
            .replace(TextFormat.MATERIAL_GOLD.toString(), TextFormat.YELLOW.toString())
            .replace(TextFormat.MATERIAL_EMERALD.toString(), TextFormat.DARK_GREEN.toString())
            .replace(TextFormat.MATERIAL_DIAMOND.toString(), TextFormat.AQUA.toString())
            .replace(TextFormat.MATERIAL_LAPIS.toString(), TextFormat.BLUE.toString())
            .replace(TextFormat.MATERIAL_AMETHYST.toString(), TextFormat.LIGHT_PURPLE.toString())
            .replace(TextFormat.MATERIAL_RESIN.toString(), TextFormat.TextFormat.GOLD.toString());
    }
    private void processPacket(TextPacket packet, Player player){
        String message = packet.message;
        if(message == null){
            message = packet.source;
        }
        message = processText(message);
        if(packet.message != null && packet.message.equals(message)){
            return;
        }
        if(packet.source != null && package.message == null){
            packet.source = message;
        }
        if(packet.message != null){
            packet.message = message;
        }
    }
    private void processPacket(SetTitlePacket packet, Player player){
        String message = packet.text;
        message = processText(message);
        if(packet.text.equals(message)){
            return;
        }
        packet.text = message;
    }
}