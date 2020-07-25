package tech.ankainn.edanapplication.util;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.binding.Converter;
import tech.ankainn.edanapplication.model.apiFormTwo.Condition;
import tech.ankainn.edanapplication.model.apiFormTwo.Document;
import tech.ankainn.edanapplication.model.apiFormTwo.Etario;
import tech.ankainn.edanapplication.model.apiFormTwo.Familia;
import tech.ankainn.edanapplication.model.apiFormTwo.ApiFormTwo;
import tech.ankainn.edanapplication.model.apiFormTwo.FormTwoCab;
import tech.ankainn.edanapplication.model.apiFormTwo.GrupoEtarios;
import tech.ankainn.edanapplication.model.apiFormTwo.GruposVulnerables;
import tech.ankainn.edanapplication.model.apiFormTwo.InformacionEspecial;
import tech.ankainn.edanapplication.model.apiFormTwo.InformacionRegular;
import tech.ankainn.edanapplication.model.apiFormTwo.InformacionVivienda;
import tech.ankainn.edanapplication.model.apiFormTwo.LifeHealth;
import tech.ankainn.edanapplication.model.apiFormTwo.MaterialType;
import tech.ankainn.edanapplication.model.apiFormTwo.PersonalInjury;
import tech.ankainn.edanapplication.model.apiFormTwo.Responsable;
import tech.ankainn.edanapplication.model.dto.FormTwoWithMembers;
import tech.ankainn.edanapplication.model.dto.MemberEntity;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.formTwo.GenInfData;
import tech.ankainn.edanapplication.model.formTwo.HouseholdData;
import tech.ankainn.edanapplication.model.formTwo.MemberData;
import tech.ankainn.edanapplication.model.formTwo.MapLocationData;
import tech.ankainn.edanapplication.retrofit.ApiListResponse;

public class FormTwoFactory {

    public static FormTwoData createEmptyFormTwoData() {
        FormTwoData formTwoData = new FormTwoData();

        formTwoData.dataVersion = 0;
        formTwoData.formTwoApiId = -1;

        formTwoData.mapLocationData = new MapLocationData();
        formTwoData.mapLocationData.longitude = 0d;
        formTwoData.mapLocationData.latitude = 0d;
        formTwoData.mapLocationData.altitude = 0;
        formTwoData.mapLocationData.reference = "";
        formTwoData.mapLocationData.transport = "";

        formTwoData.genInfData = new GenInfData();
        formTwoData.genInfData.groupDanger = "";
        formTwoData.genInfData.typeDanger = "";
        formTwoData.genInfData.date = "";
        formTwoData.genInfData.hour = "";
        formTwoData.genInfData.department = "";
        formTwoData.genInfData.province = "";
        formTwoData.genInfData.district = "";
        formTwoData.genInfData.locality = "";
        formTwoData.genInfData.zone = "";

        formTwoData.householdData = new HouseholdData();
        formTwoData.householdData.lot = "";
        formTwoData.householdData.owner = false;
        formTwoData.householdData.condition = "";
        formTwoData.householdData.idWall = -1;
        formTwoData.householdData.wall = "";
        formTwoData.householdData.idFloor = -1;
        formTwoData.householdData.floor = "";
        formTwoData.householdData.idRoof = -1;
        formTwoData.householdData.roof = "";

        return formTwoData;
    }

    public static MemberData createEmptyMemberData() {
        MemberData memberData = new MemberData();
        memberData.head = false;
        memberData.age = -1;
        memberData.name = "";
        memberData.gender = "";
        memberData.idType = "";
        memberData.idNumber = -1;
        memberData.condition = "";
        memberData.personalInjury = "";

        return memberData;
    }

    public static FormTwoData create(GenInfData genInfData, MapLocationData mapLocationData,
                                     HouseholdData householdData, List<MemberData> listMember) {
        FormTwoData formTwoData = createEmptyFormTwoData();

        formTwoData.genInfData = genInfData;
        formTwoData.mapLocationData = mapLocationData;
        formTwoData.householdData = householdData;
        formTwoData.listMember = listMember;
        return formTwoData;
    }

