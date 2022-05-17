package org.ovirt.engine.core.vdsbroker.vdsbroker;

import java.util.Map;

import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.vdscommands.VdsAndCmdVDSCommandParametersBase;
import org.ovirt.engine.core.dal.dbbroker.auditloghandling.AuditLogable;
import org.ovirt.engine.core.dal.dbbroker.auditloghandling.AuditLogableImpl;
import org.ovirt.engine.core.utils.log.Logged;
import org.ovirt.engine.core.utils.log.Logged.LogLevel;
import org.ovirt.vdsm.jsonrpc.client.BrokerCommandCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Logged(executionLevel = LogLevel.DEBUG)
public class IntegrityAsyncVDSCommand<P extends VdsAndCmdVDSCommandParametersBase> extends IntegrityVdsBrokerCommand<P>{
    private P parameters;

    public IntegrityAsyncVDSCommand(P parameters){
        super(parameters, parameters.getVds());
        this.parameters = parameters;
    }

    protected VDSIntegrityReturn vdsIntegrityReturn;
    Logger logger = LoggerFactory.getLogger(IntegrityAsyncVDSCommand.class);

    @Override
    protected Object getReturnValueFromBroker(){
        return vdsIntegrityReturn;
    }

    @Override
    protected Status getReturnStatus() {
        return null;
    }

    @Override
    protected void executeVdsBrokerCommand() {
        try{
            getBroker().runIntegrity(parameters.getCmd(), new IntegrityVDSCommandCallback());
        }catch (Throwable t){
            getParameters().getCallback().onFailure(t);
        }
    }

    private class IntegrityVDSCommandCallback implements BrokerCommandCallback{

        @Override
        public void onResponse(Map<String, Object> response) {
            logger.info("integrity vds async onResponse");
            vdsIntegrityReturn = new VDSIntegrityReturn(response);
            for(String key : response.keySet()){
                logger.info("IntegrityVDSCOmmandCallback");
                logger.info(key);
                logger.info(String.valueOf(response.get(key)));
            }

            AuditLogable logable = new AuditLogableImpl();
            logable.setVdsId(getParameters().getVdsId());
            logable.setVdsName(getParameters().getVds().getName());
            logable.addCustomValue("host", getParameters().getVds().getHostName());
            logable.addCustomValue("result", String.valueOf(response.get("info")));
            logable.addCustomValue("status", "0001");

            getAuditLogable().log(logable, AuditLogType.INTEGRITY_CHECK_VDS_PASS);
        }

        @Override
        public void onFailure(Map<String, Object> map) {
            AuditLogable logable = new AuditLogableImpl();
            logable.setVdsId(getParameters().getVdsId());
            logable.setVdsName(getParameters().getVds().getName());
            logable.addCustomValue("host", getParameters().getVds().getHostName());
            logable.addCustomValue("result", String.valueOf(map.get("info")));
            logable.addCustomValue("status", new Status(map).message);

            getAuditLogable().log(logable, AuditLogType.INTEGRITY_CHECK_VDS_FAIL);
        }
    }
}
