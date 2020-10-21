package tech.ankainn.edanapplication.danger;

import java.util.ArrayList;
import java.util.List;

public class DangerSource {

    private static final DangerSource instance = new DangerSource();

    public final List<DangerGroup> listDangerGroups;

    public static DangerSource getInstance() {
        return instance;
    }

    private DangerSource() {
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
    }
}
