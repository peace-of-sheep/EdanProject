package tech.ankainn.edanapplication.ui.form;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class MapLocationViewModel extends ViewModel {

    private LatLng currentLatLng = new LatLng(-7.146,-75.009);

    public LatLng getCurrentLatLng() {
        return currentLatLng;
    }

    public void setCurrentLatLng(LatLng currentLatLng) {
        this.currentLatLng = currentLatLng;
    }
}
