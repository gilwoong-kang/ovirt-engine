package org.ovirt.engine.core.bll;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.ovirt.engine.core.bll.context.CommandContext;
import org.ovirt.engine.core.bll.utils.PermissionSubject;
import org.ovirt.engine.core.common.VdcObjectType;
import org.ovirt.engine.core.common.action.CheckVdsIntegrityParameter;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.vdsbroker.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NonTransactiveCommandAttribute
public class CheckVdsIntegrityCommand<T extends CheckVdsIntegrityParameter> extends CommandBase<T>{

    Logger logger = LoggerFactory.getLogger(CheckVdsIntegrityParameter.class);
    CheckVdsIntegrityParameter checkVdsIntegrityParameter;

    @Inject
    private ResourceManager resourceManager;

    public CheckVdsIntegrityCommand(T parameters, CommandContext commandContext){
        super(parameters, commandContext);
        checkVdsIntegrityParameter = parameters;
    }

    @Override
    protected void executeCommand() {
        logger.info(super.getParameters().toString());
        logger.info("exec checkvdsIntegrityCommand");
        setVdsId(checkVdsIntegrityParameter.getVdsId());
        VDSReturnValue res = resourceManager.getVdsManager(checkVdsIntegrityParameter.getVdsId())
                .checkHostIntegrity(getVds());
        setSucceeded(true);
    }

    @Override
    public List<PermissionSubject> getPermissionCheckSubjects() {
        List<PermissionSubject> permissionList = new ArrayList<>();
        permissionList.add(new PermissionSubject(Guid.SYSTEM,
                VdcObjectType.System,
                getActionType().getActionGroup()));
        return permissionList;
    }
}
