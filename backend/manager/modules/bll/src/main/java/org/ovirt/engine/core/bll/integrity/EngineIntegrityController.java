package org.ovirt.engine.core.bll.integrity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.ovirt.engine.core.bll.NonTransactiveCommandAttribute;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.dal.dbbroker.auditloghandling.AuditLogDirector;
import org.ovirt.engine.core.dal.dbbroker.auditloghandling.AuditLogableBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NonTransactiveCommandAttribute
public class EngineIntegrityController extends AuditLogableBase {

    @Inject
    private AuditLogDirector auditLogDirector;

    private final int PERIOD_TIME = 1000*60*60;
    private Aide aide;
    private final int abnormalValue = 10;
    Logger logger = LoggerFactory.getLogger(EngineIntegrityController.class);

    public EngineIntegrityController() {
        aide = new Aide();
    }

    public void dataUpdate(){
        // todo aide update logic dev
        aide.aideUpdate();
        logger.info("aide update. ");
    }

    // todo maybe remove ?
    public void run(){
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        EngineIntegrityController engineIntegrityController = this;
        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                logger.info("perform periodic verification.");
                while(true){
                    logger.info("Integrity check...");
                    String[] report = aide.aideRunWithLog().split("\n");
                    if(isIntegrityFail(report)){
                        logger.error("integrity problem. Detail");
                        StringBuilder log = new StringBuilder();
                        for(String s : report){
                            log.append(s);
                            log.append("\n");
                        }
                        logger.error(log.toString());
                        addCustomValue("errorFile", log.toString());
                        auditLogDirector.log(engineIntegrityController, AuditLogType.INTEGRITY_CHECK_FAIL);
                        System.exit(0);
                        break;
                    }else{
                        logger.info("System Integrity check success. is stable. ["+System.currentTimeMillis()+"]");
                        auditLogDirector.log(engineIntegrityController, AuditLogType.INTEGRITY_CHECK_PASS);
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
        String[] report = aide.aideRunWithLog().split("\n");
        if(isIntegrityFail(report)){
            logger.error("integrity problem. Detail");
            StringBuilder log = new StringBuilder();
            for(String s : report){
                log.append(s);
                log.append("\n");
            }
            logger.error(log.toString());
            addCustomValue("errorFile", log.toString());
            auditLogDirector.log(this, AuditLogType.INTEGRITY_CHECK_ENGINE_INIT_FAIL);
            return false;
        }else{
            logger.info("System Integrity check success. is stable. ["+System.currentTimeMillis()+"]");
            auditLogDirector.log(this, AuditLogType.INTEGRITY_CHECK_ENGINE_INIT_PASS);
            return true;
        }
    }

    public String runAdminReq(){
        logger.info("run admin req...");
        String[] report = aide.aideRunWithLog().split("\n");
        if(isIntegrityFail(report)){
            StringBuilder log = new StringBuilder();
            for(String s : report){
                log.append(s);
                log.append("\n");
            }
            return log.toString();
        }else{
            logger.info("System Integrity check success. is stable. ["+System.currentTimeMillis()+"]");
            return "";
        }
    }

    private boolean isIntegrityFail(String[] content){
        for(String value : content) {
//            String[] line = value.split(":");
//            if(line[0].equals("\tWARNING") || line[0].equals("\tDELETION") || line[0].equals("\tADDITION")){
//                result.add(value);
//            }

            // aide parse
            // aide integrity fail
            if (value.trim().equals(aide.getAIDEFAIL())) {
                return true;
                // aide integrity pass
            }
        }
        return false;
    }

    @Override
    public AuditLogType getAuditLogTypeValue(){
        return AuditLogType.INTEGRITY_CHECK_PASS;
    }
}

