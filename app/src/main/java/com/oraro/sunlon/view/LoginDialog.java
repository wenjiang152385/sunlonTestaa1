
package com.oraro.sunlon.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.oraro.sunlon.Interface.editCallback;
import com.oraro.sunlon.sunlontesta.R;

public class LoginDialog extends Dialog {

	EditText account;
	String text = "";
	editCallback callback;
	
	public LoginDialog(Context context) {
		super(context);
		
	}

	public void setCallBack(editCallback callBack) {
		this.callback = callBack;
	}
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	this.setContentView(R.layout.activity_dialog);
    	account=(EditText)findViewById(R.id.account);

		account.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}
			@Override
			public void afterTextChanged(Editable editable) {
				if (account.getText().toString().length() == 11);
				callback.setTextListener(account.getText().toString());
			}
		});
    }

	public String getEditText() {
		return  text;
	}
}
