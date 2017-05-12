package com.example.tomi.databasegoles;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.tomi.databasegoles.DBHandler.TemporadaHandler;
import com.example.tomi.databasegoles.Data.Partido;
import com.example.tomi.databasegoles.Data.Temporada;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by TOMI on 03/05/2017.
 */

public class PopUp1 extends AppCompatActivity {
    private EditText inputAño, inputMeta, inputPassword;
    private TextInputLayout inputLayoutAño, inputLayoutMeta;
    private Temporada temp;
    private Button btnAgregar;
    private TemporadaHandler temporadaHandler = new TemporadaHandler();
    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup1_temporada);
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int heigh = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (heigh * .8));

        inputLayoutAño = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutMeta = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputAño = (EditText) findViewById(R.id.input_año);
        inputMeta = (EditText) findViewById(R.id.input_meta);

        btnAgregar = (Button) findViewById(R.id.btn_agregar);

        inputAño.addTextChangedListener(new MyTextWatcher(inputAño));
        inputMeta.addTextChangedListener(new MyTextWatcher(inputMeta));
        checkIntent();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (temp == null) {
                    submitForm();
                    setDataModels();
                } else {
                    submitForm();
                    saveTemporada();
                }
            }
        });
    }
    public void setDataModels(){
        final String año = inputAño.getText().toString();
        final String meta = inputMeta.getText().toString();

            Temporada temporada = new Temporada();
            temporada.setAño(año);
            temporada.setMeta(meta);
            Intent intent = getIntent();
            intent.putExtra("temporada_obj", temporada);
            setResult(RESULT_OK, intent);
            finish();

    }
    private void submitForm() {
        if (!validateAño()) {
            return;
        }

        if (!validateMeta()) {
            return;
        }

    }
    public void saveTemporada(){
        final String año = inputAño.getText().toString();
        final String meta = inputMeta.getText().toString();

        Temporada temporada = new Temporada();
        temporada.set_id(temp.get_id());
        temporada.setAño(año);
        temporada.setMeta(meta);
        temporadaHandler.edit(temporada);
        finish();
    }
    private void checkIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            temp = (Temporada) bundle.get("temporada_");
            if (temp != null) {
                this.temp.get_id();
                this.inputAño.setText(temp.getAño());
                this.inputMeta.setText(temp.getMeta());
            }
        }
    }

    private boolean validateAño() {
        if (inputAño.getText().toString().trim().isEmpty()) {
            inputLayoutAño.setError(getString(R.string.err_msg_año));
            requestFocus(inputAño);
            return false;
        } else {
            inputLayoutAño.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMeta() {
        String email = inputMeta.getText().toString().trim();

        if (email.isEmpty()) {
            inputLayoutMeta.setError(getString(R.string.err_msg_meta));
            requestFocus(inputMeta);
            return false;
        } else {
            inputLayoutMeta.setErrorEnabled(false);
        }

        return true;
    }



    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_año:
                    validateAño();
                    break;
                case R.id.input_meta:
                    validateMeta();
                    break;
            }
        }
    }
}