    public static ApiFormTwo apiFromData(FormTwoData formTwoData) {
        ApiFormTwo result = new ApiFormTwo();

        FormTwoCab formTwoCab = new FormTwoCab();
        formTwoCab.setEvaluacionNro("1");
        formTwoCab.setSinpadNro("sinpad");
        formTwoCab.setPeligroTipo(formTwoData.genInfData.typeDanger);
        formTwoCab.setEmpadronamientoFechaHora(formTwoData.genInfData.date + " " + formTwoData.genInfData.hour);
        formTwoCab.setOcurrenciaFechaHora(formTwoData.genInfData.date + " " + formTwoData.genInfData.hour);
        formTwoCab.setDepartamento(formTwoData.genInfData.department);
        formTwoCab.setProvincia(formTwoData.genInfData.province);
        formTwoCab.setDistrito(formTwoData.genInfData.district);
        formTwoCab.setLocalidad(formTwoData.genInfData.locality);
        formTwoCab.setSector("sector");
        formTwoCab.setCalle("calle");
        formTwoCab.setPiso("piso");
        formTwoCab.setCentroPoblado("centro poblado");
        formTwoCab.setCaserio("caserio");
        formTwoCab.setAnexo("anexo");
        formTwoCab.setHojaNro("001");
        formTwoCab.setOtros(formTwoData.genInfData.zone);
        result.setForm2aCab(formTwoCab);

        //************************
        InformacionVivienda informacionVivienda = new InformacionVivienda();
        Integer lot = Converter.stringToInteger(formTwoData.householdData.lot);
        informacionVivienda.setLoteNro(lot != null ? lot : 0);
        informacionVivienda.setTenenciaPropia("si");
        informacionVivienda.setCondicionUsoInstalacion(formTwoData.householdData.condition);
        informacionVivienda.setCondicionViviendaPostDesastre(formTwoData.householdData.condition);

        MaterialType materialType = new MaterialType();

        materialType.setTecho(formTwoData.householdData.idRoof);
        materialType.setPared(formTwoData.householdData.idWall);
        materialType.setPiso(formTwoData.householdData.idFloor);

        informacionVivienda.setTipoMaterialVivienda(materialType);
        result.setInformacionVivienda(informacionVivienda);

        //***********************
        List<Familia> familiaList = new ArrayList<>();

        for (MemberData householdMemberData : formTwoData.listMember) {
            Familia familia = new Familia();

            InformacionRegular informacionRegular = new InformacionRegular();

            Responsable responsable = new Responsable();
            responsable.setNombres(householdMemberData.name);
            responsable.setApellidos(householdMemberData.name);
            responsable.setEdad(householdMemberData.age);

            Document document = new Document();
            //TODO idType
            document.setTipo(1);
            document.setNro(householdMemberData.idNumber.toString());
            responsable.setDocument(document);

            informacionRegular.setResponsable(responsable);

            LifeHealth vidaSalud = new LifeHealth();

            Condition condition = new Condition();
            // TODO personalInjury
            condition.setAfectados(2);
            condition.setDamnificados(1);
            vidaSalud.setCondicion(condition);

            PersonalInjury daniosPersonales = new PersonalInjury();

            daniosPersonales.setDesaparecidos(2);
            daniosPersonales.setFallecidos(1);
            daniosPersonales.setLesionados(1);
            vidaSalud.setDaniosPersonales(daniosPersonales);

            informacionRegular.setVidaSalud(vidaSalud);

            GrupoEtarios grupoEtarios = new GrupoEtarios();

            Etario etario = new Etario();
            etario.setF(1);
            etario.setM(0);

            grupoEtarios.setMenorA1(etario);
            grupoEtarios.set_1A40(etario);
            grupoEtarios.set_5A9(etario);
            grupoEtarios.set_10A14(etario);
            grupoEtarios.set_15A17(etario);
            grupoEtarios.set_18A49(etario);
            grupoEtarios.set_50A59(etario);
            grupoEtarios.set_60AMas(etario);
            informacionRegular.setGruposEtarios(grupoEtarios);

            familia.setInformacionRegular(informacionRegular);

            InformacionEspecial informacionEspecial = new InformacionEspecial();

            GruposVulnerables gruposVulnerables = new GruposVulnerables();
            gruposVulnerables.setGestantes(5);
            gruposVulnerables.setPersonasConDiscapacidad(1);
            gruposVulnerables.setTipoEnfermedadCronica(5);
            informacionEspecial.setGruposVulnerables(gruposVulnerables);
            familia.setInformacionEspecial(informacionEspecial);

            familiaList.add(familia);
        }

        result.setFamilias(familiaList);

        return result;
    }

