package org.ovirt.engine.core.vdsbroker.vdsbroker;

import java.util.Map;

public class VDSIntegrityReturn {
    private static final String RESULT = "result";

    public String result;

    public VDSIntegrityReturn(){
    }

    public VDSIntegrityReturn(Map<String, Object> innerMap){
        result = String.valueOf(innerMap.get(RESULT));
    }
}
