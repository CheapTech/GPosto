package br.com.senacrs.gposto.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import br.com.senacrs.gposto.R;

public class CustomAlertDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    public  Dialog dialog;

    public CustomAlertDialog(Activity context) {
        super(context);
        this.activity = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alertdialog_edit_perfil);
    }

    @Override
    public void onClick(View v) {


    }
}
