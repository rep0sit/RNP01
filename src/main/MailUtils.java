package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author nelli and etienne
 *
 */
class MailUtils {
	//FIELDS
	public static final String CONFIG_FILE = "Konfigurationsdatei.txt";
	
	//METHODS
	public static Properties getProp(String konfigFile){
		Properties props = new Properties();

		System.out.println("KONFIGDATEI: "+konfigFile);
		FileInputStream inputStr = null;
		// file = "config.properties";
		try {
			inputStr = new FileInputStream(konfigFile);
			if(inputStr == null){
				System.out.println("Unable to find "+konfigFile);
			}
			//Laden des PropertFiles
			props.load(inputStr);
		}catch(IOException io){
			io.printStackTrace();
		}finally{
			if(inputStr !=null){
				try{
					inputStr.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return props;
	}
}