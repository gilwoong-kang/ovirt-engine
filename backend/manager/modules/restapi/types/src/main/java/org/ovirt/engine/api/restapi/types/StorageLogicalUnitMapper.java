package org.ovirt.engine.api.restapi.types;

import java.util.ArrayList;

import org.ovirt.engine.api.common.util.SizeConverter;
import org.ovirt.engine.api.model.LogicalUnit;
import org.ovirt.engine.api.model.LunStatus;
import org.ovirt.engine.api.model.Storage;
import org.ovirt.engine.api.model.StorageType;
import org.ovirt.engine.core.common.businessentities.storage_server_connections;
import org.ovirt.engine.core.common.businessentities.LUNs;

public class StorageLogicalUnitMapper {

    @Mapping(from = LUNs.class, to = LogicalUnit.class)
    public static LogicalUnit map(LUNs entity, LogicalUnit template) {
        LogicalUnit model = template != null ? template : new LogicalUnit();
        model.setId(entity.getLUN_id());
        if (entity.getVendorId()!=null && !entity.getVendorId().isEmpty()) {
            model.setVendorId(entity.getVendorId());
        }
        if (entity.getProductId()!=null && !entity.getProductId().isEmpty()) {
            model.setProductId(entity.getProductId());
        }
        if (entity.getSerial()!=null && !entity.getSerial().isEmpty()) {
            model.setSerial(entity.getSerial());
        }
        if (entity.getLunMapping()!=null) {
            model.setLunMapping(entity.getLunMapping());
        }
        if (entity.getvolume_group_id() != null && !entity.getvolume_group_id().isEmpty()) {
            model.setVolumeGroupId(entity.getvolume_group_id());
        }
        if (entity.getStorageDomainId() != null) {
            model.setStorageDomainId(entity.getStorageDomainId().toString());
        }
        if (entity.getDiskId() != null) {
            model.setDiskId(entity.getDiskId().toString());
        }
        if (entity.getStatus() != null) {
            model.setStatus(map(entity.getStatus(), null).value());
        }
        model.setSize(SizeConverter.gigasToBytes(entity.getDeviceSize()));
        model.setPaths(entity.getPathCount());
        return model;
    }

    @Mapping(from = storage_server_connections.class, to = LogicalUnit.class)
    public static LogicalUnit map(storage_server_connections entity, LogicalUnit template) {
        LogicalUnit model = template != null ? template : new LogicalUnit();
        model.setAddress(entity.getconnection());
        model.setTarget(entity.getiqn());
        model.setPort(Integer.parseInt(entity.getport()));
        model.setUsername(entity.getuser_name());
        if (entity.getconnection()!=null && entity.getport()!=null && entity.getportal()!=null) {
            model.setPortal(entity.getconnection() + ":" + entity.getport() + "," + entity.getportal());
        }
        return model;
    }

    @Mapping(from = LUNs.class, to = Storage.class)
    public static Storage map(LUNs entity, Storage template) {
        Storage model = template != null ? template : new Storage();
        model.setId(entity.getLUN_id());
        model.setType(StorageDomainMapper.map(entity.getLunType(), null));
        model.getLogicalUnits().add(map(entity, (LogicalUnit)null));
        return model;
    }

    /**
     * This mapping exists for adding a lun-disk, and assumes that 'storage' entity contains exactly one lun.
     */
    @Mapping(from = Storage.class, to = LUNs.class)
    public static LUNs map(Storage model, LUNs template) {
        LUNs entity = template != null ? template : new LUNs();
        if (model.isSetLogicalUnits()) {
            LogicalUnit logicalUnit = model.getLogicalUnits().get(0);
            entity.setLUN_id(logicalUnit.getId());
            ArrayList<storage_server_connections> connections = new ArrayList<storage_server_connections>();
            connections.add(map(logicalUnit, null));
            entity.setLunConnections(connections);
        }
        if (model.isSetType()) {
            StorageType storageType = StorageType.fromValue(model.getType());
            if (storageType != null) {
                entity.setLunType(StorageDomainMapper.map(storageType, null));
            }
        }
        return entity;
    }

    @Mapping(from = LogicalUnit.class, to = storage_server_connections.class)
    public static storage_server_connections map(LogicalUnit logicalUnit, storage_server_connections connection) {
        storage_server_connections entity = connection != null ? connection : new storage_server_connections();
        if (logicalUnit.isSetAddress()) {
            entity.setconnection(logicalUnit.getAddress());
        }
        if (logicalUnit.isSetTarget()) {
            entity.setiqn(logicalUnit.getTarget());
        }
        if (logicalUnit.isSetPort()) {
            entity.setport(logicalUnit.getPort().toString());
        }
        if (logicalUnit.isSetUsername()) {
            entity.setuser_name(logicalUnit.getUsername());
        }
        if (logicalUnit.isSetPassword()) {
            entity.setpassword(logicalUnit.getPassword());
        }
        return entity;
    }

    @Mapping(from = org.ovirt.engine.core.common.businessentities.LunStatus.class, to = LunStatus.class)
    public static LunStatus map(org.ovirt.engine.core.common.businessentities.LunStatus status, LunStatus template) {
        switch (status) {
        case Free:
            return LunStatus.Free;
        case Used:
            return LunStatus.Used;
        case Unusable:
            return LunStatus.Unusable;
        default:
            return null;
        }
    }
}
