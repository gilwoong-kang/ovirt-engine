package org.ovirt.engine.core.vdsbroker.vdsbroker;

import org.ovirt.engine.core.common.businessentities.VDS;
import org.ovirt.engine.core.common.vdscommands.VdsIdVDSCommandParametersBase;

public abstract class IntegrityVdsBrokerCommand<P extends VdsIdVDSCommandParametersBase> extends VdsBrokerCommand<P> {
    protected IntegrityVdsBrokerCommand(P parameters, VDS vds){ super(parameters, vds); }

    protected VDSIntegrityReturn integrityReturn;

}
