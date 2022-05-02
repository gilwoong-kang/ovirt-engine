package org.ovirt.engine.core.vdsbroker.vdsbroker;

import org.ovirt.engine.core.common.vdscommands.BrokerCommandCallback;
import org.ovirt.engine.core.common.vdscommands.VdsIdAndVdsVDSCommandParametersBase;
import org.ovirt.engine.core.utils.log.Logged;
import org.ovirt.engine.core.utils.log.Logged.LogLevel;

import java.util.Map;

@Logged(executionLevel = LogLevel.DEBUG)
public class IntegrityAsyncVDSCommand<P extends VdsIdAndVdsVDSCommandParametersBase> extends IntegrityVdsBrokerCommand<P>{
    IntegrityAsyncVDSCommand(P parameters){super(parameters, parameters.getVds());}

    protected VDSIntegrityReturn integrityReturn;

    @Override
    protected void executeVdsBrokerCommand() {
        try{
            getBroker().runIntegrity(new IntegrityVDSCommandCallback());
        }catch (Throwable t){
            getParameters().getCallback().onFailure(t);
        }
    }

    private class IntegrityVDSCommandCallback implements BrokerCommandCallback{

        @Override
        public void onResponse(Map<String, Object> response) {
            getVDSReturnValue().setReturnValue(response.get("result"));
        }

        @Override
        public void onFailure(Throwable t) {

        }
    }
}
