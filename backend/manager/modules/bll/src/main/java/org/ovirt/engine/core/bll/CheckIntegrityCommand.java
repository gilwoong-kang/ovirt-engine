package org.ovirt.engine.core.bll;

import javax.inject.Inject;

import org.ovirt.engine.core.bll.context.CommandContext;
import org.ovirt.engine.core.bll.integrity.EngineIntegrityController;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.action.CheckIntegrityParameter;
import org.ovirt.engine.core.vdsbroker.jsonrpc.JsonRpcVdsServer;


@NonTransactiveCommandAttribute(forceCompensation = true)
public class CheckIntegrityCommand<P extends CheckIntegrityParameter> extends AbstractIntegrityCommand<CheckIntegrityParameter> {

    @Inject
    private EngineIntegrityController engineIntegrityController;

    public CheckIntegrityCommand(CheckIntegrityParameter parameters, CommandContext cmdContext){
        super(parameters, cmdContext);
        cmdContext.getExecutionContext().setMonitored(false);
    }

    @Override
    protected void executeCommand() {
        String result = engineIntegrityController.runAdminReq();
        if(result.isBlank()){
            setSucceeded(true);
        }else{
            addCustomValue("errorFile", result);
            setSucceeded(false);
        }
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        return getSucceeded() ? AuditLogType.INTEGRITY_CHECK_ADMIN_PASS : AuditLogType.INTEGRITY_CHECK_ADMIN_FAIL;
    }
}
