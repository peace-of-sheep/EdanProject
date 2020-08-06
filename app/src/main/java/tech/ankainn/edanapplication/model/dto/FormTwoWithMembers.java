package tech.ankainn.edanapplication.model.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class FormTwoWithMembers {

    @Embedded
    public FormTwoEntity formTwoEntity;

    @Relation(parentColumn = "form_two_id",
            entityColumn = "form_two_owner_id")
    public List<MemberEntity> memberEntityList;
}
