package com.anntony.myapplication.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.anntony.myapplication.R;
import com.bumptech.glide.Glide;

public class NoticiaActivity extends AppCompatActivity {

    private ImageView img;
    private TextView fecha, nombre, contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        img = findViewById(R.id.dataImagen);
        fecha = findViewById(R.id.dataFecha);
        nombre = findViewById(R.id.dataNombre);
        contenido = findViewById(R.id.dataContenido);

        Bundle extras = getIntent().getExtras();

        fecha.setText(extras.getString("fecha"));
        nombre.setText(extras.getString("nombre"));
        contenido.setText(extras.getString("contenido"));

        if(extras.getString("categoria").equals("becas")){
            Glide.with(this).load(R.drawable.becas).into(img);
        }else if(extras.getString("categoria").equals("noticia")) {
            Glide.with(this).load(R.drawable.noticias).into(img);
        }else if(extras.getString("categoria").equals("eventos")) {
            Glide.with(this).load(R.drawable.events).into(img);
        }else if(extras.getString("categoria").equals("inscripcion")) {
            Glide.with(this).load(R.drawable.registro).into(img);
        }


    }
}
