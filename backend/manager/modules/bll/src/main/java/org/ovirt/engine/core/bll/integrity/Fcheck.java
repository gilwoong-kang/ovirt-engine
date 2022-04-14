package org.ovirt.engine.core.bll.integrity;


import java.io.IOException;
import org.ovirt.engine.core.bll.integrity.cmd.Cmd;
import org.ovirt.engine.core.bll.integrity.cmd.FcheckCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fcheck {
    private FcheckCommand fcheckCommand;
    Logger logger = LoggerFactory.getLogger(Fcheck.class);

    public Fcheck(){
        fcheckCommand = new FcheckCommand();
    }

    public boolean fcheckUpdate(){
        try{
            logger.info(Cmd.execute(fcheckCommand.getFcheckUpdateCommand()));
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public String fcheckRun(){
        try {
            String result = Cmd.execute(fcheckCommand.getFcheckRunCommand());
            if(!result.equals("")){logger.error(result);}
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String fcheckRunWithLog(){
        try {
            String result = Cmd.execute(fcheckCommand.getFcheckRunCommandWithoutGrep());
//            logger.error(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void configReload() throws IOException {
        fcheckCommand.reloadFcheckConfig();
    }
}

