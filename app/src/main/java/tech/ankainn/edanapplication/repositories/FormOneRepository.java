package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.db.EdanDatabase;
import tech.ankainn.edanapplication.db.FormOneDao;
import tech.ankainn.edanapplication.model.dto.FormOneEntity;
import tech.ankainn.edanapplication.model.factory.FormOneFactory;
import tech.ankainn.edanapplication.model.formOne.FormOneData;
import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;
import tech.ankainn.edanapplication.retrofit.Service;

public class FormOneRepository {

    private static final LatLng defaultLatLng = new LatLng(-7.146,-75.009);

    private static FormOneRepository instance;

    private AppExecutors appExecutors;
    private Service service;
    private FormOneDao formOneDao;
    private Cache cache;

    public static FormOneRepository getInstance(AppExecutors appExecutors,
                                                Service service,
                                                EdanDatabase edanDatabase,
                                                Cache cache) {
        if(instance == null) {
            synchronized (FormOneRepository.class) {
                if(instance == null) {
                    instance = new FormOneRepository(appExecutors, service, edanDatabase, cache);
                }
            }
        }
        return instance;
    }

    private FormOneRepository(AppExecutors appExecutors, Service service, EdanDatabase edanDatabase, Cache cache) {
        this.appExecutors = appExecutors;
        this.service = service;
        formOneDao = edanDatabase.formOneDao();
        this.cache = cache;
    }

    public LiveData<List<FormOneData>> getAllFormsFromDb() {
        LiveData<List<FormOneEntity>> rawData = formOneDao.getAllFormOne();
        return Transformations.map(rawData,
                listEntity -> listEntity == null || listEntity.isEmpty()
                        ? Collections.emptyList()
                        : FormOneFactory.createDataListFromEntityList(listEntity));
    }

    public LiveData<FormOneData> getCurrentFormOneData() {
        return cache.getFormOneData();
    }

    public void loadFormOneDataById(long formOneId) {
        if(cache.getFormOneData().getValue() != null)
            return;

        if (formOneId == 0L) {
            createFormOneData();
        } else {
            loadFormOneData(formOneId);
        }
    }

    public void saveCurrentFormOneData() {
        FormOneData formOneData = cache.getFormOneData().getValue();
        if (formOneData == null) {
            return;
        }
        appExecutors.diskIO().execute(() -> {
            formOneData.dataVersion++;

            FormOneEntity entity = FormOneFactory.dataToEntity(formOneData);

            if (entity.formOneId == 0L) {
                formOneDao.insertFormOne(entity);
            } else {
                formOneDao.updateFormOne(entity);
            }
        });
    }

    public void clearCurrentFormOneData() {
        cache.setFormOneData(null);
    }

    private void createFormOneData() {
        FormOneData formOneData = FormOneFactory.createEmptyFormOneData();

        MapLocationData mapLocationData = cache.getMapLocationData().getValue();
        if (mapLocationData != null) {
            formOneData.mapLocationData = mapLocationData;
        } else {
            formOneData.mapLocationData.latitude = defaultLatLng.latitude;
            formOneData.mapLocationData.longitude = defaultLatLng.longitude;
            cache.setMapLocationData(formOneData.mapLocationData);
        }

        GenInfData genInfData = cache.getGenInfData().getValue();
        if (genInfData != null) {
            formOneData.genInfData = genInfData;
        } else {
            cache.setGenInfData(formOneData.genInfData);
        }

        cache.setFormOneData(formOneData);
    }

    private void loadFormOneData(long formOneId) {
        appExecutors.diskIO().execute(() -> {
            FormOneEntity formOneEntity = formOneDao.loadFormOneById(formOneId);
            FormOneData formOneData = FormOneFactory.entityToData(formOneEntity);

            cache.setFormOneData(formOneData);
            cache.setMapLocationData(formOneData.mapLocationData);
            cache.setGenInfData(formOneData.genInfData);
        });
    }
}