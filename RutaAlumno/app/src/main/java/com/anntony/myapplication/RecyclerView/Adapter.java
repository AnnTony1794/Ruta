package com.anntony.myapplication.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anntony.myapplication.Model.Noticia;
import com.anntony.myapplication.R;
import com.anntony.myapplication.Views.NoticiaActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio Facundo on 05/02/2018.
 */

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    List<Noticia> lista;
    Context context;

    public Adapter(Context context) {
        lista = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        holder.nombre.setText(lista.get(position).getName());
        holder.categoria.setText(lista.get(position).getCategory());
        holder.fecha.setText(lista.get(position).getDate());
        if(lista.get(position).getCategory().equals("becas")){
            Glide.with(context)
                    .load(R.drawable.becas)
                    .into(holder.imagen);
        }else if (lista.get(position).getCategory().equals("noticia")){
            Glide.with(context)
                    .load(R.drawable.noticias)
                    .into(holder.imagen);
        }
        else if (lista.get(position).getCategory().equals("inscripcion")){
            Glide.with(context)
                    .load(R.drawable.registro)
                    .into(holder.imagen);
        }
        else if (lista.get(position).getCategory().equals("eventos")){
            Glide.with(context)
                    .load(R.drawable.events)
                    .into(holder.imagen);
        }

        holder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NoticiaActivity.class);
                intent.putExtra("fecha", lista.get(position).getDate());
                intent.putExtra("nombre", lista.get(position).getName());
                intent.putExtra("contenido", lista.get(position).getContent());
                intent.putExtra("categoria", lista.get(position).getCategory());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void adicionarDatos(Noticia adicionar) {
        lista.add(0,adicionar);
        notifyDataSetChanged();
    }

    public void eliminarListaAnterior() {
        int tam = lista.size();
        for (int i = 0; i < tam; i++){
            lista.remove(0);
        }
    }
}
