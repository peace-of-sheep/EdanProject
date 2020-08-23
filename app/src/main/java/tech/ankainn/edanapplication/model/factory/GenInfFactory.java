package tech.ankainn.edanapplication.model.factory;

import tech.ankainn.edanapplication.model.dto.FormOneEntity;
import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;

public class GenInfFactory {

    public static MapLocationData createEmptyMapLocation() {
        MapLocationData mapLocationData = new MapLocationData();
        mapLocationData.longitude = 0d;
        mapLocationData.latitude = 0d;
        mapLocationData.altitude = 0;
        mapLocationData.reference = "";
        mapLocationData.transport = "";

        return mapLocationData;
    }

    public static GenInfData createEmptyGenInf() {
        GenInfData genInfData = new GenInfData();
        genInfData.groupDanger = "";
        genInfData.typeDanger = "";
        genInfData.date = "";
        genInfData.hour = "";
        genInfData.department = "";
        genInfData.province = "";
        genInfData.district = "";
        genInfData.locality = "";
        genInfData.zone = "";

        return genInfData;
    }

    public static MapLocationData createMapLocationFromEntity(FormOneEntity entity) {
        MapLocationData data = new MapLocationData();
        data.latitude = entity.latitude;
        data.longitude = entity.longitude;
        data.altitude = entity.altitude;
        data.transport = entity.transport;
        data.reference = entity.reference;

        return data;
    }

    public static GenInfData createGenInfFromEntity(FormOneEntity entity) {
        GenInfData data = new GenInfData();

        data.groupDanger = entity.groupDanger;
        data.typeDanger = entity.typeDanger;
        data.date = entity.date;
        data.hour = entity.hour;
        data.department = entity.department;
        data.province = entity.province;
        data.district = entity.district;
        data.locality = entity.locality;
        data.zone = entity.zone;

        return data;
    }

    public static void passDataToEntity(FormOneEntity target, MapLocationData data) {
        target.latitude = data.latitude;
        target.longitude = data.longitude;
        target.altitude = data.altitude;
        target.transport = data.transport;
        target.reference = data.reference;
    }

    public static void passDataToEntity(FormOneEntity target, GenInfData data) {
        target.groupDanger = data.groupDanger;
        target.typeDanger = data.typeDanger;
        target.date = data.date;
        target.hour = data.hour;
        target.department = data.department;
        target.province = data.province;
        target.district = data.district;
        target.locality = data.locality;
        target.zone = data.zone;
    }
}
