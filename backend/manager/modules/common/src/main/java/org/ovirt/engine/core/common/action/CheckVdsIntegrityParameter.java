package org.ovirt.engine.core.common.action;

import org.ovirt.engine.core.compat.Guid;

import javax.validation.constraints.NotNull;

public class CheckVdsIntegrityParameter extends ActionParametersBase{

    private static final long serialVersionUID = -609386731324599884L;

    @NotNull
    Guid id;
    private boolean force;

    public CheckVdsIntegrityParameter(){
        id = Guid.Empty;
    }

    public CheckVdsIntegrityParameter(boolean force){
        this.force = force;
        id = Guid.Empty;
    }

    public boolean isForce(){
        return force;
    }
    public void setForce(boolean force){
        this.force = force;
    }

    public Guid getId() {
        return id;
    }

    public void setId(Guid id) {
        this.id = id;
    }
}
