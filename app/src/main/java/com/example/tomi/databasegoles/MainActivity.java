package com.example.tomi.databasegoles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tomi.databasegoles.DBHandler.DatabaseManager;
import com.example.tomi.databasegoles.DBHandler.TemporadaHandler;
import com.example.tomi.databasegoles.Data.Partido;
import com.example.tomi.databasegoles.Data.Temporada;
import com.example.tomi.databasegoles.Widget.RobotoTextView;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Temporada> temporadas;
    private ListView listView;
    private TemporadaHandler TemporadaHandler = new TemporadaHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lvTemporadas);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openActPartidos(position);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTemporada();
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
                Temporada temporada = (Temporada) data.getSerializableExtra("temporada_obj");
                int CourseId = TemporadaHandler.insert(temporada);
                if(CourseId != -1){
                    TastyToast.makeText(getApplicationContext(), "Temporada Agregada", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                }
                else
                    TastyToast.makeText(getApplicationContext(), "Año o Temporada Repetida", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                onResume();
            }
        }
    }

    private void addTemporada(){

        Intent intent = new Intent(this, PopUp1.class);
        Temporada temporada = new Temporada();
        intent.putExtra("temporada_obj", temporada);
        startActivityForResult(intent, 1);

    }
    private void openActPartidos(int index){
        Temporada temporada = temporadas.get(index);
        Intent intent = new Intent(this, PartidosActivity.class);
        intent.putExtra("temporada", temporada);
        startActivity(intent);


    }
    public List<Temporada> getTemporadas() {
        List<Temporada> list = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Temporada.TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Temporada temporada = new Temporada();
            temporada.set_id(cursor.getString(0));
            temporada.setAño(cursor.getString(1));
            temporada.setTotalgoles(cursor.getString(2));
            temporada.setTotalpartidos(cursor.getString(3));
            temporada.setMeta(cursor.getString(4));

            list.add(temporada);
            cursor.moveToNext();
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return list;
    }
    private class Adapter extends ArrayAdapter<Temporada> {


        public Adapter(Context context, List<Temporada> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.temporada_layout_list, parent, false);
            }
            RobotoTextView tvAño = (RobotoTextView) convertView.findViewById(R.id.tvAño);
            RobotoTextView tvGoles = (RobotoTextView) convertView.findViewById(R.id.tvGoles);
            RobotoTextView tvPartidos = (RobotoTextView) convertView.findViewById(R.id.tvPartidos);
            RobotoTextView tvMeta = (RobotoTextView) convertView.findViewById(R.id.tvMeta);
            ImageView ivEditar = (ImageView) convertView.findViewById(R.id.ivEditar);
            final Temporada temporada = temporadas.get(position);
            tvAño.setText(temporada.getAño());
            tvGoles.setText(temporada.getTotalgoles());
            tvPartidos.setText(temporada.getTotalpartidos());
            tvMeta.setText(temporada.getMeta());
            ivEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    switch (v.getId()) {
                        case R.id.ivEditar:

                            android.widget.PopupMenu popup = new android.widget.PopupMenu(getApplicationContext(), v);
                            popup.getMenuInflater().inflate(R.menu.popupmenu,
                                    popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {
                                        case R.id.Editar:
                                            updateTemporada(position);
                                            TastyToast.makeText(getApplicationContext(), "Editar", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                                            onResume();

                                            break;
                                        case R.id.Borrar:
                                            TemporadaHandler.delete(temporada);
                                            TastyToast.makeText(getApplicationContext(), "Borrar", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                                            onResume();
                                            break;

                                        default:
                                            break;
                                    }

                                    return true;
                                }
                            });

                            break;

                        default:
                            break;
                    }


                }
            });

            return convertView;
        }
    }
    private void updateListView() {
        this.temporadas = getTemporadas();

        Adapter adapter = new Adapter(this, temporadas);
        this.listView.setAdapter(adapter);
    }
    public void updateTemporada(int index) {
        Temporada temporada = temporadas.get(index);
        Intent intent = new Intent(this, PopUp1.class);
        intent.putExtra("temporada_", temporada);
        startActivity(intent);

    }
}
