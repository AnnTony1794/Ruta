package com.anntony.myapplication.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anntony.myapplication.Api.APIService;
import com.anntony.myapplication.Api.APIUtilities;
import com.anntony.myapplication.Model.Noticia;
import com.anntony.myapplication.Model.Noticias;
import com.anntony.myapplication.R;
import com.anntony.myapplication.RecyclerView.Adapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoticiasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoticiasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticiasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NoticiasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoticiasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoticiasFragment newInstance(String param1, String param2) {
        NoticiasFragment fragment = new NoticiasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private RecyclerView recycler;
    private Adapter adaptador;
    private APIService mAPIService;
    private SwipeRefreshLayout swipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_noticias, container, false);
        mAPIService = APIUtilities.getAPIService();

        swipe = v.findViewById(R.id.srl);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNoticias();
            }
        });
        recycler = v.findViewById(R.id.rv);
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        l.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(l);
        adaptador = new Adapter(getContext());
        recycler.setAdapter(adaptador);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getNoticias();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getNoticias(){
        Call<Noticias> callLista = mAPIService.getNoticias();
        callLista.enqueue(new Callback<Noticias>() {
            @Override
            public void onResponse(Call<Noticias> call, Response<Noticias> response) {
                if(response.isSuccessful())
                    if(response.body() != null) {


                        adaptador.eliminarListaAnterior();
                        Noticia aux;

                        for(int i = 0; i < response.body().getNoticias().size(); i++){
                            aux = new Noticia();
                            aux.setName(response.body().getNoticias().get(i).getName());
                            aux.setCategory(response.body().getNoticias().get(i).getCategory());
                            aux.setDate(response.body().getNoticias().get(i).getDate());
                            aux.setContent(response.body().getNoticias().get(i).getContent());
                            aux.setId(response.body().getNoticias().get(i).getId());

                            adaptador.adicionarDatos(aux);
                            swipe.setRefreshing(false);
                        }

                    }

            }

            @Override
            public void onFailure(Call<Noticias> call, Throwable t) {

            }
        });
    }
}
