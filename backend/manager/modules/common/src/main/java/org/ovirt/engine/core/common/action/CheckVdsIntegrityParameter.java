package org.ovirt.engine.core.common.action;

import org.ovirt.engine.core.compat.Guid;

public class CheckVdsIntegrityParameter extends ActionParametersBase{

    private static final long serialVersionUID = -609386731324599884L;

    private Guid hostId;
    private boolean runSilent;

    public CheckVdsIntegrityParameter() {
    }

    public CheckVdsIntegrityParameter(Guid hostId) {
        this.hostId = hostId;
    }

    public Guid getVdsId() {
        return hostId;
    }

    public void setVdsId(Guid hostId) {
        this.hostId = hostId;
    }

    public boolean isRunSilent() {
        return runSilent;
    }

    public void setRunSilent(boolean runSilent) {
        this.runSilent = runSilent;
    }
}
