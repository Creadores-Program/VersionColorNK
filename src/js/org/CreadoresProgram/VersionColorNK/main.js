script.registerScript({
  name: "VersionColorNK",
  version: "1.0",
  description: "In old versions change the modern colors to the old Minecraft colors in chat",
  author: "Creadores Program"
});
script.addEventListener("DataPacketSendEvent", function(event){
  let packet = event.getPacket();
  let TextPacket = Java.type("cn.nukkit.network.protocol.TextPacket");
  if(!packet instanceof TextPacket) return;
  if(player.protocol > ) return;
});
