package com.mls.baseProject.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;

import com.mls.baseProject.R;
import com.mls.baseProject.application.AppContext;


/**
 */
public class EditLowDialog extends Dialog {
    public static EditDialogListener editDialogListener;
    private EditLowDialog dialog;
    Builder b;
    private Context mContext;

    public EditLowDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public EditLowDialog(Context context) {
        super(context);
        this.mContext = context;

    }

    public EditLowDialog getDialog(String title, String hintContent, String content) {
        b = new Builder(mContext);
        b.setMessage(title, hintContent, content);
        dialog = b.create();
        dialog.show();
        return dialog;
    }

    public EditLowDialog getDialog(String title) {
        b = new Builder(mContext);
        b.setMessage(title, "", "");
        dialog = b.create();
        dialog.show();
        return dialog;
    }

    public Builder getBuilder() {
        return b;
    }


    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {
        private TextView txtName;
        private TextView txtContent;
        private EditText editContent;
        private Context context;
        private String title, content, hintContent;

        public Builder(Context context) {
            this.context = context;
        }

        public void setMessage(String title, String hintContent, String content) {
            this.title = title;
            this.content = content;
            this.hintContent = hintContent;
        }


        public void setEidInputType(int inputType) {
            editContent.setInputType(inputType);
        }

        /**
         * Create the custom dialog
         */
        @SuppressLint("Override")
        public EditLowDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final EditLowDialog dialog = new EditLowDialog(context,
                    R.style.Dialog);
            dialog.setCanceledOnTouchOutside(false);
            View layout = inflater.inflate(R.layout.view_edit_dialog, null);
            txtName = (TextView) layout.findViewById(R.id.txt_name);
            txtContent = (TextView) layout.findViewById(R.id.txt_content);
            editContent = (EditText) layout.findViewById(R.id.edt_content);
            editContent.setHint(hintContent);
            editContent.setText(content);
//            editContent.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if (count != before) {
//                        String sss = "";
//                        String string = s.toString().replace(",", "");
//                        int b = string.length() / 3;
//                        if (string.length() >= 3) {
//                            int yushu = string.length() % 3;
//                            if (yushu == 0) {
//                                b = string.length() / 3 - 1;
//                                yushu = 3;
//                            }
//                            for (int i = 0; i < b; i++) {
//                                sss = sss + string.substring(0, yushu) + "," + string.substring(yushu, 3);
//                                string = string.substring(3, string.length());
//                            }
//                            sss = sss + string;
//                            editContent.setText(sss);
//                        }
//                    }
//                    editContent.setSelection(editContent.getText().length());
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });
            txtName.setText(title);
            txtContent.setText(content);

            layout.findViewById(R.id.txt_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editDialogListener != null) {
                        editDialogListener.cancel(dialog);
                    }
                    dialog.dismiss();
                }
            });
            layout.findViewById(R.id.txt_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String editContent = Builder.this.editContent.getText().toString().trim();
                    if (TextUtils.isEmpty(editContent)) {
                        AppContext.showToast("请输入");
                        return;
                    }
                    if (editDialogListener != null) {
                        editDialogListener.sure(editContent,dialog);

                    }
                }
            });
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            return dialog;
        }

    }

    public interface EditDialogListener {
        void sure(String editContent, EditLowDialog dialog);

        void cancel(EditLowDialog dialog);
    }


    public EditDialogListener getClearDataListener() {
        return editDialogListener;
    }

    public void seteditDialogListener(EditDialogListener editDialogListener) {
        this.editDialogListener = editDialogListener;
    }
}