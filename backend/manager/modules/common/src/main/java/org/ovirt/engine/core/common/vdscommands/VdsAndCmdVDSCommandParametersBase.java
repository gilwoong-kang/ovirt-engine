package org.ovirt.engine.core.common.vdscommands;

import java.util.List;

import org.ovirt.engine.core.common.businessentities.VDS;
import org.ovirt.engine.core.common.utils.ToStringBuilder;

public class VdsAndCmdVDSCommandParametersBase extends VdsIdAndVdsVDSCommandParametersBase {
    private VDS privateVds;
    private List<String> cmd;
    
    public List<String> getCmd() {
        return cmd;
    }

    public void setCmd(List<String> cmd) {
        this.cmd = cmd;
    }

    public VDS getVds() {
        return privateVds;
    }

    public void setVds(VDS vds) {
        privateVds = vds;
    }

    public VdsAndCmdVDSCommandParametersBase(VDS vds, List<String> cmd) {
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
