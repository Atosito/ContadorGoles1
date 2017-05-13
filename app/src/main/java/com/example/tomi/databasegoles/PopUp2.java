package com.example.tomi.databasegoles;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.tomi.databasegoles.DBHandler.PartidoHandler;
import com.example.tomi.databasegoles.Data.Partido;
import com.example.tomi.databasegoles.Data.Temporada;
import com.example.tomi.databasegoles.Widget.RobotoTextView;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by TOMI on 05/05/2017.
 */

public class PopUp2 extends AppCompatActivity {
    private Partido part,par;
    private EditText inputRival, inputCompetencia, inputGoles;
    private RobotoTextView tvAño;
    private TextInputLayout inputLayoutRival, inputLayoutCompetencia, inputLayoutGoles;
    private Button btnAgregar;
    private PartidoHandler partidoHandler = new PartidoHandler();
    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup2_partido);
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int heigh = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (heigh * .8));

        inputLayoutRival = (TextInputLayout) findViewById(R.id.input_layout_rival);
        inputLayoutCompetencia = (TextInputLayout) findViewById(R.id.input_layout_competencia);
        inputLayoutGoles = (TextInputLayout) findViewById(R.id.input_layout_goles);
        inputRival = (EditText) findViewById(R.id.input_rival);
        inputCompetencia = (EditText) findViewById(R.id.input_competencia);
        inputGoles = (EditText) findViewById(R.id.input_goles);
        tvAño = (RobotoTextView) findViewById(R.id.tvAño);
        btnAgregar = (Button) findViewById(R.id.btn_agregar);
        checkIntent();
        checkAño();
        inputRival.addTextChangedListener(new MyTextWatcher(inputRival));
        inputCompetencia.addTextChangedListener(new MyTextWatcher(inputCompetencia));
        inputGoles.addTextChangedListener(new MyTextWatcher(inputGoles));


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   submitForm();

            }
        });
    }
    private void checkIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            part = (Partido) bundle.get("partido_obj");
            if (part != null) {
                this.part.get_id();
                this.inputRival.setText(part.getRival());
                this.inputCompetencia.setText(part.getCompetencia());
                this.inputGoles.setText(part.getGoles());
            }
        }
    }
    private void checkAño() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            par = (Partido) bundle.get("partidoobj");
            if (par != null) {

                this.tvAño.setText(par.getAño());

            }
        }
    }
    public void setDataModels(){
        final String rival = inputRival.getText().toString();
        final String competencia = inputCompetencia.getText().toString();
        final String goles = inputGoles.getText().toString();
        final String año = tvAño.getText().toString();

        Partido partido = new Partido();
        partido.setRival(rival);
        partido.setCompetencia(competencia);
        partido.setGoles(goles);
        partido.setAño(año);
        Intent intent = getIntent();
        intent.putExtra("partidoobj", partido);
        setResult(RESULT_OK, intent);
        finish();

    }
    public void savePartido(){
        final String rival = inputRival.getText().toString();
        final String competencia = inputCompetencia.getText().toString();
        final String goles = inputGoles.getText().toString();
        final String año = tvAño.getText().toString();

        Partido partido = new Partido();
        partido.set_id(part.get_id());
        partido.setRival(rival);
        partido.setCompetencia(competencia);
        partido.setGoles(goles);
        partido.setAño(año);
        partidoHandler.edit(partido);
        finish();
    }
    private void submitForm() {
        if (!validateRival()) {
            return;
        }

        if (!validateCompetencia()) {
            return;
        }
        if (!validateGoles()) {
            return;
        }
        if (part == null) {
            setDataModels();
        }
        else {
            savePartido();
        }
    }

    private boolean validateRival() {
        if (inputRival.getText().toString().trim().isEmpty()) {
            inputLayoutRival.setError(getString(R.string.err_msg_rival));
            requestFocus(inputRival);
            return false;
        } else {
            inputLayoutRival.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateGoles() {
        if (inputGoles.getText().toString().trim().isEmpty()) {
            inputLayoutGoles.setError(getString(R.string.err_msg_goles));
            requestFocus(inputGoles);
            return false;
        } else {
            inputLayoutGoles.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCompetencia() {
        String competencia = inputCompetencia.getText().toString().trim();

        if (competencia.isEmpty()) {
            inputLayoutCompetencia.setError(getString(R.string.err_msg_competencia));
            requestFocus(inputCompetencia);
            return false;
        } else {
            inputLayoutCompetencia.setErrorEnabled(false);
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
                case R.id.input_rival:
                    validateRival();
                    break;
                case R.id.input_competencia:
                    validateCompetencia();
                    break;
                case R.id.input_goles:
                    validateGoles();
                    break;
            }
        }
    }
}

