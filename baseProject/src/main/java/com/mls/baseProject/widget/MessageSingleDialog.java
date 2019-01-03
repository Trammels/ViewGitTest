package com.mls.baseProject.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.mls.baseProject.R;


public class MessageSingleDialog extends Dialog {

    public static MessageDialogListener messageDialogListener;
    private MessageSingleDialog dialog;
    private Context mContext;

    public MessageSingleDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public MessageSingleDialog(Context context) {
        super(context);
        this.mContext = context;

    }

    public MessageSingleDialog getDialog(String title, String content) {
        Builder b = new Builder(mContext);
        b.setMessage(title, content);
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
        private TextView txtContent;
        private String title;
        private String content;

        public Builder(Context context) {
            this.context = context;
        }

        public void setMessage(String title, String content) {
            this.title = title;
            this.content = content;
        }

        /**
         * Create the custom dialog
         */
        @SuppressLint("Override")
        public MessageSingleDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final MessageSingleDialog dialog = new MessageSingleDialog(context, R.style.Dialog);
            dialog.setCanceledOnTouchOutside(false);
            View layout = inflater.inflate(R.layout.view_message_single_dialog, null);
            txtName = (TextView) layout.findViewById(R.id.txt_name);
            txtContent = (TextView) layout.findViewById(R.id.txt_content);
            txtName.setText(title);
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