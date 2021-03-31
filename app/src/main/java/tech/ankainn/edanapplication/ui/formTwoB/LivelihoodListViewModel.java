package tech.ankainn.edanapplication.ui.formTwoB;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.repositories.LivelihoodRepository;
import tech.ankainn.edanapplication.repositories.MemberRepository;

public class LivelihoodListViewModel extends ViewModel {

    private LiveData<List<MemberData>> membersList;
    private MutableLiveData<MemberData> currentMember = new MutableLiveData<>();

    private LiveData<List<LivelihoodData>> livelihoodDataList;

    private long tempMemberId;

    public LivelihoodListViewModel(LivelihoodRepository livelihoodRepository, MemberRepository memberRepository) {

        LiveData<List<MemberData>> membersSource = memberRepository.loadListMemberData();
        membersList = Transformations.map(membersSource, list -> {
            List<MemberData> listToShow = new ArrayList<>();
            for (MemberData memberData : list) {
                if (!memberData.toRemove && memberData.livelihoodOwner) {
                    listToShow.add(memberData);
                }
            }
            return listToShow;
        });

        livelihoodDataList = Transformations.switchMap(currentMember,
                memberData -> livelihoodRepository.loadLivelihoodDataList(memberData.tempId));
    }

    public LiveData<List<MemberData>> getMembersList() {
        return membersList;
    }

    public LiveData<List<LivelihoodData>> getLivelihoodDataList() {
        return livelihoodDataList;
    }

    public void onMember(MemberData memberData) {
        currentMember.setValue(memberData);
    }

    public long getMemberId() {
        MemberData current = currentMember.getValue();
        return current != null ? current.tempId : 0L;
    }
}
