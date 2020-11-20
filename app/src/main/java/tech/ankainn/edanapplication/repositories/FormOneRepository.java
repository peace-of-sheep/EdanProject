package tech.ankainn.edanapplication.repositories;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.db.EdanDatabase;
import tech.ankainn.edanapplication.db.FormOneDao;
import tech.ankainn.edanapplication.model.app.formOne.FormOneData;
import tech.ankainn.edanapplication.model.dto.FormOneSubset;
import tech.ankainn.edanapplication.api.EdanApiService;
import tech.ankainn.edanapplication.util.Utilities;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class FormOneRepository {

    private static final LatLng defaultLatLng = new LatLng(-11.37621439,-75.41892405);

    private static FormOneRepository instance;

    private AppExecutors appExecutors;
    private EdanApiService service;
    private FormOneDao formOneDao;
    private Cache cache;

    public static FormOneRepository getInstance(AppExecutors appExecutors,
                                                EdanApiService service,
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

    private FormOneRepository(AppExecutors appExecutors, EdanApiService service,
                              EdanDatabase edanDatabase, Cache cache) {
        this.appExecutors = appExecutors;
        this.service = service;
        formOneDao = edanDatabase.formOneDao();
        this.cache = cache;
    }

    public LiveData<List<FormOneSubset>> loadAllFormOneSubset(long userId) {
        return formOneDao.loadAllFormOneSubset(userId);
    }

    public LiveData<FormOneData> loadCacheFormOneData() {
        return cache.getFormOneData();
    }

    public void loadFormOneDataById(long formOneId, long userId, String username) {
        FormOneData oldValue = cache.getFormOneData().getValue();
        if(oldValue != null && oldValue.id == formOneId)
            return;

            /*if (formOneId == 0L) {
                createFormOneData(userId);
            } else {
                loadFormOneData(formOneId);
            }*/

        appExecutors.diskIO().execute(() -> {

            FormOneData formOneData = formOneId == 0L ? createFormOneData(userId, username) : loadFormOneData(formOneId);

            cache.setFormOneData(formOneData);
            cache.setGenInfData(formOneData.genInfData);
        });
    }

    @WorkerThread
    private FormOneData createFormOneData(long userId, String username) {
        FormOneData formOneData = Utilities.createEmptyFormOneData();

        formOneData.ownerUserId = userId;
        formOneData.username = username;

        /*GenInfData genInfData = cache.getGenInfData().getValue();
        if (genInfData == null) {
            formOneData.genInfData.mapLocationData.latitude = defaultLatLng.latitude;
            formOneData.genInfData.mapLocationData.longitude = defaultLatLng.longitude;

            cache.setGenInfData(formOneData.genInfData);
        } else {
            formOneData.genInfData = genInfData;
        }*/
        formOneData.genInfData.mapLocationData.latitude = defaultLatLng.latitude;
        formOneData.genInfData.mapLocationData.longitude = defaultLatLng.longitude;

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String dateCreation = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);
        String hourCreation = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);

        formOneData.genInfData.headerData.dateEvent = dateCreation;
        formOneData.genInfData.headerData.hourEvent = hourCreation;

        formOneData.genInfData.headerData.dateCreation = dateCreation;
        formOneData.genInfData.headerData.hourCreation = hourCreation;

        /*cache.setFormOneData(formOneData);*/

        Timber.tag(Tagger.DATA_FLOW).i("FormOneRepository.createFormOneData: %s", formOneData);

        return formOneData;
    }

    @WorkerThread
    private FormOneData loadFormOneData(long formOneId) {
        FormOneData formOneData = formOneDao.loadFormOneById(formOneId);
        Timber.tag(Tagger.DATA_FLOW).i("FormOneRepository.loadFormOneData: %s", formOneData);

        /*cache.setFormOneData(formOneData);
            cache.setGenInfData(formOneData.genInfData);*/
        return formOneData;
    }

    public void saveFormOneData() {
        FormOneData formOneData = cache.getFormOneData().getValue();
        if (formOneData == null) {
            return;
        }

        appExecutors.diskIO().execute(() -> {
            formOneData.dataVersion++;

            Timber.tag(Tagger.DATA_FLOW).i("FormOneRepository.saveForm: %s", formOneData);

            if (formOneData.id == 0L) {
                formOneDao.insertFormOne(formOneData);
            } else {
                formOneDao.updateFormOne(formOneData);
            }
        });
    }

    public void clearCacheFormOneData() {
        cache.setFormOneData(null);
        cache.setGenInfData(null);
    }
}
