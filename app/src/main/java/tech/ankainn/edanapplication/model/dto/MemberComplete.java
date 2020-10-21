package tech.ankainn.edanapplication.model.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;

public class MemberComplete {

    @Embedded
    public MemberData memberData;

    @Relation(parentColumn = "member_id",
            entityColumn = "member_owner_id",
            entity = LivelihoodData.class)
    public List<LivelihoodData> livelihoodDataList;
}
