package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.model.factory.ModelFactory;

public class MemberRepository {

    private static MemberRepository instance;

    private AppExecutors appExecutors;
    private Cache cache;

    public static MemberRepository getInstance(Cache cache) {
        if(instance == null) {
            synchronized (MemberRepository.class) {
                if(instance == null) {
                    instance = new MemberRepository(cache);
                }
            }
        }
        return instance;
    }

    private MemberRepository(Cache cache) {
        this.appExecutors = appExecutors;
        this.cache = cache;
    }

    public LiveData<List<MemberData>> loadListMemberData() {
        LiveData<FormTwoData> source = cache.getFormTwoData();
        MediatorLiveData<List<MemberData>> result = new MediatorLiveData<>();
        result.addSource(source, formTwoData -> {
            if (formTwoData != null) {
                result.setValue(formTwoData.listMemberData);
            }
        });
        return result;
    }

    public LiveData<MemberData> loadMemberData(Long tempId) {
        if (tempId == 0L) {
            MemberData memberData = ModelFactory.createEmptyMemberData();
            return new MutableLiveData<>(memberData);
        } else {
            LiveData<FormTwoData> source = cache.getFormTwoData();
            return Transformations.map(source, formTwoData -> {
                if (formTwoData != null) {
                    List<MemberData> listMembers = formTwoData.listMemberData;
                    for (MemberData memberData : listMembers) {
                        if (memberData.tempId == tempId) {
                            return ModelFactory.clonePojo(memberData);
                        }
                    }
                }
                return null;
            });
        }
    }

    public void saveMemberData(MemberData memberData) {
        FormTwoData formTwoData = cache.getFormTwoData().getValue();
        if (formTwoData != null) {
            List<MemberData> copyList = new ArrayList<>(formTwoData.listMemberData);
            if (memberData.tempId == 0L) {
                copyList.add(memberData);
            } else {
                for (int i = 0; i < copyList.size(); i++) {
                    if (copyList.get(i).tempId == memberData.tempId) {
                        copyList.set(i, memberData);
                    }
                }
            }
            formTwoData.listMemberData = copyList;
            cache.setFormTwoData(formTwoData);
        }
    }
}