    public static MemberData dataFromDb(MemberEntity source) {
        MemberData memberData = new MemberData();
        memberData.id = source.memberId;
        memberData.head = source.head;
        memberData.name = source.name;
        memberData.age = source.age;
        memberData.gender = source.gender;
        memberData.idType = source.idType;
        memberData.idNumber = source.idNumber;
        memberData.condition = source.condition;
        memberData.personalInjury = source.personalInjury;

        return memberData;
    }

    public static FormTwoData dataFromDb(FormTwoWithMembers source) {
        FormTwoData formTwoData = createEmptyFormTwoData();

        formTwoData.id = source.formTwoEntity.formTwoId;
        formTwoData.formTwoApiId = source.formTwoEntity.formTwoApiId;
        formTwoData.dataVersion = source.formTwoEntity.dataVersion;

        formTwoData.mapLocationData.latitude = source.formTwoEntity.latitude;
        formTwoData.mapLocationData.longitude = source.formTwoEntity.longitude;
        formTwoData.mapLocationData.altitude = source.formTwoEntity.altitude;
        formTwoData.mapLocationData.reference = source.formTwoEntity.reference;
        formTwoData.mapLocationData.transport = source.formTwoEntity.transport;

        formTwoData.genInfData.groupDanger = source.formTwoEntity.groupDanger;
        formTwoData.genInfData.typeDanger = source.formTwoEntity.typeDanger;
        formTwoData.genInfData.date = source.formTwoEntity.date;
        formTwoData.genInfData.hour = source.formTwoEntity.hour;
        formTwoData.genInfData.department = source.formTwoEntity.department;
        formTwoData.genInfData.province = source.formTwoEntity.province;
        formTwoData.genInfData.district = source.formTwoEntity.district;
        formTwoData.genInfData.locality = source.formTwoEntity.locality;
        formTwoData.genInfData.zone = source.formTwoEntity.zone;

        formTwoData.householdData.lot = source.formTwoEntity.lot;
        formTwoData.householdData.owner = source.formTwoEntity.owner;
        formTwoData.householdData.condition = source.formTwoEntity.condition;
        formTwoData.householdData.idRoof = source.formTwoEntity.idRoof;
        formTwoData.householdData.roof = source.formTwoEntity.roof;
        formTwoData.householdData.idWall = source.formTwoEntity.idWall;
        formTwoData.householdData.wall = source.formTwoEntity.wall;
        formTwoData.householdData.idFloor = source.formTwoEntity.idFloor;
        formTwoData.householdData.floor = source.formTwoEntity.floor;

        List<MemberData> tempMembers = new ArrayList<>();
        for (MemberEntity memberEntity : source.memberEntityList) {
            MemberData memberData = dataFromDb(memberEntity);
            tempMembers.add(memberData);
        }

        if(tempMembers.size() != 0)
            formTwoData.listMember = tempMembers;

        return formTwoData;
    }

    public static List<FormTwoData> fromApiList(List<ApiListResponse.Datum> list) {
        if(list == null || list.size() == 0)
            return null;

        List<FormTwoData> temp = new ArrayList<>();

        for (ApiListResponse.Datum datum : list) {
            FormTwoData formTwoData = new FormTwoData();
            formTwoData.genInfData = new GenInfData();
            formTwoData.formTwoApiId = datum.getFORM2ACABID();
            formTwoData.genInfData.typeDanger = datum.getPELIGROTIPO();
            formTwoData.genInfData.department = datum.getDEPARTAMENTO();
            formTwoData.genInfData.date = datum.getOCURRENCIAFECHAHORA();
            formTwoData.genInfData.hour = "";
            temp.add(formTwoData);
        }

        return temp;
    }

