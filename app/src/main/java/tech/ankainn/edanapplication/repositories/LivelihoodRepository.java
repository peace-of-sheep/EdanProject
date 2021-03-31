package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.db.DataCodesDao;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.model.app.master.DataEntity;
import tech.ankainn.edanapplication.model.app.master.MasterTypes;
import tech.ankainn.edanapplication.util.Utilities;

public class LivelihoodRepository {

    private static LivelihoodRepository instance;

    private AppExecutors appExecutors;
    private DataCodesDao dataCodesDao;
    private Cache cache;

    public static LivelihoodRepository getInstance(AppExecutors appExecutors, Cache cache, DataCodesDao dataCodesDao) {
        if(instance == null) {
            synchronized (LivelihoodRepository.class) {
                if(instance == null) {
                    instance = new LivelihoodRepository(appExecutors, cache, dataCodesDao);
                }
            }
        }
        return instance;
    }

    private LivelihoodRepository(AppExecutors appExecutors, Cache cache, DataCodesDao dataCodesDao) {
        this.appExecutors = appExecutors;
        this.cache = cache;
        this.dataCodesDao = dataCodesDao;
    }

    public LiveData<List<LivelihoodData>> loadLivelihoodDataList(long tempMemberId) {
        LiveData<FormTwoData> source = cache.getFormTwoData();
        return Transformations.map(source, formTwoData -> {
            if (formTwoData != null) {
                MemberData memberData = Utilities.findMemberDataByTempId(formTwoData, tempMemberId);
                if (memberData != null) {
                    return memberData.livelihoodDataList;
                }
            }
            return new ArrayList<>();
        });
    }

    public LivelihoodData loadLivelihoodData(long tempMemberId, long tempId) {
        if (tempId == 0L) {
            LivelihoodData livelihoodData = Utilities.createEmptyLivelihoodData();
            livelihoodData.tempMemberId = tempMemberId;
            return livelihoodData;
        } else {
            FormTwoData formTwoData = cache.getFormTwoData().getValue();
            if (formTwoData != null) {
                MemberData memberData = Utilities.findMemberDataByTempId(formTwoData, tempMemberId);
                if (memberData != null) {
                    LivelihoodData livelihoodData = Utilities.findLivelihoodDataByTempId(memberData, tempId);
                    return Utilities.clonePojo(livelihoodData);
                }
            }
            return null;
        }
    }

    public void saveLivelihoodData(LivelihoodData livelihoodData) {
        FormTwoData formTwoData = cache.getFormTwoData().getValue();
        if (formTwoData == null) return;

        MemberData memberData = Utilities.findMemberDataByTempId(formTwoData, livelihoodData.tempMemberId);
        if (memberData == null) return;

        List<LivelihoodData> copyList = new ArrayList<>(memberData.livelihoodDataList);

        if (livelihoodData.tempId == 0L) {
            memberData.livelihoodDataCount++;

            long tempCount = 0L;
            for (MemberData data : formTwoData.memberDataList) {
                tempCount += data.livelihoodDataCount;
            }

            livelihoodData.tempId = tempCount;

            copyList.add(livelihoodData);
        } else {
            for (int i = 0; i < copyList.size(); i++) {
                if (copyList.get(i).tempId == livelihoodData.tempId) {
                    copyList.set(i, livelihoodData);
                }
            }
        }
        memberData.livelihoodDataList = copyList;
        cache.setFormTwoData(formTwoData);
    }

    public LiveData<List<DataEntity>> loadLivelihoodNames() {
        MutableLiveData<List<DataEntity>> result = new MutableLiveData<>();
        appExecutors.diskIO().execute(() -> {
            result.postValue(dataCodesDao.loadDataCodesByType(MasterTypes.LIVELIHOODS));
        });
        return result;
    }

    public LiveData<List<DataEntity>> loadLivelihoodTypeNames(Integer ownerCode) {
        MutableLiveData<List<DataEntity>> result = new MutableLiveData<>();
        appExecutors.diskIO().execute(() -> {
            result.postValue(dataCodesDao.loadDataCodesByOwner(ownerCode));
        });
        return result;
    }
}
