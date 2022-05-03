package org.ovirt.engine.core.vdsbroker.vdsbroker;

import java.util.Map;

@SuppressWarnings("unchecked")
public class VDSIntegrityReturn {
    private static final String RESULT = "result";
    private static final String STATUS = "out";

    public String status;
    public String result;

    public VDSIntegrityReturn(){
    }

    public VDSIntegrityReturn(Map<String, Object> innerMap){
        status = String.valueOf(innerMap.get(STATUS));
        Object obj = innerMap.get(RESULT);
        if(obj == null){
            result = "res is null";
        } else{
            result = obj.toString();
        }
    }
}
