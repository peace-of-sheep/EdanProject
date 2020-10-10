package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.db.EdanDatabase;
import tech.ankainn.edanapplication.db.FormOneDao;
import tech.ankainn.edanapplication.model.app.formOne.FormOneData;
import tech.ankainn.edanapplication.model.app.formOne.FormOneSubset;
import tech.ankainn.edanapplication.model.app.geninf.GenInfData;
import tech.ankainn.edanapplication.api.EdanApiService;
import tech.ankainn.edanapplication.model.factory.ModelFactory;
import tech.ankainn.edanapplication.util.Tagger;
import timber.log.Timber;

public class FormOneRepository {

    private static final LatLng defaultLatLng = new LatLng(-7.146,-75.009);

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

    public LiveData<List<FormOneSubset>> loadAllFormOneSubset() {
        return formOneDao.loadAllFormOneSubset();
    }

    public LiveData<FormOneData> loadCacheFormOneData() {
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

    private void createFormOneData() {
        FormOneData formOneData = ModelFactory.createEmptyFormOneData();

        GenInfData genInfData = cache.getGenInfData().getValue();
        if (genInfData == null) {
            formOneData.genInfData.mapLocationData.latitude = defaultLatLng.latitude;
            formOneData.genInfData.mapLocationData.longitude = defaultLatLng.longitude;

            cache.setGenInfData(formOneData.genInfData);
        } else {
            formOneData.genInfData = genInfData;
        }

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String dateCreation = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);
        String hourCreation = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);

        formOneData.genInfData.headerData.dateCreation = dateCreation;
        formOneData.genInfData.headerData.hourCreation = hourCreation;

        cache.setFormOneData(formOneData);
    }

    private void loadFormOneData(long formOneId) {
        appExecutors.diskIO().execute(() -> {
            FormOneData formOneData = formOneDao.loadFormOneById(formOneId);

            cache.setFormOneData(formOneData);
            cache.setGenInfData(formOneData.genInfData);
        });
    }

    public void saveFormOneData() {
        FormOneData formOneData = cache.getFormOneData().getValue();
        if (formOneData == null) {
            return;
        }

        appExecutors.diskIO().execute(() -> {
            formOneData.dataVersion++;

            Timber.tag(Tagger.DUMPER).d("FormOneRepository.saveForm: %s", new Gson().toJson(formOneData));

            if (formOneData.id == 0L) {
                formOneDao.insertFormOne(formOneData);
            } else {
                formOneDao.updateFormOne(formOneData);
            }
        });
    }

    public void clearCacheFormOneData() {
        cache.setFormOneData(null);
    }
}
