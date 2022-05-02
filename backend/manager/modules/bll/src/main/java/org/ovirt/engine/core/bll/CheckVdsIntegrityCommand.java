package org.ovirt.engine.core.bll;

import org.ovirt.engine.core.bll.context.CommandContext;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.action.VdsActionParameters;
import org.ovirt.engine.core.vdsbroker.ResourceManager;

import javax.inject.Inject;

@NonTransactiveCommandAttribute
public class CheckVdsIntegrityCommand<T extends VdsActionParameters> extends VdsCommand{

    @Inject
    private ResourceManager resourceManager;

    public CheckVdsIntegrityCommand(T parameters, CommandContext commandContext){
        super(parameters, commandContext);
    }

    @Override
    protected void executeCommand() {
        Object result = resourceManager.getVdsManager(getVdsId()).checkHostIntegrity(getVds());
        addCustomValue("host", getVdsName());
        addCustomValue("result", String.valueOf(result));
        setSucceeded(true);
    }

    @Override
    public AuditLogType getAuditLogTypeValue(){
        return getSucceeded() ? AuditLogType.INTEGRITY_CHECK_VDS_PASS
                : AuditLogType.INTEGRITY_CHECK_VDS_FAIL;
    }
}
