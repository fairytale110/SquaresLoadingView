package tech.nicesky.libsquaresloadingview;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;

/**
* @class tech.nicesky.libsquaresloadingview.ValueAnimatorUtil
* @date on 2018/9/19-21:31
* @author fairytale110
* @email  fairytale110@foxmail.com
* @description:
*
*/
public class ValueAnimatorUtil {
    /**
     * 如果动画被禁用，则重置动画缩放时长
     * If the system animation is disabled, reset the zooming time of the app's animation
     */
    public static void resetDurationScaleIfDisable() {
        if (getDurationScale() == 0)
            resetDurationScale();
    }

    /**
     * Reset the app's animation zoom time
     */
    public static void resetDurationScale() {
        try {
            getField().setFloat(null, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static float getDurationScale() {
        try {
            return getField().getFloat(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @NonNull
    private static Field getField() throws NoSuchFieldException {
        Field field = ValueAnimator.class.getDeclaredField("sDurationScale");
        field.setAccessible(true);
        return field;
    }
}
