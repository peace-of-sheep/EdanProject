package tech.ankainn.edanapplication.model.factory;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.dto.FormOneEntity;
import tech.ankainn.edanapplication.model.app.formOne.FormOneData;
import tech.ankainn.edanapplication.model.app.formOne.SelectableData;
import tech.ankainn.edanapplication.model.app.formOne.SelectableItemData;

public class FormOneFactory {

    public static FormOneData createEmptyFormOneData() {
        FormOneData formOneData = new FormOneData();

        formOneData.dataVersion = 0;

        formOneData.genInfData = GenInfFactory.createEmptyGenInf();

        formOneData.damageOne = new SelectableData(createSelectableList(false, 0, 5), null);
        formOneData.damageTwo = new SelectableData(createSelectableList(false, null, 5), null);
        formOneData.damageThree = new SelectableData(createSelectableList(false, null, 4), null);

        formOneData.activities = new SelectableData(createSelectableList(false, null, 5), "");
        formOneData.needs = new SelectableData(createSelectableList(false, null, 3), "");

        return formOneData;
    }

    private static List<SelectableItemData> createSelectableList(Boolean selection, Integer quantity, int number) {
        List<SelectableItemData> result = new ArrayList<>();
        for (int i = 0; i < number ; i++) {
            result.add(new SelectableItemData(selection, quantity));
        }
        return result;
    }

    public static FormOneData entityToData(FormOneEntity entity) {
        FormOneData result = createEmptyFormOneData();

        result.id = entity.formOneId;
        result.dataVersion = entity.dataVersion;

        result.mapLocationData = GenInfFactory.createMapLocationFromEntity(entity);
        //result.genInfData = GenInfFactory.createGenInfFromEntity(entity);

        result.damageOne = createSelectableDataFromEntity(entity.damageOneQuantity, entity.damageOneBool, null);
        result.damageTwo = createSelectableDataFromEntity(null, entity.damageTwoBool, null);
        result.damageThree = createSelectableDataFromEntity(null, entity.damageThreeBool, null);

        result.activities = createSelectableDataFromEntity(null, entity.activitiesBool, entity.activitiesObservation);
        result.needs = createSelectableDataFromEntity(null, entity.needsBool, entity.needsObservation);

        return result;
    }

    public static FormOneEntity dataToEntity(FormOneData data) {
        FormOneEntity entity = new FormOneEntity();

        entity.formOneId = data.id;
        entity.dataVersion = data.dataVersion;

        GenInfFactory.passDataToEntity(entity, data.mapLocationData);
        GenInfFactory.passDataToEntity(entity, data.genInfData);

        entity.damageOneQuantity = getIntsFromSelectableData(data.damageOne);
        entity.damageOneBool = getBoolFromSelectableData(data.damageOne);
        entity.damageTwoBool = getBoolFromSelectableData(data.damageTwo);
        entity.damageThreeBool = getBoolFromSelectableData(data.damageThree);

        entity.activitiesBool = getBoolFromSelectableData(data.activities);
        entity.activitiesObservation = data.activities.observation;

        entity.needsBool = getBoolFromSelectableData(data.needs);
        entity.needsObservation = data.needs.observation;

        return entity;
    }

    private static SelectableData createSelectableDataFromEntity(List<Integer> quantities, List<Boolean> booleans, String observation) {
        List<SelectableItemData> list = new ArrayList<>();

        int n = quantities == null ? booleans.size() : quantities.size();
        for (int i = 0; i < n; i++) {
            SelectableItemData itemData = new SelectableItemData(null, null);
            if (quantities != null) {
                itemData.quantity = quantities.get(i);
            }
            if (booleans != null) {
                itemData.selection = booleans.get(i);
            }
            list.add(itemData);
        }

        return new SelectableData(list, observation);
    }

    private static List<Boolean> getBoolFromSelectableData(SelectableData data) {
        List<Boolean> result = new ArrayList<>();
        for (SelectableItemData itemData : data.list) {
            result.add(itemData.selection);
        }
        return result;
    }

    private static List<Integer> getIntsFromSelectableData(SelectableData data) {
        List<Integer> result = new ArrayList<>();
        for (SelectableItemData itemData : data.list) {
            result.add(itemData.quantity);
        }
        return result;
    }

    public static List<FormOneData> createDataListFromEntityList(List<FormOneEntity> listEntity) {
        List<FormOneData> result = new ArrayList<>();
        for (FormOneEntity entity : listEntity) {
            FormOneData formOneData = entityToData(entity);
            result.add(formOneData);
        }
        return result;
    }
}
