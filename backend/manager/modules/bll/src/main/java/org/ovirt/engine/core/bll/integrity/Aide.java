package org.ovirt.engine.core.bll.integrity;


import java.io.IOException;

import org.ovirt.engine.core.bll.integrity.cmd.AideCommand;
import org.ovirt.engine.core.bll.integrity.cmd.Cmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Aide {
    private AideCommand aideCommand;
    Logger logger = LoggerFactory.getLogger(Aide.class);
    private final String AIDE_PASS= "AIDE found NO differences between database and filesystem. Looks okay!!";
    private final String AIDE_fAIL= "AIDE found differences between database and filesystem!!";

    public Aide(){
        aideCommand = new AideCommand();
    }

    public boolean aideUpdate(){
        try{
            logger.info(Cmd.execute(aideCommand.getAideUpdateCommand()));
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String aideRun(){
        try {
            String result = Cmd.execute(aideCommand.getAideRunCommand());
            if(!result.equals("")){
                logger.error(result);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String aideRunWithLog(){
        try {
            String result = Cmd.execute(aideCommand.getAideRunCommandWithoutGrep());
//            logger.error(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void configReload() throws IOException {
        aideCommand.reloadFcheckConfig();
    }

    public String getAIDEPASS() {
        return AIDE_PASS;
    }

    public String getAIDEfAIL() {
        return AIDE_fAIL;
    }
}

