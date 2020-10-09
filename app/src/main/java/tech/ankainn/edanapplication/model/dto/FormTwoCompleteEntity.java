package tech.ankainn.edanapplication.model.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class FormTwoCompleteEntity {

    @Embedded
    public FormTwoEntity formTwoEntity;

    @Relation(parentColumn = "form_two_id",
            entityColumn = "form_two_owner_id",
            entity = MemberEntity.class)
    public List<MemberEntity> memberEntityList;

    @Relation(parentColumn = "form_two_id",
            entityColumn = "form_two_owner_id",
            entity = LivelihoodEntity.class)
    public List<LivelihoodEntity> livelihoodEntityList;
}
