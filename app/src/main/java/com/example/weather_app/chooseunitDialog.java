package com.example.weather_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class chooseunitDialog extends AppCompatDialogFragment  {

    RadioButton c, k;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.chooseunit,null);

        c=view.findViewById(R.id.c);
        k=view.findViewById(R.id.k);
        builder.setView(view)
                .setTitle("choose")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (c.isChecked())
                        {
                            Toast.makeText(getActivity(), "c", Toast.LENGTH_SHORT).show();
                        }else if (k.isChecked())
                        {
                            Toast.makeText(getActivity(), "k", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        return builder.create();
    }
}
