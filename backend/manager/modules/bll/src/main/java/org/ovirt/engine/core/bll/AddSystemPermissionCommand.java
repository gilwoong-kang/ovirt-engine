package org.ovirt.engine.core.bll;

import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.VdcObjectType;
import org.ovirt.engine.core.common.action.PermissionsOperationsParametes;

public class AddSystemPermissionCommand<T extends PermissionsOperationsParametes> extends AddPermissionCommand<T> {

    public AddSystemPermissionCommand(T parameters) {
        super(parameters);
        parameters.getPermission().setObjectId(MultiLevelAdministrationHandler.SYSTEM_OBJECT_ID);
        parameters.getPermission().setObjectType(VdcObjectType.System);
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        return getSucceeded() ? AuditLogType.USER_ADD_SYSTEM_PERMISSION : AuditLogType.USER_ADD_SYSTEM_PERMISSION_FAILED;
    }

}
