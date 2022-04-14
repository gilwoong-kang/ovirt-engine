package org.ovirt.engine.core.bll.integrity;


//import org.ovirt.engine.core.bll.integrity.mail.SendMail;
import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.dal.dbbroker.auditloghandling.AuditLogDirector;
import org.ovirt.engine.core.dal.dbbroker.auditloghandling.AuditLogableBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IntegrityController extends AuditLogableBase {

    @Inject
    private AuditLogDirector auditLogDirector;

    private final int PERIOD_TIME = 1000*60*60;

    private Fcheck fcheck;
  //  private SendMail sendMail;
    private final int abnormalValue = 10;
    Logger logger = LoggerFactory.getLogger(IntegrityController.class);
//    private PropertiesConfig propertiesConfig;
    public IntegrityController() {
//        PropertyConfigurator.configure(prop);
        fcheck = new Fcheck();
  //      sendMail = new SendMail();
//        propertiesConfig = new PropertiesConfig();
    }

    public void dataUpdate(){
        fcheck.fcheckUpdate();
        logger.info("fcheck update. ");
    }

    public void run(Backend backend){
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        IntegrityController integrityController = this;
        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                logger.info("perform periodic verification.");
                while(true){
                    logger.info("Integrity check...");
                    String[] report = fcheck.fcheckRunWithLog().split("\n");
                    if(!parse(report).isEmpty()){
                        logger.error("integrity problem. Detail");
                        StringBuilder log = new StringBuilder();
                        for(String s : report){
                            log.append(s+"\n");
                        }
                        logger.error(log.toString());
                        addCustomValue("errorFile",log.toString());
                        auditLogDirector.log(integrityController,AuditLogType.INTEGRITY_CHECK_FAIL);
                        System.exit(0);
                        break;
                    }else{
                        logger.info("System Integrity check success. is stable. ["+System.currentTimeMillis()+"]");
                        auditLogDirector.log(integrityController, AuditLogType.INTEGRITY_CHECK_PASS);
                        try{
                            Thread.sleep(PERIOD_TIME);
                        }catch(InterruptedException e){
                            logger.error("System interrupt.");
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    public boolean runOnce(){
        logger.info("run once.");
        String[] report = fcheck.fcheckRunWithLog().split("\n");
        if(!parse(report).isEmpty()){
            logger.error("integrity problem. Detail");
            StringBuilder log = new StringBuilder();
            for(String s : report){
	        log.append(s);
	    }
            logger.error(log.toString());
            addCustomValue("errorFile",log.toString());
            auditLogDirector.log(this,AuditLogType.INTEGRITY_CHECK_ENGINE_INIT_FAIL);
            return false;
        }else{
	    logger.info("System Integrity check success. is stable. ["+System.currentTimeMillis()+"]");
	    auditLogDirector.log(this, AuditLogType.INTEGRITY_CHECK_ENGINE_INIT_PASS);
	    return true;
	    }
    }

    public String runAdminReq(){
        logger.info("run admin req...");
        String[] report = fcheck.fcheckRunWithLog().split("\n");
        if(!parse(report).isEmpty()){
            StringBuilder log = new StringBuilder();
            for(String s : report){
                log.append(s);
            }
            return log.toString();
        }else{
            logger.info("System Integrity check success. is stable. ["+System.currentTimeMillis()+"]");
            return "";
        }
    }

    private List<String> parse(String[] content){
        List<String> result = new ArrayList<>();
        for(String value : content){
            String[] line = value.split(":");
            if(line[0].equals("\tWARNING") || line[0].equals("\tDELETION") || line[0].equals("\tADDITION")){
                result.add(value);
            }
        }
        return result;
    }

    @Override
    public AuditLogType getAuditLogTypeValue(){
        return AuditLogType.INTEGRITY_CHECK_PASS;
    }
}

