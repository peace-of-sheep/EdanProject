package tech.ankainn.edanapplication.model.factory;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.binding.Converter;
import tech.ankainn.edanapplication.model.api.formtwo.DocumentRemote;
import tech.ankainn.edanapplication.model.api.formtwo.FamiliesRemote;
import tech.ankainn.edanapplication.model.api.formtwo.FamilyRemote;
import tech.ankainn.edanapplication.model.api.formtwo.FormTwoHeaderRemote;
import tech.ankainn.edanapplication.model.api.formtwo.FormTwoRemote;
import tech.ankainn.edanapplication.model.api.formtwo.HouseholdRemote;
import tech.ankainn.edanapplication.model.api.formtwo.InformationRemote;
import tech.ankainn.edanapplication.model.api.formtwo.MaterialTypeRemote;
import tech.ankainn.edanapplication.model.api.formtwo.ResponsableRemote;
import tech.ankainn.edanapplication.model.app.formTwo.HouseholdData;
import tech.ankainn.edanapplication.model.app.geninf.ExtraData;
import tech.ankainn.edanapplication.model.app.geninf.MapLocationData;
import tech.ankainn.edanapplication.model.dto.FormTwoCompleteEntity;
import tech.ankainn.edanapplication.model.dto.FormTwoEntity;
import tech.ankainn.edanapplication.model.dto.HeaderEntity;
import tech.ankainn.edanapplication.model.dto.LivelihoodEntity;
import tech.ankainn.edanapplication.model.dto.MemberEntity;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.geninf.GenInfData;
import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;

public class FormTwoFactory {

