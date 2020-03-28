package tech.ankainn.edanapplication.textinput;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Locale;

public class TextInputViewModel extends ViewModel {

    public static final int NO_VIEW = 0;

    private final Data defaultData = new Data(NO_VIEW, "");

    private MutableLiveData<Data> input = new MutableLiveData<>(defaultData);

    public LiveData<Data> getInput() {
        return input;
    }

    public void setText(CharSequence text) {
        Data temp = input.getValue() != null ? input.getValue() : defaultData;

        if(temp.viewId != NO_VIEW) {
            temp.text = text;
            input.postValue(temp);
        }
    }

    public boolean tryOpenInputFragment(int viewId) {
        Data temp = input.getValue() != null ? input.getValue() : defaultData;

        if(temp.viewId == NO_VIEW) {
            Data data = new Data(viewId, temp.text);
            input.setValue(data);
            return true;
        }

        return false;
    }

    void clearData() {
        input.setValue(defaultData);
    }

    public static class Data {
        public int viewId;
        public CharSequence text;

        Data(int viewId, CharSequence text) {
            this.viewId = viewId;
            this.text = text;
        }

        @NonNull
        @Override
        public String toString() {
            return String.format(Locale.getDefault() ,"Data[%s] viewId={%d} text={%s}", hashCode(), viewId, text);
        }
    }
}