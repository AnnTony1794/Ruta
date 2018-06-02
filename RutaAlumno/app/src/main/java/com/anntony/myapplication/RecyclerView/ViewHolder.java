package com.anntony.myapplication.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anntony.myapplication.R;

/**
 * Created by Antonio Facundo on 05/02/2018.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView nombre;
    TextView categoria;
    ImageView imagen;
    TextView fecha;

    public ViewHolder(View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.cvNombre);
        categoria = itemView.findViewById(R.id.cvCategoria);
        imagen = itemView.findViewById(R.id.cvImg);
        fecha = itemView.findViewById(R.id.cvFecha);
    }
}