    // util
    public static <T> T copyObject(T object){
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(object), (Type) object.getClass());
    }

    public static MemberData cloneMemberData(MemberData memberData) {
        return copyObject(memberData);
    }

    public static LivelihoodData cloneLivelihoodData(LivelihoodData livelihoodData) {
        return copyObject(livelihoodData);
    }

    public static FormTwoData createEmptyFormTwoData() {
        return new FormTwoData();
    }

    public static MemberData createEmptyMemberData() {
        return new MemberData();
    }

    public static LivelihoodData createEmptyLivelihoodData() {
        LivelihoodData livelihoodData = new LivelihoodData();
        livelihoodData.dataVersion = 0;

        return livelihoodData;
    }

    public static MemberData memberDataFromEntity(MemberEntity memberEntity) {
        MemberData memberData = new MemberData();

        memberData.id = memberEntity.memberId;
        memberData.formTwoOwnerId = memberEntity.formTwoOwnerId;

        memberData.dataVersion = memberEntity.dataVersion;

        memberData.typeIdentification = memberEntity.typeIdentification;
        memberData.codeIdentification = memberEntity.codeIdentification;
        memberData.textIdentification = memberEntity.textIdentification;
        memberData.surname = memberEntity.surname;
        memberData.name = memberEntity.name;
        memberData.birthDate = memberEntity.birthdate;
        memberData.age = memberEntity.age;
        memberData.gender = memberEntity.gender;
        memberData.codeGender = memberEntity.codeGender;
        memberData.condition = memberEntity.condition;
        memberData.codeCondition = memberEntity.codeCondition;
        memberData.personalInjury = memberEntity.personalInjury;
        memberData.codePersonalInjury = memberEntity.codePersonalInjury;

        return memberData;
    }

    public static MemberEntity dataToEntity(MemberData memberData) {
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.memberId = memberData.id;
        memberEntity.formTwoOwnerId = memberData.formTwoOwnerId;

        memberEntity.dataVersion = memberData.dataVersion;

        memberEntity.typeIdentification = memberData.typeIdentification;
        memberEntity.codeIdentification = memberData.codeIdentification;
        memberEntity.textIdentification = memberData.textIdentification;

        memberEntity.surname = memberData.surname;
        memberEntity.name = memberData.name;
        memberEntity.birthdate = memberData.birthDate;
        memberEntity.age = memberData.age;
        memberEntity.gender = memberData.gender;
        memberEntity.codeGender = memberData.codeGender;

        memberEntity.condition = memberData.condition;
        memberEntity.codeCondition = memberData.codeCondition;
        memberEntity.personalInjury = memberData.personalInjury;
        memberEntity.codePersonalInjury = memberData.codePersonalInjury;

        return memberEntity;
    }

    public static FormTwoData dataFromEntity(FormTwoEntity formTwoEntity) {
        FormTwoData formTwoData = new FormTwoData();

        formTwoData.id = formTwoEntity.formTwoId;
        formTwoData.formTwoApiId = formTwoEntity.formTwoApiId;

        formTwoData.dataVersion = formTwoEntity.dataVersion;

        formTwoData.genInfData = GenInfFactory.genInfDataFromEntity(formTwoEntity);

        HouseholdData householdData = formTwoData.householdData;
        householdData.lot = formTwoEntity.lot;
        householdData.owner = formTwoEntity.owner;
        householdData.useHouse = formTwoEntity.useHouse;
        householdData.codeUseHouse = formTwoEntity.codeUseHouse;
        householdData.conditionHouse = formTwoEntity.conditionHouse;
        householdData.codeConditionHouse = formTwoEntity.codeConditionHouse;

        householdData.typeRoof = formTwoEntity.typeRoof;
        householdData.codeRoof = formTwoEntity.codeRoof;
        householdData.typeWall = formTwoEntity.typeWall;
        householdData.codeWall = formTwoEntity.codeWall;
        householdData.typeFloor = formTwoEntity.typeFloor;
        householdData.codeFloor = formTwoEntity.codeFloor;

        return formTwoData;
    }

    public static FormTwoData dataFromEntityComplete(FormTwoCompleteEntity source) {

        FormTwoData formTwoData = dataFromEntity(source.formTwoEntity);

        List<MemberData> tempMembers = new ArrayList<>();
        for (MemberEntity memberEntity : source.memberEntityList) {
            MemberData memberData = memberDataFromEntity(memberEntity);
            tempMembers.add(memberData);
        }

        if(tempMembers.size() != 0)
            formTwoData.listMemberData = tempMembers;

        /*List<LivelihoodData> tempLivelihoods = new ArrayList<>();
        for (LivelihoodEntity livelihoodEntity : source.livelihoodEntityList) {
            LivelihoodData livelihoodData = entityToData(livelihoodEntity);
            tempLivelihoods.add(livelihoodData);
        }

        if(tempLivelihoods.size() != 0)
            formTwoData.listLivelihood = tempLivelihoods;*/

        return formTwoData;
    }

    public static LivelihoodData entityToData(LivelihoodEntity source) {
        LivelihoodData livelihoodData = new LivelihoodData();

        livelihoodData.id = source.livelihoodId;

        livelihoodData.formTwoOwnerId = source.formTwoOwnerId;

        livelihoodData.dataVersion = source.dataVersion;

        livelihoodData.name = source.name;
        livelihoodData.type = source.type;
        livelihoodData.amountLost = source.amountLost;
        livelihoodData.amountAffected = source.amountAffected;

        return livelihoodData;
    }

    public static FormTwoEntity dataToEntity(FormTwoData formTwoData) {
        FormTwoEntity formTwoEntity = new FormTwoEntity();

        formTwoEntity.formTwoId = formTwoData.id;

        formTwoEntity.dataVersion = formTwoData.dataVersion;

        formTwoEntity.formTwoApiId = formTwoData.formTwoApiId;

        GenInfData genInfData = formTwoData.genInfData;
        formTwoEntity.headerEntity = GenInfFactory.headerDataToEntity(genInfData.headerData);

        ExtraData extraData = genInfData.extraData;
        formTwoEntity.locality = extraData.nameLocality;
        formTwoEntity.codeLocality = extraData.codeLocality;

        formTwoEntity.typeBSU = extraData.typeBSU;
        formTwoEntity.codeBSU = extraData.codeBSU;
        formTwoEntity.textBSU = extraData.nameBSU;

        formTwoEntity.typeCM = extraData.typeCM;
        formTwoEntity.codeCM = extraData.codeCM;
        formTwoEntity.textCM = extraData.nameCM;

        formTwoEntity.typeCA = extraData.typeCA;
        formTwoEntity.codeCA = extraData.codeCA;
        formTwoEntity.textCA = extraData.nameCA;

        formTwoEntity.textEPD = extraData.nameEPD;

        MapLocationData mapLocationData = genInfData.mapLocationData;
        formTwoEntity.latitude = mapLocationData.latitude;
        formTwoEntity.longitude = mapLocationData.longitude;

        HouseholdData householdData = formTwoData.householdData;

        formTwoEntity.lot = householdData.lot;
        formTwoEntity.owner = householdData.owner;
        formTwoEntity.useHouse = householdData.useHouse;
        formTwoEntity.codeUseHouse = householdData.codeUseHouse;
        formTwoEntity.conditionHouse = householdData.conditionHouse;
        formTwoEntity.codeConditionHouse = householdData.codeConditionHouse;

        formTwoEntity.typeFloor = householdData.typeFloor;
        formTwoEntity.typeWall = householdData.typeWall;
        formTwoEntity.typeRoof = householdData.typeRoof;
        formTwoEntity.codeFloor = householdData.codeFloor;
        formTwoEntity.codeWall = householdData.codeWall;
        formTwoEntity.codeRoof = householdData.codeRoof;

        return formTwoEntity;
    }

    public static LivelihoodEntity dataToEntity(LivelihoodData livelihoodData) {
        LivelihoodEntity livelihoodEntity = new LivelihoodEntity();

        livelihoodEntity.livelihoodId = livelihoodData.id;
        livelihoodEntity.formTwoOwnerId = livelihoodData.formTwoOwnerId;
        livelihoodEntity.dataVersion = livelihoodData.dataVersion;
        livelihoodEntity.name = livelihoodData.name;
        livelihoodEntity.type = livelihoodData.type;
        livelihoodEntity.amountLost = livelihoodData.amountLost;
        livelihoodEntity.amountAffected = livelihoodData.amountAffected;

        return livelihoodEntity;
    }

    public static FormTwoRemote dataToRemote(FormTwoData formTwoData) {
        FormTwoRemote formTwoRemote = new FormTwoRemote();

        return formTwoRemote;
    }

    public static FormTwoRemote completeEntityToRemote(FormTwoCompleteEntity formTwoCompleteEntity) {
        FormTwoRemote formTwoRemote = new FormTwoRemote();

        FormTwoEntity entity = formTwoCompleteEntity.formTwoEntity;
        HeaderEntity headerEntity = entity.headerEntity;

        //***********************************************
        FormTwoHeaderRemote headerRemote = new FormTwoHeaderRemote();
        headerRemote.setPeligroTipo(headerEntity.codeGroupDanger);
        headerRemote.setEvaluacionNro(-1);

        String eventDateTime = headerEntity.dateEvent + " " + headerEntity.hourEvent;
        String creationDateTime = headerEntity.dateCreation + " " + headerEntity.hourCreation;

        headerRemote.setOcurrenciaFechaHora(eventDateTime);
        headerRemote.setEmpadronamientoFechaHora(creationDateTime);
        headerRemote.setCentroPoblado(entity.locality);
        headerRemote.setLocalidad(entity.locality);
        headerRemote.setCaserio(entity.textCA);
        headerRemote.setCalle(entity.textCM);
        headerRemote.setPiso(Converter.stringToInteger(entity.textEPD));

        formTwoRemote.setForm2aCab(headerRemote);

        //***********************************************
        HouseholdRemote householdRemote = new HouseholdRemote();
        householdRemote.setCondicionViviendaPostDesastre(entity.codeConditionHouse);
        householdRemote.setCondicionUsoInstalacion(entity.codeUseHouse);
        householdRemote.setLoteNro(entity.lot);
        householdRemote.setTenenciaPropia(entity.owner ? "S" : "N");

        MaterialTypeRemote materialTypeRemote = new MaterialTypeRemote();
        materialTypeRemote.setTecho(entity.codeRoof);
        materialTypeRemote.setPared(entity.codeWall);
        materialTypeRemote.setPiso(entity.codeFloor);

        householdRemote.setTipoMaterialVivienda(materialTypeRemote);

        formTwoRemote.setInformacionVivienda(householdRemote);

        //************************************************
        formTwoRemote.setFamilias(new FamiliesRemote());
        formTwoRemote.getFamilias().setFamily(new FamilyRemote());
        formTwoRemote.getFamilias().getFamily().setInformacionRegular(new InformationRemote());

        ResponsableRemote responsableRemote = new ResponsableRemote();

        List<MemberEntity> list = formTwoCompleteEntity.memberEntityList;
        if (list != null && list.size() > 0) {
            MemberEntity memberEntity = list.get(0);

            responsableRemote.setApellidos(memberEntity.surname);
            responsableRemote.setNombres(memberEntity.name);
            responsableRemote.setEdad(Integer.toString(memberEntity.age));

            DocumentRemote documentRemote = new DocumentRemote();
            documentRemote.setNro(Integer.toString(memberEntity.textIdentification));
            documentRemote.setTipo(Integer.toString(memberEntity.codeIdentification));

            responsableRemote.setDocumento(documentRemote);

        }

        formTwoRemote.getFamilias().getFamily().getInformacionRegular().setResponsable(responsableRemote);

        return formTwoRemote;
    }
}
