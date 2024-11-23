script.addEventListener("DataPacketSendEvent", function(event){
  if(event.isCancelled()) return;
  let packet = event.getPacket();
  let player = event.getPlayer();
  let TextPacket = Java.type("cn.nukkit.network.protocol.TextPacket");
  if(!(packet instanceof TextPacket)) return;
  if(player.protocol > 422) return;
  let message = packet.message || packet.source;
  let TextFormat = Java.type("cn.nukkit.utils.TextFormat");
  switch(true){
    case message.indexOf(TextFormat.MATERIAL_QUARTZ.toString()) != -1:
    case message.indexOf(TextFormat.MATERIAL_IRON.toString()) != -1:
    case message.indexOf(TextFormat.MATERIAL_NETHERITE.toString()) != -1:
    case message.indexOf(TextFormat.MATERIAL_REDSTONE.toString()) != -1:
    case message.indexOf(TextFormat.MATERIAL_COPPER.toString()) != -1:
    case message.indexOf(TextFormat.MATERIAL_GOLD.toString()) != -1:
    case message.indexOf(TextFormat.MATERIAL_EMERALD.toString()) != -1:
    case message.indexOf(TextFormat.MATERIAL_DIAMOND.toString()) != -1:
    case message.indexOf(TextFormat.MATERIAL_LAPIS.toString()) != -1:
    case message.indexOf(TextFormat.MATERIAL_AMETHYST.toString()) != -1:
      break;
    default:
      return;
  }
  event.setCancelled(true);
  packet.message = message
    .replaceAll(TextFormat.MATERIAL_QUARTZ.toString(), TextFormat.WHITE.toString())
    .replaceAll(TextFormat.MATERIAL_IRON.toString(), TextFormat.WHITE.toString())
    .replaceAll(TextFormat.MATERIAL_NETHERITE.toString(), TextFormat.GRAY.toString())
    .replaceAll(TextFormat.MATERIAL_REDSTONE.toString(), TextFormat.DARK_RED.toString())
    .replaceAll(TextFormat.MATERIAL_COPPER.toString(), TextFormat.GOLD.toString())
    .replaceAll(TextFormat.MATERIAL_GOLD.toString(), TextFormat.YELLOW.toString())
    .replaceAll(TextFormat.MATERIAL_EMERALD.toString(), TextFormat.DARK_GREEN.toString())
    .replaceAll(TextFormat.MATERIAL_DIAMOND.toString(), TextFormat.AQUA.toString())
    .replaceAll(TextFormat.MATERIAL_LAPIS.toString(), TextFormat.BLUE.toString())
    .replaceAll(TextFormat.MATERIAL_AMETHYST.toString(), TextFormat.LIGHT_PURPLE.toString());
  packet.source = packet.message;
  player.dataPacket(packet);
});
