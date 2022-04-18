package org.ovirt.engine.core.bll;

import org.ovirt.engine.core.bll.context.CommandContext;
import org.ovirt.engine.core.bll.integrity.EngineIntegrityController;
import org.ovirt.engine.core.bll.utils.PermissionSubject;
import org.ovirt.engine.core.common.VdcObjectType;
import org.ovirt.engine.core.common.action.CheckIntegrityParameter;
import org.ovirt.engine.core.compat.Guid;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class AbstractIntegrityCommand<T extends CheckIntegrityParameter> extends CommandBase<T> {

    @Inject
    private EngineIntegrityController engineIntegrityController;

    public AbstractIntegrityCommand(T parameters, CommandContext commandContext){
        super(parameters,commandContext);
    }

    @Override
    protected void executeCommand() {

    }

    //    protected Whitelist getWhitelist() {
//        return getParameters().getWhitelist();
//    }
//
//    protected List<Whitelist> getWhitelists() {
//        // FIXME, getAll error
//        return whitelistDao.getAll();
//    }
    @Override
    public List<PermissionSubject> getPermissionCheckSubjects(){
        List<PermissionSubject> permissionList = new ArrayList<>();
        permissionList.add(new PermissionSubject(Guid.SYSTEM,
                VdcObjectType.System,
                getActionType().getActionGroup()));
        return permissionList;
    }
}
