package org.ovirt.engine.core.common.vdscommands;

import java.util.List;

import org.ovirt.engine.core.common.businessentities.VDS;
import org.ovirt.engine.core.common.utils.ToStringBuilder;

public class VdsAndCmdVDSCommandParametersBase extends VdsIdAndVdsVDSCommandParametersBase {
    private VDS privateVds;
    private String[] cmd;
    
    public String[] getCmd() {
        return cmd;
    }

    public void setCmd(String[] cmd) {
        this.cmd = cmd;
    }

    public VDS getVds() {
        return privateVds;
    }

    public void setVds(VDS vds) {
        privateVds = vds;
    }

    public VdsAndCmdVDSCommandParametersBase(VDS vds, String[] cmd) {
        super(vds);
        this.cmd = cmd;
    }

    public VdsAndCmdVDSCommandParametersBase() {
    }

    @Override
    protected ToStringBuilder appendAttributes(ToStringBuilder tsb) {
        return super.appendAttributes(tsb)
                .append("vds", getVds());
    }
}
