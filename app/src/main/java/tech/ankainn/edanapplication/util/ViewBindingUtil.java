package tech.ankainn.edanapplication.util;

import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import tech.ankainn.edanapplication.ui.common.BindingAdapter;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import timber.log.Timber;

public class ViewBindingUtil {

    @NotNull
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static <VB extends ViewBinding> VB inflate(Class<?> clazz,
                                                      LayoutInflater inflater,
                                                      ViewGroup container,
                                                      boolean attach) {
        try {
            Timber.tag(Tagger.DUMPER).i("inflate: %s", clazz);
            final Type type = clazz.getGenericSuperclass();
            Timber.tag(Tagger.DUMPER).d("inflate: %s", type);
            final Class<VB> bindingClazz = (Class<VB>) ((ParameterizedType) type).getActualTypeArguments()[0];
            final Method inflateMethod = bindingClazz
                    .getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            return (VB) inflateMethod.invoke(null, inflater, container, attach);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Timber.wtf(e, "This shouldn't happen!!");
        }
        throw new RuntimeException("Call the police!!");
    }
}
