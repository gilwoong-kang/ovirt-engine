package org.ovirt.engine.core.bll.integrity;


import java.io.IOException;
import org.ovirt.engine.core.bll.integrity.cmd.Cmd;
import org.ovirt.engine.core.bll.integrity.cmd.AideCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Aide {
    private AideCommand aideCommand;
    Logger logger = LoggerFactory.getLogger(Aide.class);

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
            if(!result.equals("")){logger.error(result);}
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
}

