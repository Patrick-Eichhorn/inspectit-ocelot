package rocks.inspectit.ocelot.core.instrumentation;

import lombok.extern.slf4j.Slf4j;

/**
 * @TODO This is a really dirty fix to avoid Wildfly crashes in MSG Applications. InspectIT wrapping approach does not work for custom implementations of ExecutorServices.
 */
@Slf4j
public class PreventWildflyMangedFutureTaskWrapping {

    private static Class<?> MFG_INSTANCE = null;
    private static final Object lock = new Object();

    private static Class<?> makeMfgInstance(Object r) {
        if (MFG_INSTANCE == null) {
            if (r.getClass().getName().startsWith("org.glassfish.enterprise")) {
                synchronized (lock) {
                    if (MFG_INSTANCE == null) {
                        try {
                            MFG_INSTANCE = Class.forName("org.glassfish.enterprise.concurrent.internal.ManagedFutureTask",
                                    false,
                                    Thread.currentThread().getContextClassLoader());
                        } catch (ClassNotFoundException e) {
                            log.debug("Failed to create ManagedFutureTask instance.");
                        }
                    }
                }
            }
        }
        return MFG_INSTANCE;
    }

    public static boolean isMangedFutureTask(Object o) {
        final Class<?> aClass = makeMfgInstance(o);
        if (aClass != null && aClass.isAssignableFrom(o.getClass())) {
            log.debug("Skip wrapping for: " + o.getClass().getName());
            return true;
        }
        return false;
    }
}
