package org.ovirt.engine.core.bll;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.ovirt.engine.core.bll.context.CommandContext;
import org.ovirt.engine.core.bll.integrity.EngineIntegrityController;
import org.ovirt.engine.core.bll.utils.PermissionSubject;
import org.ovirt.engine.core.common.VdcObjectType;
import org.ovirt.engine.core.common.action.CheckIntegrityParameter;
import org.ovirt.engine.core.compat.Guid;


public class AbstractIntegrityCommand<T extends CheckIntegrityParameter> extends CommandBase<T> {

    @Inject
    private EngineIntegrityController engineIntegrityController;

    public AbstractIntegrityCommand(T parameters, CommandContext commandContext){
        super(parameters, commandContext);
    }

    @Override
    protected void executeCommand() {

    }

    @Override
    public List<PermissionSubject> getPermissionCheckSubjects(){
        List<PermissionSubject> permissionList = new ArrayList<>();
        permissionList.add(new PermissionSubject(Guid.SYSTEM,
                VdcObjectType.System,
                getActionType().getActionGroup()));
        return permissionList;
    }
}
