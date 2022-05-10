package org.ovirt.engine.core.bll;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.ovirt.engine.core.bll.context.CommandContext;
import org.ovirt.engine.core.bll.utils.PermissionSubject;
import org.ovirt.engine.core.common.VdcObjectType;
import org.ovirt.engine.core.common.action.CheckVdsIntegrityParameter;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.vdsbroker.ResourceManager;

@NonTransactiveCommandAttribute
public class CheckVdsIntegrityCommand<T extends CheckVdsIntegrityParameter> extends CommandBase<T>{
    CheckVdsIntegrityParameter checkVdsIntegrityParameter;

    @Inject
    private ResourceManager resourceManager;

    public CheckVdsIntegrityCommand(T parameters, CommandContext commandContext){
        super(parameters, commandContext);
        checkVdsIntegrityParameter = parameters;
    }

    @Override
    protected void executeCommand() {
        setVdsId(checkVdsIntegrityParameter.getVdsId());
        resourceManager.getVdsManager(checkVdsIntegrityParameter.getVdsId())
                .checkHostIntegrity(getVds());
        // todo resourceManager 직접 runAsyncVDSCommand해도 되지 않나?
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
