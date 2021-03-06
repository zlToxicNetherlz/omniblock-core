package net.omniblock.core.protocol.manager.xml.type;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import net.omniblock.core.protocol.manager.xml.XML;
import net.omniblock.packets.network.tool.config.Config;

public class VersionsXML implements XML {

	@Override
	public XML create(String filename) throws URISyntaxException, FileNotFoundException {
		
		File file = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath(), "/plugins/" + filename);
		
		if(!file.exists()){
			
			file.mkdirs();
			Config.copyFile(ClassLoader.getSystemResourceAsStream("/plugins/" + filename), file);
			return this;
			
		}
		
		return this;
	}

}
