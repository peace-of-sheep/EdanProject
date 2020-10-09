package tech.ankainn.edanapplication.model.factory;

import tech.ankainn.edanapplication.model.app.geninf.ExtraData;
import tech.ankainn.edanapplication.model.app.geninf.HeaderData;
import tech.ankainn.edanapplication.model.dto.FormOneEntity;
import tech.ankainn.edanapplication.model.app.geninf.GenInfData;
import tech.ankainn.edanapplication.model.app.geninf.MapLocationData;
import tech.ankainn.edanapplication.model.dto.FormTwoEntity;
import tech.ankainn.edanapplication.model.dto.HeaderEntity;

public class GenInfFactory {

    public static GenInfData createEmptyGenInf() {
        return new GenInfData();
    }

    public static MapLocationData createMapLocationFromEntity(FormOneEntity entity) {
        MapLocationData data = new MapLocationData();
        data.latitude = entity.latitude;
        data.longitude = entity.longitude;
        data.altitude = Double.valueOf(entity.altitude);
        data.transport = entity.transport;
        data.reference = entity.reference;

        return data;
    }

    public static GenInfData genInfDataFromEntity(FormOneEntity entity) {
        GenInfData data = new GenInfData();

        return data;
    }

    public static GenInfData genInfDataFromEntity(FormTwoEntity entity) {
        GenInfData data = new GenInfData();
        data.headerData = headerDataFromEntity(entity.headerEntity);

        ExtraData extraData = data.extraData;
        extraData.nameLocality = entity.locality;
        extraData.codeLocality = entity.codeLocality;
        extraData.typeBSU = entity.typeBSU;
        extraData.codeBSU = entity.codeBSU;
        extraData.nameBSU = entity.textBSU;

        extraData.typeCM = entity.typeCM;
        extraData.codeCM = entity.codeCM;
        extraData.nameCM = entity.textCM;

        extraData.typeCA = entity.typeCA;
        extraData.codeCA = entity.codeCA;
        extraData.nameCA = entity.textCA;

        extraData.nameEPD = entity.textEPD;

        MapLocationData mapLocationData = data.mapLocationData;
        mapLocationData.latitude = entity.latitude;
        mapLocationData.longitude = entity.longitude;

        return data;
    }

    public static HeaderData headerDataFromEntity(HeaderEntity headerEntity) {
        HeaderData headerData = new HeaderData();

        headerData.groupDanger = headerEntity.groupDanger;
        headerData.codeGroupDanger = headerEntity.codeGroupDanger;
        headerData.danger = headerEntity.typeDanger;
        headerData.codeDanger = headerEntity.codeTypeDanger;

        headerData.department = headerEntity.department;
        headerData.codeDepartment = headerEntity.codeDepartment;
        headerData.province = headerEntity.province;
        headerData.codeProvince = headerEntity.codeProvince;
        headerData.district = headerEntity.district;
        headerData.codeDistrict = headerEntity.codeDistrict;

        headerData.dateEvent = headerEntity.dateEvent;
        headerData.hourEvent = headerEntity.hourEvent;
        headerData.dateCreation = headerEntity.dateCreation;
        headerData.hourCreation = headerEntity.hourCreation;

        return headerData;
    }

    public static HeaderEntity headerDataToEntity(HeaderData headerData) {
        HeaderEntity headerEntity = new HeaderEntity();

        headerEntity.groupDanger = headerData.groupDanger;
        headerEntity.codeGroupDanger = headerData.codeGroupDanger;
        headerEntity.typeDanger = headerData.danger;
        headerEntity.codeTypeDanger = headerData.codeDanger;

        headerEntity.department = headerData.department;
        headerEntity.codeDepartment = headerData.codeDepartment;
        headerEntity.province = headerData.province;
        headerEntity.codeProvince = headerData.codeProvince;
        headerEntity.district = headerData.district;
        headerEntity.codeDistrict = headerData.codeDistrict;

        headerEntity.dateEvent = headerData.dateEvent;
        headerEntity.hourEvent = headerData.hourEvent;
        headerEntity.dateCreation = headerData.dateCreation;
        headerEntity.hourCreation = headerData.hourCreation;

        return headerEntity;
    }

    public static void passDataToEntity(FormOneEntity target, MapLocationData data) {
        /*target.latitude = data.latitude;
        target.longitude = data.longitude;
        target.altitude = data.altitude;
        target.transport = data.transport;
        target.reference = data.reference;*/
    }

    public static void passDataToEntity(FormOneEntity target, GenInfData data) {
        /*target.groupDanger = data.groupDanger;
        target.typeDanger = data.typeDanger;
        target.date = data.date;
        target.hour = data.hour;
        target.department = data.department;
        target.province = data.province;
        target.district = data.district;
        target.locality = data.locality;
        target.zone = data.zone;*/
    }
}
