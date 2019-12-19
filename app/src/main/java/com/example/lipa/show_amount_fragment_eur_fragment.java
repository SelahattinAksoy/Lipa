package com.example.lipa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class show_amount_fragment_eur_fragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.show_amount_fragment_eur_fragment,container,false);

        TextView t=v.findViewById(R.id.eur_amou_fragment);
        t.setText(Anasayfa.db_eur);
        return v;
    }
}
