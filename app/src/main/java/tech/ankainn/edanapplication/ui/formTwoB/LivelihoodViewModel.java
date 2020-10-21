package tech.ankainn.edanapplication.ui.formTwoB;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;
import tech.ankainn.edanapplication.repositories.LivelihoodRepository;
import tech.ankainn.edanapplication.repositories.MemberRepository;

public class LivelihoodViewModel extends ViewModel {

    private LivelihoodRepository livelihoodRepository;

    private LiveData<String[]> nameMembers;
    private List<MemberData> currentMembers;

    private MutableLiveData<Integer> position = new MutableLiveData<>();
    private LiveData<List<LivelihoodData>> livelihoodDataList;

    private MutableLiveData<Pair<Long, Long>> tempIds = new MutableLiveData<>();
    private MediatorLiveData<LivelihoodData> livelihoodData = new MediatorLiveData<>();
    private LivelihoodData currentLivelihoodData;

    public LivelihoodViewModel(LivelihoodRepository livelihoodRepository, MemberRepository memberRepository) {
        this.livelihoodRepository = livelihoodRepository;

        LiveData<List<MemberData>> source = memberRepository.loadListMemberData();

        nameMembers = Transformations.map(source, memberDataList -> {
            this.currentMembers = memberDataList;

            String[] names = new String[memberDataList.size()];
            for (int i = 0; i < memberDataList.size(); i++) {
                String name = memberDataList.get(i).name + " " + memberDataList.get(i).surname;
                names[i] = name;
            }
            return names;
        });

        livelihoodDataList = Transformations.switchMap(position,
                pos -> livelihoodRepository.loadLivelihoodDataList(currentMembers.get(pos).tempId));

        livelihoodData.addSource(tempIds, pair -> {
            LivelihoodData livelihoodData = livelihoodRepository.loadLivelihoodData(pair.first, pair.second);

            if (livelihoodData != null)
                this.currentLivelihoodData = livelihoodData;
                this.livelihoodData.setValue(livelihoodData);
        });
    }

    public LiveData<String[]> getNameMembers() {
        return nameMembers;
    }

    public LiveData<List<LivelihoodData>> getLivelihoodDataList() {
        return livelihoodDataList;
    }

    public LiveData<LivelihoodData> getLivelihoodData() {
        return livelihoodData;
    }

    public void onOption(int position) {
        this.position.setValue(position);
    }

    public void loadLivelihoodData(long tempMemberId, long tempId) {
        this.tempIds.setValue(Pair.create(tempMemberId, tempId));
    }

    public long getCurrentMemberId() {
        Integer pos = position.getValue();
        if (pos != null) {
            return currentMembers.get(pos).tempId;
        }
        return -1;
    }

    public void saveLivelihoodData() {
        if (currentLivelihoodData != null) {
            currentLivelihoodData.dataVersion++;
            livelihoodRepository.saveLivelihoodData(currentLivelihoodData);
        }
    }
}
