package com.jerry.payment.mobile.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.common.Env;
import com.jerry.payment.mobile.utils.DisplayUtils;

/**
 * 自定义Dialog
 *
 * <p>
 *    CustomDialog.Buidler builder = new CustomDialog.Buidler(context);
 *    builder.setTitle("title").setMessage("message").create().show();
 * </p>
 * Created by jerry on 2016/7/29.
 */
public class CustomDialog extends Dialog {

    private final int GAP = 20;

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void resizeDialog(Dialog dialog){
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.CustomDialogAnimation);
        WindowManager.LayoutParams lps = window.getAttributes();
        lps.width = Env.screenWidth - DisplayUtils.convertDIP2PX(dialog.getContext(), GAP) * 2;
        lps.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lps);
    }

    @Override
    public void show() {
        resizeDialog(this);
        super.show();
    }

    public static class Builder {
        private Context context;
        private String result;    //结果文字
        private String message;    //结果内容
        private String positiveButtonText;    //确认按钮文字
        private View contentView;    //对话框中部内容View
        private DialogInterface.OnClickListener positiveButtonClickListener; //确认按钮回调

        public Builder(Context mCtx) {
            context = mCtx;
        }

        public Builder setResult(String mResult) {
            this.result = mResult;
            return this;
        }

        public Builder setMessage(String msg) {
            this.message = msg;
            return this;
        }

        public Builder setContentView(View v) {
            contentView = v;
            return this;
        }

        public Builder setPositiveButton(String btnText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = btnText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        private void buildDialog(final CustomDialog dialog) {
            View view = LayoutInflater.from(context).inflate(R.layout.app_custom_dialog, null);
            dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            TextView resultTV = (TextView) view.findViewById(R.id.dialog_result);
            TextView messageTV = (TextView) view.findViewById(R.id.dialog_message);
            TextView positiveTV = (TextView) view.findViewById(R.id.dialog_positive_btn);
            LinearLayout contentLL = (LinearLayout) view.findViewById(R.id.dialog_content);

            if(TextUtils.isEmpty(result)){
                resultTV.setVisibility(View.GONE);
            }else {
                resultTV.setText(result);
            }

            if(TextUtils.isEmpty(message)){
                messageTV.setVisibility(View.GONE);
            }else {
                messageTV.setText(message);
            }

            if(TextUtils.isEmpty(positiveButtonText)){
                positiveTV.setVisibility(View.GONE);
            }else {
                positiveTV.setText(positiveButtonText);
                positiveTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(positiveButtonClickListener != null){
                            positiveButtonClickListener.onClick(dialog, BUTTON_POSITIVE);
                        }else {
                            dialog.dismiss();
                        }
                    }
                });
            }

            if(contentView == null){
                contentLL.setVisibility(View.GONE);
            }else {
                RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                contentLL.addView(contentView, lps);
            }
        }

        public CustomDialog build(){
            CustomDialog dialog = new CustomDialog(context, R.style.CustomDialog);
            buildDialog(dialog);
            return dialog;
        }

    }
}
