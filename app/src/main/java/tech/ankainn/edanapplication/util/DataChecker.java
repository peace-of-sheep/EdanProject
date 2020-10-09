package tech.ankainn.edanapplication.util;

import tech.ankainn.edanapplication.model.app.formOne.FormOneData;

public class DataChecker {

    private boolean flagError;

    private String strError;

    public DataChecker(FormOneData internalForm) {
        
    }

    public boolean haveErrors() {
        return flagError;
    }

    public String getErrorStr() {
        return strError;
    }
}
