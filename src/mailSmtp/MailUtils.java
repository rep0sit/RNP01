package mailSmtp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
	 * @author nelli and etienne
	 *
	 */
	public class MailUtils {
		//METHODS
			public static Properties getProp(String konfigFile){
				Properties props = new Properties();

				FileInputStream inputStr = null;
				// file = "config.properties";
				try {
					inputStr = new FileInputStream(konfigFile);
					
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
