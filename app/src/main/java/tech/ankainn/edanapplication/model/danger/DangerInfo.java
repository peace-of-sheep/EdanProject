package tech.ankainn.edanapplication.model.danger;

import java.util.ArrayList;
import java.util.List;

public class DangerInfo {

    private static final DangerInfo instance = new DangerInfo();

    public final List<DangerGroup> listDangerGroups;

    public static DangerInfo getInstance() {
        return instance;
    }

    private DangerInfo() {
        listDangerGroups = new ArrayList<>();

        DangerGroup danger1000 = new DangerGroup.Builder("1000", "Peligros de Geodinámica interna")
                .addDanger("1100", "1100 Sismos")
                .addDanger("1200", "1200 Tsunami(Maremoto)")
                .addDanger("1300", "1300 Erupciones Volcánicas")
                .build();
        listDangerGroups.add(danger1000);

        DangerGroup danger2000 = new DangerGroup.Builder("2000", "Peligros de Geodinámica externa")
                .addDanger("2100", "Huaycos")
                .addDanger("2210", "Derrumbes de Viviendas")
                .addDanger("2220", "Derrumbes de Cerros")
                .addDanger("2230", "Derrumbes de Estructuras en general")
                .addDanger("2300", "Reptación")
                .addDanger("2400", "Deslizamientos")
                .addDanger("2500", "Aluviones")
                .addDanger("2600", "Aludes")
                .addDanger("2700", "Deglaciación")
                .addDanger("2800", "Otros")
                .build();
        listDangerGroups.add(danger2000);

        DangerGroup danger3000 = new DangerGroup.Builder("3000", "Peligros Hidrometeorológicos y Oceanográficos")
                .addDanger("3110", "Inundaciones por desborde de Ríos")
                .addDanger("3110", "Inundaciones por desborde de Lagos o Lagunas")
                .addDanger("3110", "Inundaciones por desborde de Canales")
                .addDanger("3110", "Inundaciones por desborde en la Ruptura de Diques")
                .addDanger("3200", "Lluvias intensas")
                .addDanger("3300", "Vientos fuertes")
                .addDanger("3400", "Sequías")
                .addDanger("3500", "Heladas")
                .addDanger("3600", "Granizadas")
                .addDanger("3700", "Nevadas")
                .addDanger("3800", "Friaje")
                .addDanger("3900", "Maretazos (Marejadas)")
                .addDanger("3A00", "Tempestades Eléctricas")
                .addDanger("3B00", "Desertificación")
                .addDanger("3C00", "Embalses")
                .addDanger("3D00", "Temporales (Vientos con Lluvias)")
                .addDanger("3E00", "Déficit Hídrico")
                .addDanger("3F00", "Otros")
                .build();
        listDangerGroups.add(danger3000);

        DangerGroup danger4000 = new DangerGroup.Builder("4000", "Peligros Biológicos")
                .addDanger("4100", "Epidemias")
                .build();
    }
}
