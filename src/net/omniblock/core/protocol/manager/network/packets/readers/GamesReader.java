package net.omniblock.core.protocol.manager.network.packets.readers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.omniblock.core.config.ConfigHandler;
import net.omniblock.core.protocol.manager.network.GameManager;
import net.omniblock.core.protocol.manager.network.NetworkManager;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.data.PacketStructure;
import net.omniblock.packets.network.structure.data.PacketStructure.DataType;
import net.omniblock.packets.network.structure.packet.GameOnlineInfoPacket;
import net.omniblock.packets.network.structure.packet.RequestInformationPacket;
import net.omniblock.packets.network.structure.packet.ResposeInformationPacket;
import net.omniblock.packets.network.tool.object.PacketReader;

public class GamesReader {

	public static void start() {
		
		/*
		 * 
		 * Este reader es el encargado de actualizar
		 * la información de el estado de un juego en
		 * base al paquete que contiene dicha información.
		 * 
		 */
		Packets.READER.registerReader(new PacketReader<GameOnlineInfoPacket>(){

			@Override
			public void readPacket(PacketSocketData<GameOnlineInfoPacket> packetsocketdata) {
				
				PacketStructure structure = packetsocketdata.getStructure();
				
				String servername = structure.get(DataType.STRINGS, "servername");
				String mapname = structure.get(DataType.STRINGS, "mapname");
				
				Integer maxplayers = structure.get(DataType.INTEGERS, "maximiumplayers");
				Integer onlineplayers = structure.get(DataType.INTEGERS, "onlineplayers");
				
				Boolean opened = structure.get(DataType.BOOLEANS, "opened");
				
				GameManager.setGameOnlineINFO(servername, mapname, onlineplayers, maxplayers);
				GameManager.setGameOpened(servername, opened);
				return;
				
			}

			@Override
			public Class<GameOnlineInfoPacket> getAttachedPacketClass() {
				return GameOnlineInfoPacket.class;
			}
			
		});
		
		Packets.READER.registerReader(new PacketReader<RequestInformationPacket>(){

			@Override
			public void readPacket(PacketSocketData<RequestInformationPacket> packetsocketdata) {
				
				PacketStructure structure = packetsocketdata.getStructure();
				
				String servername = structure.get(DataType.STRINGS, "servername");
				
				String infokey = structure.get(DataType.STRINGS, "infokey");
				String infovalue = structure.get(DataType.STRINGS, "infovalue");
				
				if(infokey.equalsIgnoreCase("Skywars") && infovalue.equalsIgnoreCase("isZActivated")){
					
					DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now());
					
					boolean isZActivated = (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY ||
												dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.WEDNESDAY);
					
					isZActivated = true; // TODO Change
					
					Packets.STREAMER.streamPacket(
							new ResposeInformationPacket()
							.setInformationPreset("gen_sheet_preset")
							.setInformationKey("Skywars")
							.setInformationValue(String.valueOf(isZActivated))
							.build()
							.setPacketUUID(packetsocketdata.getPacketUUID())
							.setReceiver(NetworkManager.getSocketServer(servername)));
					
				}
				
				return;
				
			}

			@Override
			public Class<RequestInformationPacket> getAttachedPacketClass() {
				return RequestInformationPacket.class;
			}
			
		});
		
		Packets.READER.registerReader(new PacketReader<RequestInformationPacket>(){

			public String weekprizeformat = "dd-MMM-yyyy HH:mm:ss";
			
			@Override
			public void readPacket(PacketSocketData<RequestInformationPacket> packetsocketdata) {
				
				PacketStructure structure = packetsocketdata.getStructure();
				
				String servername = structure.get(DataType.STRINGS, "servername");
				
				String infokey = structure.get(DataType.STRINGS, "infokey");
				String infovalue = structure.get(DataType.STRINGS, "infovalue");
				
				if(infokey.equalsIgnoreCase("WeekPrize")){
					
					if(infovalue.equalsIgnoreCase("Skywars")){
						
						String respose = "WAITING";
						
						if(ConfigHandler.getWeekPrizeConfig().getConfiguration().containsKey("weekprizes.skywars")){
							
							Date date = new Date();
							Map<Date, String> dates = new HashMap<Date, String>();
							
							for(String cache : ConfigHandler.getWeekPrizeConfig().getConfiguration().getStringArray("weekprizes.skywars")){
								
								try { dates.put(new SimpleDateFormat(weekprizeformat).parse(cache), cache);
								} catch (ParseException e) { }
								
							}
							
							for (Date cache: dates.keySet()) {
							    if (date.compareTo(date) <= 0) {
							        if (date.compareTo(date) > 0) {
							            respose = dates.get(cache);
							        }
							    }
							}
							
						}
						
						Packets.STREAMER.streamPacket(
								new ResposeInformationPacket()
								.setInformationPreset("WeekPrize")
								.setInformationKey("Skywars")
								.setInformationValue(respose)
								.build()
								.setPacketUUID(packetsocketdata.getPacketUUID())
								.setReceiver(NetworkManager.getSocketServer(servername)));
						
					}
					
				}
				
				return;
				
			}
			
			@Override
			public Class<RequestInformationPacket> getAttachedPacketClass() {
				return RequestInformationPacket.class;
			}
			
		});
		
	}
	
}