    public static List<FormTwoData> fromDbList(List<FormTwoWithMembers> source) {
        List<FormTwoData> result = new ArrayList<>();
        for (FormTwoWithMembers formTwoWithMembers : source) {
            FormTwoData formTwoData = dataFromDb(formTwoWithMembers);
            result.add(formTwoData);
        }
        return result;
    }

    public static ApiFormTwo testApi() {
        ApiFormTwo result = new ApiFormTwo();

        FormTwoCab formTwoCab = new FormTwoCab();
        formTwoCab.setEvaluacionNro("1");
        formTwoCab.setSinpadNro("sinpad");
        formTwoCab.setPeligroTipo("peligro");
        formTwoCab.setEmpadronamientoFechaHora("2000-06-01 00:20:09");
        formTwoCab.setOcurrenciaFechaHora("2020-06-01 10:20:00");
        formTwoCab.setDepartamento("Lima");
        formTwoCab.setProvincia("Lima");
        formTwoCab.setDistrito("Lima");
        formTwoCab.setLocalidad("Limatambo");
        formTwoCab.setSector("sector");
        formTwoCab.setCalle("calle");
        formTwoCab.setPiso("piso");
        formTwoCab.setCentroPoblado("centro poblado");
        formTwoCab.setCaserio("caserio");
        formTwoCab.setAnexo("anexo");
        formTwoCab.setHojaNro("001");
        formTwoCab.setOtros("otros");
        result.setForm2aCab(formTwoCab);

        //************************
        InformacionVivienda informacionVivienda = new InformacionVivienda();
        informacionVivienda.setLoteNro(1);
        informacionVivienda.setTenenciaPropia("si");
        informacionVivienda.setCondicionUsoInstalacion("vivienda");
        informacionVivienda.setCondicionViviendaPostDesastre("afectada");

        MaterialType materialType = new MaterialType();
        materialType.setTecho(2);
        materialType.setPared(1);
        materialType.setPiso(1);
        informacionVivienda.setTipoMaterialVivienda(materialType);
        result.setInformacionVivienda(informacionVivienda);

        //***********************
        Familia familia1 = new Familia();

        InformacionRegular informacionRegular = new InformacionRegular();

        Responsable responsable = new Responsable();
        responsable.setNombres("Will");
        responsable.setApellidos("Smith");
        responsable.setEdad(20);

        Document document = new Document();
        document.setTipo(1);
        document.setNro("88888888");
        responsable.setDocument(document);
        informacionRegular.setResponsable(responsable);

        LifeHealth vidaSalud = new LifeHealth();

        Condition condition = new Condition();
        condition.setAfectados(2);
        condition.setDamnificados(1);
        vidaSalud.setCondicion(condition);

        PersonalInjury daniosPersonales = new PersonalInjury();

        daniosPersonales.setDesaparecidos(2);
        daniosPersonales.setFallecidos(1);
        daniosPersonales.setLesionados(1);
        vidaSalud.setDaniosPersonales(daniosPersonales);

        informacionRegular.setVidaSalud(vidaSalud);

        GrupoEtarios grupoEtarios = new GrupoEtarios();

        Etario etario = new Etario();
        etario.setF(1);
        etario.setM(0);

        grupoEtarios.setMenorA1(etario);
        grupoEtarios.set_1A40(etario);
        grupoEtarios.set_5A9(etario);
        grupoEtarios.set_10A14(etario);
        grupoEtarios.set_15A17(etario);
        grupoEtarios.set_18A49(etario);
        grupoEtarios.set_50A59(etario);
        grupoEtarios.set_60AMas(etario);
        informacionRegular.setGruposEtarios(grupoEtarios);

        familia1.setInformacionRegular(informacionRegular);

        InformacionEspecial informacionEspecial = new InformacionEspecial();

        GruposVulnerables gruposVulnerables = new GruposVulnerables();
        gruposVulnerables.setGestantes(5);
        gruposVulnerables.setPersonasConDiscapacidad(1);
        gruposVulnerables.setTipoEnfermedadCronica(5);
        informacionEspecial.setGruposVulnerables(gruposVulnerables);
        familia1.setInformacionEspecial(informacionEspecial);

        List<Familia> familiaList = new ArrayList<>();
        familiaList.add(familia1);

        result.setFamilias(familiaList);

        return result;
    }
}
