package tech.ankainn.edanapplication.model.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class FormTwoWithMembers {

    @Embedded
    public FormTwoEntity formTwoEntity;

    @Relation(parentColumn = "form_two_id",
            entityColumn = "member_id",
            entity = MemberEntity.class)
    public List<MemberEntity> memberEntityList;
}
