package tech.ankainn.edanapplication.util;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import timber.log.Timber;

public class ViewBindingUtil {

    @NotNull
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static <VB extends ViewBinding> VB inflate(Class<?> clazz,
                                                      LayoutInflater inflater,
                                                      ViewGroup container,
                                                      boolean attach) {
        try {
            final Type type = clazz.getGenericSuperclass();
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
