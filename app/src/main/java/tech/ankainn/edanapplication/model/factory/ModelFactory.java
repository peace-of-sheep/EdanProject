package tech.ankainn.edanapplication.model.factory;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.binding.Converter;
import tech.ankainn.edanapplication.model.api.auth.ApiUser;
import tech.ankainn.edanapplication.model.api.auth.AuthResponse;
import tech.ankainn.edanapplication.model.api.formtwo.DocumentRemote;
import tech.ankainn.edanapplication.model.api.formtwo.FamiliesRemote;
import tech.ankainn.edanapplication.model.api.formtwo.FamilyRemote;
import tech.ankainn.edanapplication.model.api.formtwo.FormTwoHeaderRemote;
import tech.ankainn.edanapplication.model.api.formtwo.FormTwoRemote;
import tech.ankainn.edanapplication.model.api.formtwo.HouseholdRemote;
import tech.ankainn.edanapplication.model.api.formtwo.InformationRemote;
import tech.ankainn.edanapplication.model.api.formtwo.MaterialTypeRemote;
import tech.ankainn.edanapplication.model.api.formtwo.ResponsableRemote;
import tech.ankainn.edanapplication.model.app.auth.UserData;
import tech.ankainn.edanapplication.model.app.formOne.FormOneData;
import tech.ankainn.edanapplication.model.app.formOne.SelectableData;
import tech.ankainn.edanapplication.model.app.formOne.SelectableItemData;
import tech.ankainn.edanapplication.model.app.geninf.GenInfData;
import tech.ankainn.edanapplication.model.app.geninf.HeaderData;
import tech.ankainn.edanapplication.model.dto.FormTwoComplete;
import tech.ankainn.edanapplication.model.app.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.app.formTwo.LivelihoodData;
import tech.ankainn.edanapplication.model.app.formTwo.MemberData;

public class ModelFactory {

    public static <T> T clonePojo(T pojo) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(pojo), (Type) pojo.getClass());
    }

    public static GenInfData createEmptyGenInf() {
        return new GenInfData();
    }

    public static FormOneData createEmptyFormOneData() {
        FormOneData formOneData = new FormOneData();

        /*formOneData.dataVersion = 0;

        formOneData.genInfData = createEmptyGenInf();

        formOneData.damageOne = new SelectableData(createSelectableList(5));
        formOneData.damageTwo = new SelectableData(createSelectableList(5));
        formOneData.damageThree = new SelectableData(createSelectableList(4));

        formOneData.activities = new SelectableData(createSelectableList(5));
        formOneData.needs = new SelectableData(createSelectableList(3));*/

        return formOneData;
    }

    public static FormTwoData createEmptyFormTwoData() {
        return new FormTwoData();
    }

    public static MemberData createEmptyMemberData() {
        return new MemberData();
    }

    public static LivelihoodData createEmptyLivelihoodData() {
        return new LivelihoodData();
    }

    public static UserData userFromRemote(AuthResponse authResponse, String hash) {
        UserData userData = new UserData();

        userData.hash = hash;

        ApiUser apiUser = authResponse.getApiUser().get(0);

        userData.surname = apiUser.getAPPAT() + " " + apiUser.getAPMAT();
        userData.name = apiUser.getNOMBRE();
        userData.email = apiUser.getEMAIL();
        userData.idenNum = apiUser.getDOCUMENTO();

        userData.online = true;
        userData.token = authResponse.getToken();

        return userData;
    }

    public static FormTwoData dataFromEntityComplete(FormTwoComplete source) {

        FormTwoData formTwoData = source.formTwoData;

        List<MemberData> tempMembers = source.memberDataList;

        if(!tempMembers.isEmpty())
            formTwoData.listMemberData = tempMembers;

        List<LivelihoodData> tempLivelihoods = source.livelihoodDataList;

        if(!tempLivelihoods.isEmpty())
            formTwoData.listLivelihood = tempLivelihoods;

        return formTwoData;
    }

    public static FormTwoRemote completeEntityToRemote(FormTwoComplete formTwoCompleteEntity) {
        FormTwoRemote formTwoRemote = new FormTwoRemote();

        FormTwoData data = formTwoCompleteEntity.formTwoData;
        HeaderData headerEntity = data.genInfData.headerData;

        //***********************************************
        FormTwoHeaderRemote headerRemote = new FormTwoHeaderRemote();
        headerRemote.setPeligroTipo(headerEntity.codeGroupDanger);
        headerRemote.setEvaluacionNro(-1);

        String eventDateTime = headerEntity.dateEvent + " " + headerEntity.hourEvent;
        String creationDateTime = headerEntity.dateCreation + " " + headerEntity.hourCreation;

        headerRemote.setOcurrenciaFechaHora(eventDateTime);
        headerRemote.setEmpadronamientoFechaHora(creationDateTime);
        headerRemote.setCentroPoblado(data.genInfData.extraData.nameLocality);
        headerRemote.setLocalidad(data.genInfData.extraData.nameLocality);
        headerRemote.setCaserio(data.genInfData.extraData.nameCA);
        headerRemote.setCalle(data.genInfData.extraData.nameCM);
        headerRemote.setPiso(Converter.stringToInteger(data.genInfData.extraData.nameEPD));

        formTwoRemote.setForm2aCab(headerRemote);

        //***********************************************
        HouseholdRemote householdRemote = new HouseholdRemote();
        householdRemote.setCondicionViviendaPostDesastre(data.householdData.codeConditionHouse);
        householdRemote.setCondicionUsoInstalacion(data.householdData.codeUseHouse);
        householdRemote.setLoteNro(data.householdData.lot);
        householdRemote.setTenenciaPropia(data.householdData.owner ? "S" : "N");

        MaterialTypeRemote materialTypeRemote = new MaterialTypeRemote();
        materialTypeRemote.setTecho(data.householdData.codeRoof);
        materialTypeRemote.setPared(data.householdData.codeWall);
        materialTypeRemote.setPiso(data.householdData.codeFloor);

        householdRemote.setTipoMaterialVivienda(materialTypeRemote);

        formTwoRemote.setInformacionVivienda(householdRemote);

        //************************************************
        formTwoRemote.setFamilias(new FamiliesRemote());
        formTwoRemote.getFamilias().setFamily(new FamilyRemote());
        formTwoRemote.getFamilias().getFamily().setInformacionRegular(new InformationRemote());

        ResponsableRemote responsableRemote = new ResponsableRemote();

        List<MemberData> list = formTwoCompleteEntity.memberDataList;
        if (list != null && list.size() > 0) {
            MemberData memberEntity = list.get(0);

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

    private static List<SelectableItemData> createSelectableList(int number) {
        List<SelectableItemData> result = new ArrayList<>();
        for (int i = 0; i < number ; i++) {
            result.add(new SelectableItemData());
        }
        return result;
    }
}
