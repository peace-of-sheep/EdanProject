package tech.ankainn.edanapplication.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiListResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public static class Datum {

        @SerializedName("FORM2A_CAB_ID")
        @Expose
        private Integer fORM2ACABID;
        @SerializedName("EVALUACION_NRO")
        @Expose
        private String eVALUACIONNRO;
        @SerializedName("SINPAD_NRO")
        @Expose
        private String sINPADNRO;
        @SerializedName("PELIGRO_TIPO")
        @Expose
        private String pELIGROTIPO;
        @SerializedName("EMPADRONAMIENTO_FECHA_HORA")
        @Expose
        private String eMPADRONAMIENTOFECHAHORA;
        @SerializedName("OCURRENCIA_FECHA_HORA")
        @Expose
        private String oCURRENCIAFECHAHORA;
        @SerializedName("DEPARTAMENTO")
        @Expose
        private String dEPARTAMENTO;
        @SerializedName("PROVINCIA")
        @Expose
        private String pROVINCIA;
        @SerializedName("DISTRITO")
        @Expose
        private String dISTRITO;
        @SerializedName("LOCALIDAD")
        @Expose
        private String lOCALIDAD;
        @SerializedName("SECTOR")
        @Expose
        private String sECTOR;
        @SerializedName("CALLE")
        @Expose
        private String cALLE;
        @SerializedName("PISO")
        @Expose
        private String pISO;
        @SerializedName("CENTRO_POBLADO")
        @Expose
        private String cENTROPOBLADO;
        @SerializedName("CASERIO")
        @Expose
        private String cASERIO;
        @SerializedName("ANEXO")
        @Expose
        private String aNEXO;
        @SerializedName("HOJA_NRO")
        @Expose
        private String hOJANRO;
        @SerializedName("OTROS")
        @Expose
        private String oTROS;
        @SerializedName("CREATED_AT")
        @Expose
        private String cREATEDAT;

        public Integer getFORM2ACABID() {
            return fORM2ACABID;
        }

        public void setFORM2ACABID(Integer fORM2ACABID) {
            this.fORM2ACABID = fORM2ACABID;
        }

        public String getEVALUACIONNRO() {
            return eVALUACIONNRO;
        }

        public void setEVALUACIONNRO(String eVALUACIONNRO) {
            this.eVALUACIONNRO = eVALUACIONNRO;
        }

        public String getSINPADNRO() {
            return sINPADNRO;
        }

        public void setSINPADNRO(String sINPADNRO) {
            this.sINPADNRO = sINPADNRO;
        }

        public String getPELIGROTIPO() {
            return pELIGROTIPO;
        }

        public void setPELIGROTIPO(String pELIGROTIPO) {
            this.pELIGROTIPO = pELIGROTIPO;
        }

        public String getEMPADRONAMIENTOFECHAHORA() {
            return eMPADRONAMIENTOFECHAHORA;
        }

        public void setEMPADRONAMIENTOFECHAHORA(String eMPADRONAMIENTOFECHAHORA) {
            this.eMPADRONAMIENTOFECHAHORA = eMPADRONAMIENTOFECHAHORA;
        }

        public String getOCURRENCIAFECHAHORA() {
            return oCURRENCIAFECHAHORA;
        }

        public void setOCURRENCIAFECHAHORA(String oCURRENCIAFECHAHORA) {
            this.oCURRENCIAFECHAHORA = oCURRENCIAFECHAHORA;
        }

        public String getDEPARTAMENTO() {
            return dEPARTAMENTO;
        }

        public void setDEPARTAMENTO(String dEPARTAMENTO) {
            this.dEPARTAMENTO = dEPARTAMENTO;
        }

        public String getPROVINCIA() {
            return pROVINCIA;
        }

        public void setPROVINCIA(String pROVINCIA) {
            this.pROVINCIA = pROVINCIA;
        }

        public String getDISTRITO() {
            return dISTRITO;
        }

        public void setDISTRITO(String dISTRITO) {
            this.dISTRITO = dISTRITO;
        }

        public String getLOCALIDAD() {
            return lOCALIDAD;
        }

        public void setLOCALIDAD(String lOCALIDAD) {
            this.lOCALIDAD = lOCALIDAD;
        }

        public String getSECTOR() {
            return sECTOR;
        }

        public void setSECTOR(String sECTOR) {
            this.sECTOR = sECTOR;
        }

        public String getCALLE() {
            return cALLE;
        }

        public void setCALLE(String cALLE) {
            this.cALLE = cALLE;
        }

        public String getPISO() {
            return pISO;
        }

        public void setPISO(String pISO) {
            this.pISO = pISO;
        }

        public String getCENTROPOBLADO() {
            return cENTROPOBLADO;
        }

        public void setCENTROPOBLADO(String cENTROPOBLADO) {
            this.cENTROPOBLADO = cENTROPOBLADO;
        }

        public String getCASERIO() {
            return cASERIO;
        }

        public void setCASERIO(String cASERIO) {
            this.cASERIO = cASERIO;
        }

        public String getANEXO() {
            return aNEXO;
        }

        public void setANEXO(String aNEXO) {
            this.aNEXO = aNEXO;
        }

        public String getHOJANRO() {
            return hOJANRO;
        }

        public void setHOJANRO(String hOJANRO) {
            this.hOJANRO = hOJANRO;
        }

        public String getOTROS() {
            return oTROS;
        }

        public void setOTROS(String oTROS) {
            this.oTROS = oTROS;
        }

        public String getCREATEDAT() {
            return cREATEDAT;
        }

        public void setCREATEDAT(String cREATEDAT) {
            this.cREATEDAT = cREATEDAT;
        }
    }
}
