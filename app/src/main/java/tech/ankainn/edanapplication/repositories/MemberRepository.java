package tech.ankainn.edanapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.AppExecutors;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.util.Utilities;

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
        return Transformations.map(source, formTwoData -> {
            if (formTwoData != null) {
                return formTwoData.memberDataList;
            }
            return new ArrayList<>();
        });
    }

    public MemberData loadMemberData(Long tempId) {
        if (tempId == 0L) {
            return Utilities.createEmptyMemberData();
        } else {
            FormTwoData formTwoData = cache.getFormTwoData().getValue();
            if (formTwoData != null) {
                List<MemberData> listMembers = formTwoData.memberDataList;
                for (MemberData memberData : listMembers) {
                    if (memberData.tempId == tempId) {
                        return Utilities.clonePojo(memberData);
                    }
                }
            }
            return null;
        }
    }

    public void saveMemberData(MemberData memberData) {
        FormTwoData formTwoData = cache.getFormTwoData().getValue();
        if (formTwoData != null) {
            List<MemberData> copyList = new ArrayList<>(formTwoData.memberDataList);

            if (memberData.tempId == 0L) {
                memberData.tempId = ++formTwoData.memberDataCount;
                copyList.add(memberData);
            } else {
                for (int i = 0; i < copyList.size(); i++) {
                    if (copyList.get(i).tempId == memberData.tempId) {
                        copyList.set(i, memberData);
                    }
                }
            }

            formTwoData.memberDataList = copyList;
            cache.setFormTwoData(formTwoData);
        }
    }

    public boolean checkHouseholdAffected() {
        FormTwoData formTwoData = cache.getFormTwoData().getValue();
        if (formTwoData != null) {
            return formTwoData.householdData.codeConditionHouse == 1;
        }
        return false;
    }
}
