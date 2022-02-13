/*
 * Copyright 2022 Junyu Long
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.junyulong.ecc.core.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class TextEditorDialogBuilder {
    private final AlertDialog.Builder builder;
    private final Context context;
    private EditText editText;
    private boolean canceledOnTouchOutside = true;
    private boolean enterAsPositive = false;

    /**
     * Creates a builder for an alert dialog that uses an explicit theme
     * resource.
     * <p>
     * The specified theme resource ({@code themeResId}) is applied on top
     * of the parent {@code context}'s theme. It may be specified as a
     * style resource containing a fully-populated theme, such as
     * {@link android.R.style#Theme_Material_Dialog}, to replace all
     * attributes in the parent {@code context}'s theme including primary
     * and accent colors.
     * <p>
     * To preserve attributes such as primary and accent colors, the
     * {@code themeResId} may instead be specified as an overlay theme such
     * as {@link android.R.style#ThemeOverlay_Material_Dialog}. This will
     * override only the window attributes necessary to style the alert
     * window as a dialog.
     * <p>
     * Alternatively, the {@code themeResId} may be specified as {@code 0}
     * to use the parent {@code context}'s resolved value for
     * {@link android.R.attr#alertDialogTheme}.
     *
     * @param context    the parent context
     * @param themeResId the resource ID of the theme against which to inflate
     *                   this dialog, or {@code 0} to use the parent
     *                   {@code context}'s default alert dialog theme
     */
    public TextEditorDialogBuilder(@NonNull Context context, @StyleRes int themeResId) {
        builder = new AlertDialog.Builder(context, themeResId);
        this.context = builder.getContext();
        builder.setView(createView());
    }

    /**
     * Creates a builder for an alert dialog that uses the default alert
     * dialog theme.
     * <p>
     * The default alert dialog theme is defined by
     * {@link android.R.attr#alertDialogTheme} within the parent
     * {@code context}'s theme.
     *
     * @param context the parent context
     */
    public TextEditorDialogBuilder(@NonNull Context context) {
        this(context, THEME_DEVICE_DEFAULT_DARK);
    }

    private View createView() {
        FrameLayout layout = new FrameLayout(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        if (editText == null) {
            editText = new EditText(context);
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            editParams.gravity = Gravity.CENTER;
        }
        layout.addView(editText);

        return layout;
    }

    public EditText getEditView() {
        return this.editText;
    }

    public TextEditorDialogBuilder setCustomEdit(EditText editText) {
        this.editText = editText;
        builder.setView(createView());
        return this;
    }

    public TextEditorDialogBuilder setInputType(int type) {
        editText.setInputType(type);
        return this;
    }

    public TextEditorDialogBuilder setText(String str) {
        editText.setText(str);
        return this;
    }

    public TextEditorDialogBuilder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public TextEditorDialogBuilder setEnterAsPositive(boolean enterAsPositive) {
        this.enterAsPositive = enterAsPositive;
        return this;
    }

    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setSelection(editText.getText().toString().length());
        editText.postDelayed(() -> {
            InputMethodManager inputManager =
                    (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }, 50);
    }

    /**
     * Returns a {@link Context} with the appropriate theme for dialogs created by this Builder.
     * Applications should use this Context for obtaining LayoutInflaters for inflating views
     * that will be used in the resulting dialogs, as it will cause views to be inflated with
     * the correct theme.
     *
     * @return A Context for built Dialogs.
     */
    public Context getContext() {
        return builder.getContext();
    }

    /**
     * Set the title displayed in the {@link Dialog}.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public TextEditorDialogBuilder setTitle(CharSequence title) {
        builder.setTitle(title);
        return this;
    }

    /**
     * Set the title using the custom view {@code customTitleView}.
     * <p>
     * The methods {@link #setTitle(CharSequence)} and {@link #setIcon(Drawable)} should
     * be sufficient for most titles, but this is provided if the title
     * needs more customization. Using this will replace the title and icon
     * set via the other methods.
     * <p>
     * <strong>Note:</strong> To ensure consistent styling, the custom view
     * should be inflated or constructed using the alert dialog's themed
     * context obtained via {@link #getContext()}.
     *
     * @param customTitleView the custom view to use as the title
     * @return this Builder object to allow for chaining of calls to set
     * methods
     */
    public TextEditorDialogBuilder setCustomTitle(View customTitleView) {
        builder.setCustomTitle(customTitleView);
        return this;
    }

    /**
     * Set the message to display.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public TextEditorDialogBuilder setMessage(CharSequence message) {
        builder.setMessage(message);
        return this;
    }

    /**
     * Set the {@link Drawable} to be used in the title.
     * <p>
     * <strong>Note:</strong> To ensure consistent styling, the drawable
     * should be inflated or constructed using the alert dialog's themed
     * context obtained via {@link #getContext()}.
     *
     * @return this Builder object to allow for chaining of calls to set
     * methods
     */
    public TextEditorDialogBuilder setIcon(Drawable icon) {
        builder.setIcon(icon);
        return this;
    }

    /**
     * Set a listener to be invoked when the positive button of the dialog is pressed.
     *
     * @param text     The text to display in the positive button
     * @param listener The {@link OnTextConfirmedListener} to use.
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public TextEditorDialogBuilder setPositiveButton(CharSequence text, final OnTextConfirmedListener listener) {
        builder.setPositiveButton(text, listener == null ? null :
                (DialogInterface.OnClickListener) (dialog, which) -> {
                    listener.onTextConfirmed(dialog, editText, editText.getText().toString());
                });
        return this;
    }

    /**
     * Set a listener to be invoked when the negative button of the dialog is pressed.
     *
     * @param text     The text to display in the negative button
     * @param listener The {@link TextEditorDialogBuilder.OnTextCanceledListener} to use.
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public TextEditorDialogBuilder setNegativeButton(CharSequence text, final OnTextCanceledListener listener) {
        builder.setNegativeButton(text, listener == null ? null :
                (DialogInterface.OnClickListener) (dialog, which) -> {
                    listener.onTextCanceled(dialog, editText);
                });
        return this;
    }

    /**
     * Sets whether the dialog is cancelable or not.  Default is true.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public TextEditorDialogBuilder setCancelable(boolean cancelable) {
        builder.setCancelable(cancelable);
        return this;
    }

    /**
     * Sets the callback that will be called if the dialog is canceled.
     *
     * <p>Even in a cancelable dialog, the dialog may be dismissed for reasons other than
     * being canceled or one of the supplied choices being selected.
     * If you are interested in listening for all cases where the dialog is dismissed
     * and not just when it is canceled, see
     * {@link #setOnDismissListener(android.content.DialogInterface.OnDismissListener) setOnDismissListener}.</p>
     *
     * @return This Builder object to allow for chaining of calls to set methods
     * @see #setCancelable(boolean)
     * @see #setOnDismissListener(android.content.DialogInterface.OnDismissListener)
     */
    public TextEditorDialogBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        builder.setOnCancelListener(onCancelListener);
        return this;
    }

    /**
     * Sets the callback that will be called when the dialog is dismissed for any reason.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public TextEditorDialogBuilder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        builder.setOnDismissListener(onDismissListener);
        return this;
    }

    /**
     * Sets the callback that will be called if a key is dispatched to the dialog.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public TextEditorDialogBuilder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        builder.setOnKeyListener(onKeyListener);
        return this;
    }

    /**
     * Creates an {@link TextEditorDialogBuilder} with the arguments supplied to this
     * builder.
     * <p>
     * Calling this method does not display the dialog. If no additional
     * processing is needed, {@link #show()} may be called instead to both
     * create and display the dialog.
     */
    public AlertDialog create() {
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> {
            showSoftInputFromWindow(editText);
        });
        alertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        if (enterAsPositive) {
            editText.setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    alertDialog.getButton(BUTTON_POSITIVE).performClick();
                    return true;
                } else
                    return false;
            });
        }
        return alertDialog;
    }

    /**
     * Creates an {@link AlertDialog} with the arguments supplied to this
     * builder and immediately displays the dialog.
     * <p>
     * Calling this method is functionally identical to:
     * <pre>
     *     AlertDialog dialog = builder.create();
     *     dialog.show();
     * </pre>
     */
    public AlertDialog show() {
        final AlertDialog dialog = create();
        dialog.show();
        return dialog;
    }

    public interface OnTextConfirmedListener {
        void onTextConfirmed(DialogInterface dialog, EditText editText, String text);
    }

    public interface OnTextCanceledListener {
        void onTextCanceled(DialogInterface dialog, EditText editText);
    }
}
