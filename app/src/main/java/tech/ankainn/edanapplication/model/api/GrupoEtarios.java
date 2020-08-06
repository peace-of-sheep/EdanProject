package tech.ankainn.edanapplication.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrupoEtarios {

    @SerializedName("menor_a_1")
    @Expose
    private Etario menorA1;

    @SerializedName("_1_a_40")
    @Expose
    private Etario _1A40;

    @SerializedName("_5_a_9")
    @Expose
    private Etario _5A9;

    @SerializedName("_10_a_14")
    @Expose
    private Etario _10A14;

    @SerializedName("_15_a_17")
    @Expose
    private Etario _15A17;

    @SerializedName("_18_a_49")
    @Expose
    private Etario _18A49;

    @SerializedName("_50_a_59")
    @Expose
    private Etario _50A59;

    @SerializedName("_60_a_mas")
    @Expose
    private Etario _60AMas;

    public Etario getMenorA1() {
        return menorA1;
    }

    public void setMenorA1(Etario menorA1) {
        this.menorA1 = menorA1;
    }

    public Etario get_1A40() {
        return _1A40;
    }

    public void set_1A40(Etario _1A40) {
        this._1A40 = _1A40;
    }

    public Etario get_5A9() {
        return _5A9;
    }

    public void set_5A9(Etario _5A9) {
        this._5A9 = _5A9;
    }

    public Etario get_10A14() {
        return _10A14;
    }

    public void set_10A14(Etario _10A14) {
        this._10A14 = _10A14;
    }

    public Etario get_15A17() {
        return _15A17;
    }

    public void set_15A17(Etario _15A17) {
        this._15A17 = _15A17;
    }

    public Etario get_18A49() {
        return _18A49;
    }

    public void set_18A49(Etario _18A49) {
        this._18A49 = _18A49;
    }

    public Etario get_50A59() {
        return _50A59;
    }

    public void set_50A59(Etario _50A59) {
        this._50A59 = _50A59;
    }

    public Etario get_60AMas() {
        return _60AMas;
    }

    public void set_60AMas(Etario _60AMas) {
        this._60AMas = _60AMas;
    }
}
