package com.mls.baseProject.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.mls.baseProject.R;


public class MessageTwoSingleDialog extends Dialog {

    public static MessageDialogListener messageDialogListener;
    private MessageTwoSingleDialog dialog;
    private Context mContext;

    public MessageTwoSingleDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public MessageTwoSingleDialog(Context context) {
        super(context);
        this.mContext = context;

    }

    public MessageTwoSingleDialog getDialog(String title, String name, String content) {
        Builder b = new Builder(mContext);
        b.setMessage(title, name, content);
        dialog = b.create();
        dialog.show();
        return dialog;
    }

    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {
        private Context context;
        private TextView txtName;
        private TextView txtTitle;
        private TextView txtContent;
        private String title;
        private String name;
        private String content;

        public Builder(Context context) {
            this.context = context;
        }

        public void setMessage(String name, String title, String content) {
            this.name = name;
            this.title = title;
            this.content = content;
        }

        /**
         * Create the custom dialog
         */
        @SuppressLint("Override")
        public MessageTwoSingleDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final MessageTwoSingleDialog dialog = new MessageTwoSingleDialog(context, R.style.Dialog);
            dialog.setCanceledOnTouchOutside(false);
            View layout = inflater.inflate(R.layout.view_message_two_single_dialog, null);
            txtName = (TextView) layout.findViewById(R.id.txt_name);
            txtTitle = (TextView) layout.findViewById(R.id.txt_title);
            txtContent = (TextView) layout.findViewById(R.id.txt_content);
            txtName.setText(name);
            txtTitle.setText(title);
            txtContent.setText(content);
            layout.findViewById(R.id.txt_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (messageDialogListener != null) {
                        messageDialogListener.cancel();
                    }
                    dialog.dismiss();
                }
            });

            dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            return dialog;
        }

    }

    public interface MessageDialogListener {
        void sure();

        void cancel();
    }

    public MessageDialogListener getClearDataListener() {
        return messageDialogListener;
    }

    public void seteditDialogListener(MessageDialogListener messageDialogListener) {
        this.messageDialogListener = messageDialogListener;
    }

}