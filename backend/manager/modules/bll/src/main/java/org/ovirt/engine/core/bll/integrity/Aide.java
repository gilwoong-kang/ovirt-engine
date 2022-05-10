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
    private final String AIDE_FAIL = "AIDE found differences between database and filesystem!!";

    public Aide(){
        aideCommand = new AideCommand();
    }

    public boolean aideUpdate(){
        try{
            // todo aide command update
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
            // todo aide command update
            String result = Cmd.execute(aideCommand.getAideRunCommandWithoutGrep());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAIDEPASS() {
        return AIDE_PASS;
    }

    public String getAIDEFAIL() {
        return AIDE_FAIL;
    }
}

