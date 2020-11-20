package tech.ankainn.edanapplication.ui.formTwoA;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.repositories.MemberRepository;

public class ListMemberViewModel extends ViewModel {

    private MemberRepository memberRepository;

    private LiveData<List<MemberData>> listMemberData;

    private MemberData toDelete;

    public ListMemberViewModel(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;

        LiveData<List<MemberData>> source = memberRepository.loadListMemberData();
        listMemberData = Transformations.map(source, listMemberData -> {
            List<MemberData> toShow = new ArrayList<>();
            for (MemberData memberData : listMemberData) {
                if (!memberData.toRemove) {
                    toShow.add(memberData);
                }
            }
            return toShow;
        });
    }

    public LiveData<List<MemberData>> getListMemberData() {
        return listMemberData;
    }

    public void onRemoveItem(MemberData memberData) {
        memberRepository.removeMember(memberData);
    }

    public void setToDeleteMember(MemberData toDelete) {
        this.toDelete = toDelete;
    }
    public MemberData getToDeleteMember() {
        return toDelete;
    }
}
