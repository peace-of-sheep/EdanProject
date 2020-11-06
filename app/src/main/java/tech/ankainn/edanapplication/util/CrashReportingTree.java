package tech.ankainn.edanapplication.util;

import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

public class CrashReportingTree extends Timber.Tree {

    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_TAG = "tag";
    @Override
    protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();

        crashlytics.setCustomKey(KEY_PRIORITY, priority);

        if (tag != null) {
            crashlytics.setCustomKey(KEY_TAG, tag);
        }

        crashlytics.log(message);

        if (t != null) {
            crashlytics.recordException(t);
        }
    }
}
