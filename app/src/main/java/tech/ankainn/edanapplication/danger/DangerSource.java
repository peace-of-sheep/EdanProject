package tech.ankainn.edanapplication.danger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DangerSource {

    private static final DangerSource instance = new DangerSource();

    //public final List<DangerGroup> listDangerGroups;

    private final List<DangerEntity> listGroupDanger;
    private final List<DangerEntity> listDanger;

    public static DangerSource getInstance() {
        return instance;
    }

    /*private DangerSource() {
        listDangerGroups = new ArrayList<>();

        DangerGroup danger1000 = new DangerGroup.Builder("1000", "1000 Peligros de Geodinámica interna")
                .addDanger("1100", "1100 Sismos")
                .addDanger("1200", "1200 Tsunami(Maremoto)")
                .addDanger("1300", "1300 Erupciones Volcánicas")
                .build();
        listDangerGroups.add(danger1000);

        DangerGroup danger2000 = new DangerGroup.Builder("2000", "2000 Peligros de Geodinámica externa")
                .addDanger("2100", "2100 Huaycos")
                .addDanger("2210", "2210 Derrumbes de Viviendas")
                .addDanger("2220", "2220 Derrumbes de Cerros")
                .addDanger("2230", "2230 Derrumbes de Estructuras en general")
                .addDanger("2300", "2300 Reptación")
                .addDanger("2400", "2400 Deslizamientos")
                .addDanger("2500", "2500 Aluviones")
                .addDanger("2600", "2600 Aludes")
                .addDanger("2700", "2700 Deglaciación")
                .addDanger("2800", "2800 Otros")
                .build();
        listDangerGroups.add(danger2000);

        DangerGroup danger3000 = new DangerGroup.Builder("3000", "3000 Peligros Hidrometeorológicos y Oceanográficos")
                .addDanger("3110", "3110 Inundaciones por desborde de Ríos")
                .addDanger("3120", "3120 Inundaciones por desborde de Lagos o Lagunas")
                .addDanger("3130", "3130 Inundaciones por desborde de Canales")
                .addDanger("3140", "3140 Inundaciones por desborde en la Ruptura de Diques")
                .addDanger("3200", "3200 Lluvias intensas")
                .addDanger("3300", "3300 Vientos fuertes")
                .addDanger("3400", "3400 Sequías")
                .addDanger("3500", "3500 Heladas")
                .addDanger("3600", "3600 Granizadas")
                .addDanger("3700", "3700 Nevadas")
                .addDanger("3800", "3800 Friaje")
                .addDanger("3900", "3900 Maretazos (Marejadas)")
                .addDanger("3A00", "3A00 Tempestades Eléctricas")
                .addDanger("3B00", "3B00 Desertificación")
                .addDanger("3C00", "3C00 Embalses")
                .addDanger("3D00", "3D00 Temporales (Vientos con Lluvias)")
                .addDanger("3E00", "3E00 Déficit Hídrico")
                .addDanger("3F00", "3F00 Otros")
                .build();
        listDangerGroups.add(danger3000);

        DangerGroup danger4000 = new DangerGroup.Builder("4000", "4000 Peligros Biológicos")
                .addDanger("4100", "4100 Epidemias")
                .addDanger("4200", "4200 Epizotías")
                .addDanger("4300", "4300 Plagas")
                .addDanger("4400", "4400 Varazones de Peces")
                .addDanger("4500", "4500 Hambrunas")
                .addDanger("4600", "4600 Otros")
                .build();
        listDangerGroups.add(danger4000);

        DangerGroup danger5000 = new DangerGroup.Builder("5000", "5000 Peligros Antrópicos")
                .addDanger("5100", "5100 Incendios Urbanos")
                .addDanger("5200", "5200 Incendios Industriales")
                .addDanger("5300", "5300 Incendios Forestales")

                .addDanger("5410", "5410 Contaminación Ambiental Atmosférica")
                .addDanger("5420", "5420 Contaminación Ambiental de Aguas")
                .addDanger("5430", "5430 Contaminación Ambiental de Suelos")
                .addDanger("5440", "5440 Contaminación Ambiental Radioactiva")

                .addDanger("5510", "5510 AcTr Medio terrestre")
                .addDanger("5520", "5520 AcTr Medio Aéreo")
                .addDanger("5531", "5531 AcTr Medio Acuático Marítimo")
                .addDanger("5532", "5532 AcTr Medio Acuático Fluvial")
                .addDanger("5533", "5533 AcTr Medio Acuático Lacustre")

                .addDanger("5610", "5610 DSNPP Gas natural")
                .addDanger("5620", "5620 DSNPP Mercurio")
                .addDanger("5630", "5630 DSNPP Hidrocarburos")
                .addDanger("5640", "5640 DSNPP Material Radioactivo")

                .addDanger("5700", "5700 Deforestación")
                .addDanger("5800", "5800 Explosiones")
                .addDanger("5900", "5900 Sabotajes")
                .addDanger("5A00", "5A00 Vandalismo (Saqueos)")
                .addDanger("5B00", "5B00 Acciones de Guerra")
                .addDanger("5C00", "5C00 Otros")
                .build();
        listDangerGroups.add(danger5000);
    }*/

    public DangerSource() {
        listDanger = new ArrayList<>();
        listDanger.add(DangerEntity.createWithOwner("1100", "1100 Sismos"));
        listDanger.add(DangerEntity.createWithOwner("1200", "1200 Tsunami(Maremoto)"));
        listDanger.add(DangerEntity.createWithOwner("1300", "1300 Erupciones Volcánicas"));

        listDanger.add(DangerEntity.createWithOwner("2100", "2100 Huaycos"));
        listDanger.add(DangerEntity.createWithOwner("2210", "2210 Derrumbes de Viviendas"));
        listDanger.add(DangerEntity.createWithOwner("2220", "2220 Derrumbes de Cerros"));
        listDanger.add(DangerEntity.createWithOwner("2230", "2230 Derrumbes de Estructuras en general"));
        listDanger.add(DangerEntity.createWithOwner("2300", "2300 Reptación"));
        listDanger.add(DangerEntity.createWithOwner("2400", "2400 Deslizamientos"));
        listDanger.add(DangerEntity.createWithOwner("2500", "2500 Aluviones"));
        listDanger.add(DangerEntity.createWithOwner("2600", "2600 Aludes"));
        listDanger.add(DangerEntity.createWithOwner("2700", "2700 Deglaciación"));
        listDanger.add(DangerEntity.createWithOwner("2800", "2800 Otros"));

        listDanger.add(DangerEntity.createWithOwner("3110", "3110 Inundaciones por desborde de Ríos"));
        listDanger.add(DangerEntity.createWithOwner("3120", "3120 Inundaciones por desborde de Lagos o Lagunas"));
        listDanger.add(DangerEntity.createWithOwner("3130", "3130 Inundaciones por desborde de Canales"));
        listDanger.add(DangerEntity.createWithOwner("3140", "3140 Inundaciones por desborde en la Ruptura de Diques"));
        listDanger.add(DangerEntity.createWithOwner("3200", "3200 Lluvias intensas"));
        listDanger.add(DangerEntity.createWithOwner("3300", "3300 Vientos fuertes"));
        listDanger.add(DangerEntity.createWithOwner("3400", "3400 Sequías"));
        listDanger.add(DangerEntity.createWithOwner("3500", "3500 Heladas"));
        listDanger.add(DangerEntity.createWithOwner("3600", "3600 Granizadas"));
        listDanger.add(DangerEntity.createWithOwner("3700", "3700 Nevadas"));
        listDanger.add(DangerEntity.createWithOwner("3800", "3800 Friaje"));
        listDanger.add(DangerEntity.createWithOwner("3900", "3900 Maretazos (Marejadas)"));
        listDanger.add(DangerEntity.createWithOwner("3A00", "3A00 Tempestades Eléctricas"));
        listDanger.add(DangerEntity.createWithOwner("3B00", "3B00 Desertificación"));
        listDanger.add(DangerEntity.createWithOwner("3C00", "3C00 Embalses"));
        listDanger.add(DangerEntity.createWithOwner("3D00", "3D00 Temporales (Vientos con Lluvias)"));
        listDanger.add(DangerEntity.createWithOwner("3E00", "3E00 Déficit Hídrico"));
        listDanger.add(DangerEntity.createWithOwner("3F00", "3F00 Otros"));

        listDanger.add(DangerEntity.createWithOwner("4100", "4100 Epidemias"));
        listDanger.add(DangerEntity.createWithOwner("4200", "4200 Epizotías"));
        listDanger.add(DangerEntity.createWithOwner("4300", "4300 Plagas"));
        listDanger.add(DangerEntity.createWithOwner("4400", "4400 Varazones de Peces"));
        listDanger.add(DangerEntity.createWithOwner("4500", "4500 Hambrunas"));
        listDanger.add(DangerEntity.createWithOwner("4600", "4600 Otros"));

        listDanger.add(DangerEntity.createWithOwner("5100", "5100 Incendios Urbanos"));
        listDanger.add(DangerEntity.createWithOwner("5200", "5200 Incendios Industriales"));
        listDanger.add(DangerEntity.createWithOwner("5300", "5300 Incendios Forestales"));
        listDanger.add(DangerEntity.createWithOwner("5410", "5410 Contaminación Ambiental Atmosférica"));
        listDanger.add(DangerEntity.createWithOwner("5420", "5420 Contaminación Ambiental de Aguas"));
        listDanger.add(DangerEntity.createWithOwner("5430", "5430 Contaminación Ambiental de Suelos"));
        listDanger.add(DangerEntity.createWithOwner("5440", "5440 Contaminación Ambiental Radioactiva"));
        listDanger.add(DangerEntity.createWithOwner("5510", "5510 AcTr Medio terrestre"));
        listDanger.add(DangerEntity.createWithOwner("5520", "5520 AcTr Medio Aéreo"));
        listDanger.add(DangerEntity.createWithOwner("5531", "5531 AcTr Medio Acuático Marítimo"));
        listDanger.add(DangerEntity.createWithOwner("5532", "5532 AcTr Medio Acuático Fluvial"));
        listDanger.add(DangerEntity.createWithOwner("5533", "5533 AcTr Medio Acuático Lacustre"));
        listDanger.add(DangerEntity.createWithOwner("5610", "5610 DSNPP Gas natural"));
        listDanger.add(DangerEntity.createWithOwner("5620", "5620 DSNPP Mercurio"));
        listDanger.add(DangerEntity.createWithOwner("5630", "5630 DSNPP Hidrocarburos"));
        listDanger.add(DangerEntity.createWithOwner("5640", "5640 DSNPP Material Radioactivo"));
        listDanger.add(DangerEntity.createWithOwner("5700", "5700 Deforestación"));
        listDanger.add(DangerEntity.createWithOwner("5800", "5800 Explosiones"));
        listDanger.add(DangerEntity.createWithOwner("5900", "5900 Sabotajes"));
        listDanger.add(DangerEntity.createWithOwner("5A00", "5A00 Vandalismo (Saqueos)"));
        listDanger.add(DangerEntity.createWithOwner("5B00", "5B00 Acciones de Guerra"));
        listDanger.add(DangerEntity.createWithOwner("5C00", "5C00 Otros"));

        listGroupDanger = new ArrayList<>();
        listGroupDanger.add(new DangerEntity("1", "1000 Peligros de Geodinámica interna", "1000"));
        listGroupDanger.add(new DangerEntity("1", "2000 Peligros de Geodinámica externa", "2000"));
        listGroupDanger.add(new DangerEntity("1", "3000 Peligros Hidrometeorológicos y Oceanográficos", "3000"));
        listGroupDanger.add(new DangerEntity("1", "4000 Peligros Biológicos", "4000"));
        listGroupDanger.add(new DangerEntity("1", "5000 Peligros Antrópicos", "5000"));
    }

    public List<DangerEntity> getDangers() {
        return listDanger;
    }

    public DangerEntity getGroupDanger(String ownerCode) {
        for (DangerEntity dangerEntity : listGroupDanger) {
            if (Objects.equals(dangerEntity.code, ownerCode)) {
                return dangerEntity;
            }
        }
        return null;
    }
}
