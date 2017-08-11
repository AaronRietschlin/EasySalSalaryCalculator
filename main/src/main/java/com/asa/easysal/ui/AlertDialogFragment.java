package com.asa.easysal.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class AlertDialogFragment extends DialogFragment {
    private static final String EXTRA_TITLE = "title";
    private static final String EXTRA_MESSAGE = "message";
    private static final String EXTRA_POSITIVE_BUTTON = "positive_button";
    private static final String EXTRA_NEGATIVE_BUTTON = "negative_button";
    private static final String EXTRA_NEUTRAL_BUTTON = "neutral_button";
    private static boolean useNeutralButton;
    private static boolean useNegativeButton;
    private static boolean showMessage;
    private OnClickListener positiveListener;
    private OnClickListener negativeListener;
    private OnClickListener neutralListener;
    private OnDialogCanceledListener onCanceledListener;
    private int titleId;
    private String titleString;
    private int messageId;
    private String messageString;
    private int positiveButtonStringId;
    private int negativeButtonStringId;
    private int neutralButtonStringId;
    private int iconDrawableId;
    private Drawable iconDrawable;
    private View mView;
    private boolean viewIsSet;
    private boolean useStringTitle;
    private boolean useStringMessage;

    /**
     * Returns an instance of the AlertDialogFragment. This will construct a
     * Dialog that has one button: a positive. Be sure to set the listeners.
     *
     * @param titleId                The resource ID of the title
     * @param messageId              The resource ID of the message
     * @param positiveButtonStringId The resource id of the positive button
     * @return
     */
    public static AlertDialogFragment newInstance(int titleId, int messageId,
                                                  int positiveButtonStringId) {
        AlertDialogFragment f = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_TITLE, titleId);
        args.putInt(EXTRA_MESSAGE, messageId);
        args.putInt(EXTRA_POSITIVE_BUTTON, positiveButtonStringId);
        f.setArguments(args);
        showMessage = true;
        return f;
    }

    /**
     * Returns an instance of the AlertDialogFragment. This will construct a
     * Dialog that has two buttons, a positive and a negative. Be sure to set
     * the listeners. By default, the negative listener will dismiss the dialog.
     *
     * @param titleId                The resource ID of the title
     * @param messageId              The resource ID of the message
     * @param positiveButtonStringId The resource id of the positive button
     * @param negativeButtonStringId The resource if of the negative button
     * @return
     */
    public static AlertDialogFragment newInstance(int titleId, int messageId,
                                                  int positiveButtonStringId, int negativeButtonStringId) {
        AlertDialogFragment f = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_TITLE, titleId);
        args.putInt(EXTRA_MESSAGE, messageId);
        args.putInt(EXTRA_POSITIVE_BUTTON, positiveButtonStringId);
        args.putInt(EXTRA_NEGATIVE_BUTTON, negativeButtonStringId);
        f.setArguments(args);
        showMessage = true;
        return f;
    }

    /**
     * Returns an instance of the AlertDialogFragment. This will construct a
     * Dialog that has three buttons: a positive, a neutral and a negative. Be
     * sure to set the listeners. By default, the negative listener will dismiss
     * the dialog.
     *
     * @param titleId                The resource ID of the title
     * @param messageId              The resource ID of the message
     * @param positiveButtonStringId The resource id of the positive button
     * @param negativeButtonStringId The resource if of the negative button
     * @param neutralButtonStringId  The resource if of the neutral button
     * @return
     */
    public static AlertDialogFragment newInstance(int titleId, int messageId,
                                                  int positiveButtonStringId, int negativeButtonStringId,
                                                  int neutralButtonStringId) {
        AlertDialogFragment f = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_TITLE, titleId);
        args.putInt(EXTRA_MESSAGE, messageId);
        args.putInt(EXTRA_POSITIVE_BUTTON, positiveButtonStringId);
        args.putInt(EXTRA_NEGATIVE_BUTTON, negativeButtonStringId);
        args.putInt(EXTRA_NEUTRAL_BUTTON, neutralButtonStringId);
        f.setArguments(args);
        showMessage = true;
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        titleId = extras.getInt(EXTRA_TITLE);
        messageId = extras.getInt(EXTRA_MESSAGE);
        positiveButtonStringId = extras.getInt(EXTRA_POSITIVE_BUTTON);
        negativeButtonStringId = extras.getInt(EXTRA_NEGATIVE_BUTTON);
        if (negativeButtonStringId != 0)
            useNegativeButton = true;
        else
            useNegativeButton = false;
        neutralButtonStringId = extras.getInt(EXTRA_NEUTRAL_BUTTON);
        if (neutralButtonStringId != 0)
            useNeutralButton = true;
        else
            useNeutralButton = false;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        // Set the title based on whether or not we should be using a string
        if (!useStringTitle)
            dialog.setTitle(titleId);
        else
            dialog.setTitle(titleString);
        if (showMessage) {
            // Set the message based on whether or not we should be using a
            // string
            if (!useStringMessage)
                dialog.setMessage(messageId);
            else
                dialog.setMessage(messageString);
        }
        // Set the icon drawable (if present)
        if (iconDrawable != null)
            dialog.setIcon(iconDrawable);
        else if (iconDrawableId != 0)
            dialog.setIcon(iconDrawableId);
        if (viewIsSet)
            dialog.setView(mView);
        dialog.setPositiveButton(positiveButtonStringId, positiveListener);
        if (useNegativeButton)
            dialog.setNegativeButton(negativeButtonStringId, negativeListener);
        if (useNeutralButton)
            dialog.setNeutralButton(neutralButtonStringId, neutralListener);
        return dialog.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (onCanceledListener != null)
            onCanceledListener.dialogCanceled();
    }

    /**
     * Sets the positive button click listener.
     *
     * @param l The listener that is called when the positive button is
     *          clicked
     * @return This FragmentAlertDialog object to allow for chaining of calls to
     * set methods.
     */
    public AlertDialogFragment setPositiveButtonClickListener(OnClickListener l) {
        positiveListener = l;
        return this;
    }

    /**
     * Sets the negative button click listener.
     *
     * @param l The listener that is called when the negative button is
     *          clicked
     * @return This FragmentAlertDialog object to allow for chaining of calls to
     * set methods.
     */
    public AlertDialogFragment setNegativeButtonClickListener(OnClickListener l) {
        negativeListener = l;
        return this;
    }

    /**
     * Sets the neutral button click listener. You must set this if you set
     * {@link AlertDialogFragment#useNeutralButton(boolean)}.
     *
     * @param l The listener that is called when the neutral button is clicked
     * @return This FragmentAlertDialog object to allow for chaining of calls to
     * set methods.
     */
    public AlertDialogFragment setNeutralButtonClickListener(OnClickListener l) {
        neutralListener = l;
        return this;
    }

    /**
     * Sets the text that appears on the Positive Button.
     *
     * @param id
     * @return
     */
    public AlertDialogFragment setPositiveButtonText(int id) {
        positiveButtonStringId = id;
        return this;
    }

    /**
     * Sets the that appears on the Negative Button.
     *
     * @param id
     * @return
     */
    public AlertDialogFragment setNegativeButtonText(int id) {
        negativeButtonStringId = id;
        return this;
    }

    /**
     * Sets the that appears on the Neutral Button.
     *
     * @param id
     * @return
     */
    public AlertDialogFragment setNeutralButtonText(int id) {
        neutralButtonStringId = id;
        return this;
    }

    /**
     * Sets whether or not a neutral button is used. If set to true, you must
     * set the NeutralButton click listener using
     * {@link #setNeutralButtonClickListener(android.content.DialogInterface.OnClickListener)}
     *
     * @param b
     * @return
     */
    public AlertDialogFragment useNeutralButton(boolean b) {
        useNeutralButton = b;
        return this;
    }

    /**
     * Sets whether or not a negative button is used. If set to true, you must
     * set the negative button click listener using
     * {@link #setNegativeButtonClickListener(android.content.DialogInterface.OnClickListener)}
     *
     * @param b
     * @return
     */
    public AlertDialogFragment useNegativeButton(boolean b) {
        useNegativeButton = b;
        return this;
    }

    public AlertDialogFragment setView(View v, boolean b) {
        mView = v;
        viewIsSet = true;
        showMessage = b;
        return this;
    }

    /**
     * Sets the title to appear in the title position. When setting this, it
     * sets the {@link #useStringTitle} so the {@link #onCreateDialog(android.os.Bundle)}
     * can use the correct method of setting the title.
     *
     * @param titleString The String that will appear in the title.
     */
    public AlertDialogFragment setTitleString(String titleString) {
        this.titleString = titleString;
        useStringTitle = true;
        return this;
    }

    /**
     * Sets the message to appear in the message position. When setting this, it
     * sets the {@link #useStringMessage} so the {@link #onCreateDialog(android.os.Bundle)}
     * can use the correct method of setting the message.
     *
     * @param messageString The String that will appear in the message.
     */
    public AlertDialogFragment setMessageString(String messageString) {
        this.messageString = messageString;
        useStringMessage = true;
        return this;
    }

    /**
     * Sets the icon drawable that will be used in the title. If you set this,
     * it WILL show up. You cannot cancel it.
     *
     * @param iconDrawableId
     */
    public AlertDialogFragment setIcon(int iconDrawableId) {
        this.iconDrawableId = iconDrawableId;
        return this;
    }

    /**
     * Sets the icon drawable that will be used in the title. If you set this,
     * it WILL show up. You cannot cancel it.
     *
     * @param iconDrawableId
     */
    public AlertDialogFragment setIcon(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
        return this;
    }

    /**
     * Set what happens when the dialog is canceled.
     *
     * @param cancelListener
     * @return
     */
    public AlertDialogFragment setOnCanceledListener(
            OnDialogCanceledListener cancelListener) {
        this.onCanceledListener = cancelListener;
        return this;
    }

    /**
     * A listener that allows you to perform an action that occurs when the
     * dialog is canceled.
     */
    public interface OnDialogCanceledListener {
        public abstract void dialogCanceled();
    }

}
