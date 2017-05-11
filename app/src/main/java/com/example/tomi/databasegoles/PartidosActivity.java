package com.example.tomi.databasegoles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.tomi.databasegoles.DBHandler.DatabaseManager;
import com.example.tomi.databasegoles.DBHandler.PartidoHandler;
import com.example.tomi.databasegoles.DBHandler.TemporadaHandler;
import com.example.tomi.databasegoles.Data.Partido;
import com.example.tomi.databasegoles.Data.Temporada;
import com.example.tomi.databasegoles.Widget.RobotoTextView;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TOMI on 04/05/2017.
 */

public class PartidosActivity extends AppCompatActivity {
    private List<Partido> partidos;
    private Temporada temp;
    private ListView lvPartidos;
    private RobotoTextView tvAño, tvRival, tvGoles, tvCompetencia;
    private Button btnAgregar;
    private TemporadaHandler TemporadaHandler = new TemporadaHandler();
    private PartidoHandler PartidoHandler = new PartidoHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidos);
        lvPartidos = (ListView) findViewById(R.id.lvPartidos);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        tvAño = (RobotoTextView) findViewById(R.id.tvAño);
        tvRival = (RobotoTextView) findViewById(R.id.tvRival);
        tvGoles = (RobotoTextView) findViewById(R.id.tvGoles);
        tvCompetencia = (RobotoTextView) findViewById(R.id.tvCompetencia);
        checkIntent();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPartido();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateListView();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                Partido partido = (Partido) data.getSerializableExtra("partido_obj");
                int CourseId = PartidoHandler.insert(partido);
                if(CourseId != -1){
                    TastyToast.makeText(getApplicationContext(), "Partido Agregado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                }
                else
                    TastyToast.makeText(getApplicationContext(), "Algo malo ocurrió", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                onResume();
            }
        }
    }
    private void addPartido(){

        Intent intent = new Intent(this, PopUp2.class);
        Partido partido = new Partido();
        String año = tvAño.getText().toString();
        partido.setAño(año);
        intent.putExtra("partido_obj", partido);
        startActivityForResult(intent, 1);

    }
    public List<Partido> getPartidos() {
        Temporada temporada = (Temporada) getIntent().getSerializableExtra("temporada");
        String año = temporada.getAño().toString();
        List<Partido> list = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM DBPartidos WHERE año=?", new String [] {año});

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Partido partido = new Partido();
            partido.set_id(cursor.getString(0));
           partido.setAño(cursor.getString(1));
            partido.setRival(cursor.getString(2));
            partido.setCompetencia(cursor.getString(3));
            partido.setGoles(cursor.getString(4));
            list.add(partido);
            cursor.moveToNext();
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return list;
    }
    private class Adapter extends ArrayAdapter<Partido> {


        public Adapter(Context context, List<Partido> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.partido_layout_list, parent, false);
            }
            RobotoTextView tvRival = (RobotoTextView) convertView.findViewById(R.id.tvRival);
            RobotoTextView tvCompetencia = (RobotoTextView) convertView.findViewById(R.id.tvCompetencia);
            RobotoTextView tvGoles = (RobotoTextView) convertView.findViewById(R.id.tvGoles);
            Partido partido = partidos.get(position);
            tvRival.setText(partido.getRival());
            tvCompetencia.setText(partido.getCompetencia());
            tvGoles.setText(partido.getGoles());
            return convertView;
        }
    }
    private void updateListView() {
        this.partidos = getPartidos();

        Adapter adapter = new Adapter(this, partidos);
        this.lvPartidos.setAdapter(adapter);
    }
    private void checkIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            temp = (Temporada) bundle.get("temporada");
            if (temp != null) {
                this.tvAño.setText(temp.getAño());
                String año = this.tvAño.getText().toString();
                int TotalGoles = getTotalGoles();
                int TotalPartidos = getPartidos().size();
                String GolesTotal = String.valueOf(TotalGoles);
                temp.setTotalgoles(GolesTotal);
                temp.setTotalpartidos(String.valueOf(TotalPartidos));
                TemporadaHandler.updateTotales(temp);
            }
        }
    }
    public int getTotalGoles(){
        String año = this.tvAño.getText().toString();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("SELECT goles FROM DBPartidos WHERE año=?", new String [] {año});
        cursor.moveToFirst();
        int TotalGoles = 0;
        while (!cursor.isAfterLast()) {

            TotalGoles += Integer.parseInt(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return TotalGoles;

    }


}
