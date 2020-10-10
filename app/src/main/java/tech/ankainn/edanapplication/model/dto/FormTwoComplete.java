package tech.ankainn.edanapplication.model.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;

public class FormTwoComplete {

    @Embedded
    public FormTwoData formTwoData;

    @Relation(parentColumn = "form_two_id",
            entityColumn = "form_two_owner_id",
            entity = MemberData.class)
    public List<MemberData> memberDataList;

    @Relation(parentColumn = "form_two_id",
            entityColumn = "form_two_owner_id",
            entity = LivelihoodData.class)
    public List<LivelihoodData> livelihoodDataList;

    @NotNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static FormTwoComplete create(FormTwoData formTwoData,
                                         List<MemberData> memberDataList,
                                         List<LivelihoodData> livelihoodDataList) {
        FormTwoComplete formTwoComplete = new FormTwoComplete();
        formTwoComplete.formTwoData = formTwoData;
        formTwoComplete.memberDataList = memberDataList;
        formTwoComplete.livelihoodDataList = livelihoodDataList;
        return formTwoComplete;
    }
}
