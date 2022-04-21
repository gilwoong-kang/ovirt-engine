package org.ovirt.engine.core.vdsbroker.vdsbroker;

import java.util.Map;

@SuppressWarnings("unchecked")
public class HostCliReturn {
    private static final String RESULT = "result";

    public String result;

    public HostCliReturn(Map<String, Object> innerMap){
        result = String.valueOf(innerMap.get(RESULT));
    }

}
