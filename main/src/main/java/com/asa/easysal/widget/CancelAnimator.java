package com.asa.easysal.widget;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by aaron on 8/18/15.
 */
abstract class CancelAnimator {
    private static final long DURATION = 300L;

    abstract void show(@NonNull View view);

    abstract void hide(@NonNull View view);


    static CancelAnimator getAnimator() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) ?
                new CancelAnimator11() : new CancelAnimatorCompat();
    }

    static class CancelAnimatorCompat extends CancelAnimator {

        @Override
        public void show(@NonNull View view) {
            view.setVisibility(View.VISIBLE);
        }

        @Override
        public void hide(@NonNull View view) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("NewApi")
    static class CancelAnimator11 extends CancelAnimator {
        @Override
        public void show(@NonNull final View view) {
            view.animate().setDuration(DURATION).alpha(1F).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    view.setAlpha(0F);
                    view.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        @Override
        public void hide(@NonNull final View view) {
            view.animate().setDuration(DURATION).alpha(0F).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    view.setAlpha(1F);
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

}
