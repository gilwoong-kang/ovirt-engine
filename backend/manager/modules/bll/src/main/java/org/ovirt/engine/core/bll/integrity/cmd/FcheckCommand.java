package org.ovirt.engine.core.bll.integrity.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FcheckCommand {
    private Properties integrityProperties;
//    private final static String CONFIG_PATH = "src/main/resources/commandConfig.properties";
    private InputStream CONFIG_PATH;
    public FcheckCommand(){
        try{
	    CONFIG_PATH=FcheckCommand.class.getClassLoader().getResourceAsStream("commandConfig.properties");
            integrityProperties = new Properties();
            integrityProperties.load(CONFIG_PATH);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public String[] getFcheckUpdateCommand(){
        return new String[]{integrityProperties.getProperty("shell"),
        integrityProperties.getProperty("shell.option"),
        integrityProperties.getProperty("fcheck.path")+integrityProperties.getProperty("fcheck.update")
        };
    }
    public String[] getFcheckRunCommand(){
        return new String[]{integrityProperties.getProperty("shell"),
        integrityProperties.getProperty("shell.option"),
        integrityProperties.getProperty("fcheck.path")+integrityProperties.getProperty("fcheck.run")+
        integrityProperties.getProperty("grep")+integrityProperties.getProperty("grep.total")};
    }

    public String[] getFcheckRunCommandWithoutGrep(){
        return new String[]{integrityProperties.getProperty("shell"),
        integrityProperties.getProperty("shell.option"),
//        integrityProperties.getProperty("fcheck.path")+integrityProperties.getProperty("fcheck.run")
        integrityProperties.getProperty("aide.commnad")
        };
    }

    public void setFcheckPath(String newPath){
        integrityProperties.setProperty("fcheck.path", newPath);
    }
    public void reloadFcheckConfig() throws IOException {
	integrityProperties = new Properties();
	integrityProperties.load(CONFIG_PATH);
    }
}
