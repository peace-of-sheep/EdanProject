package tech.ankainn.edanapplication.model.api.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiUser {

    @SerializedName("USERNAME")
    @Expose
    private String uSERNAME;
    @SerializedName("PASSWORD")
    @Expose
    private String pASSWORD;
    @SerializedName("NOMBRE")
    @Expose
    private String nOMBRE;
    @SerializedName("CARGO")
    @Expose
    private Object cARGO;
    @SerializedName("ESTADO")
    @Expose
    private String eSTADO;
    @SerializedName("EMAIL")
    @Expose
    private String eMAIL;
    @SerializedName("APMAT")
    @Expose
    private String aPMAT;
    @SerializedName("DOCUMENTO")
    @Expose
    private String dOCUMENTO;
    @SerializedName("TELEFONO")
    @Expose
    private Object tELEFONO;
    @SerializedName("APPAT")
    @Expose
    private String aPPAT;
    @SerializedName("INSTITUCION")
    @Expose
    private Object iNSTITUCION;
    @SerializedName("TIPODOC")
    @Expose
    private String tIPODOC;
    @SerializedName("JUSTIFICACION")
    @Expose
    private Object jUSTIFICACION;
    @SerializedName("TIPOUSUARIO")
    @Expose
    private Object tIPOUSUARIO;
    @SerializedName("UDEP")
    @Expose
    private Object uDEP;
    @SerializedName("UDIST")
    @Expose
    private Object uDIST;
    @SerializedName("UPROV")
    @Expose
    private Object uPROV;
    @SerializedName("AREA")
    @Expose
    private Object aREA;
    @SerializedName("UBIGEO")
    @Expose
    private Object uBIGEO;
    @SerializedName("ANEXO")
    @Expose
    private Object aNEXO;
    @SerializedName("PERFIL")
    @Expose
    private String pERFIL;
    @SerializedName("ENTIDAD")
    @Expose
    private Integer eNTIDAD;
    @SerializedName("FLG_RENIEC")
    @Expose
    private String fLGRENIEC;
    @SerializedName("FLG_UTILIZA_RENIEC")
    @Expose
    private String fLGUTILIZARENIEC;
    @SerializedName("FEC_ALTA")
    @Expose
    private Object fECALTA;
    @SerializedName("FEC_BAJA")
    @Expose
    private Object fECBAJA;

    public String getUSERNAME() {
        return uSERNAME;
    }

    public void setUSERNAME(String uSERNAME) {
        this.uSERNAME = uSERNAME;
    }

    public String getPASSWORD() {
        return pASSWORD;
    }

    public void setPASSWORD(String pASSWORD) {
        this.pASSWORD = pASSWORD;
    }

    public String getNOMBRE() {
        return nOMBRE;
    }

    public void setNOMBRE(String nOMBRE) {
        this.nOMBRE = nOMBRE;
    }

    public Object getCARGO() {
        return cARGO;
    }

    public void setCARGO(Object cARGO) {
        this.cARGO = cARGO;
    }

    public String getESTADO() {
        return eSTADO;
    }

    public void setESTADO(String eSTADO) {
        this.eSTADO = eSTADO;
    }

    public String getEMAIL() {
        return eMAIL;
    }

    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public String getAPMAT() {
        return aPMAT;
    }

    public void setAPMAT(String aPMAT) {
        this.aPMAT = aPMAT;
    }

    public String getDOCUMENTO() {
        return dOCUMENTO;
    }

    public void setDOCUMENTO(String dOCUMENTO) {
        this.dOCUMENTO = dOCUMENTO;
    }

    public Object getTELEFONO() {
        return tELEFONO;
    }

    public void setTELEFONO(Object tELEFONO) {
        this.tELEFONO = tELEFONO;
    }

    public String getAPPAT() {
        return aPPAT;
    }

    public void setAPPAT(String aPPAT) {
        this.aPPAT = aPPAT;
    }

    public Object getINSTITUCION() {
        return iNSTITUCION;
    }

    public void setINSTITUCION(Object iNSTITUCION) {
        this.iNSTITUCION = iNSTITUCION;
    }

    public String getTIPODOC() {
        return tIPODOC;
    }

    public void setTIPODOC(String tIPODOC) {
        this.tIPODOC = tIPODOC;
    }

    public Object getJUSTIFICACION() {
        return jUSTIFICACION;
    }

    public void setJUSTIFICACION(Object jUSTIFICACION) {
        this.jUSTIFICACION = jUSTIFICACION;
    }

    public Object getTIPOUSUARIO() {
        return tIPOUSUARIO;
    }

    public void setTIPOUSUARIO(Object tIPOUSUARIO) {
        this.tIPOUSUARIO = tIPOUSUARIO;
    }

    public Object getUDEP() {
        return uDEP;
    }

    public void setUDEP(Object uDEP) {
        this.uDEP = uDEP;
    }

    public Object getUDIST() {
        return uDIST;
    }

    public void setUDIST(Object uDIST) {
        this.uDIST = uDIST;
    }

    public Object getUPROV() {
        return uPROV;
    }

    public void setUPROV(Object uPROV) {
        this.uPROV = uPROV;
    }

    public Object getAREA() {
        return aREA;
    }

    public void setAREA(Object aREA) {
        this.aREA = aREA;
    }

    public Object getUBIGEO() {
        return uBIGEO;
    }

    public void setUBIGEO(Object uBIGEO) {
        this.uBIGEO = uBIGEO;
    }

    public Object getANEXO() {
        return aNEXO;
    }

    public void setANEXO(Object aNEXO) {
        this.aNEXO = aNEXO;
    }

    public String getPERFIL() {
        return pERFIL;
    }

    public void setPERFIL(String pERFIL) {
        this.pERFIL = pERFIL;
    }

    public Integer getENTIDAD() {
        return eNTIDAD;
    }

    public void setENTIDAD(Integer eNTIDAD) {
        this.eNTIDAD = eNTIDAD;
    }

    public String getFLGRENIEC() {
        return fLGRENIEC;
    }

    public void setFLGRENIEC(String fLGRENIEC) {
        this.fLGRENIEC = fLGRENIEC;
    }

    public String getFLGUTILIZARENIEC() {
        return fLGUTILIZARENIEC;
    }

    public void setFLGUTILIZARENIEC(String fLGUTILIZARENIEC) {
        this.fLGUTILIZARENIEC = fLGUTILIZARENIEC;
    }

    public Object getFECALTA() {
        return fECALTA;
    }

    public void setFECALTA(Object fECALTA) {
        this.fECALTA = fECALTA;
    }

    public Object getFECBAJA() {
        return fECBAJA;
    }

    public void setFECBAJA(Object fECBAJA) {
        this.fECBAJA = fECBAJA;
    }
}
