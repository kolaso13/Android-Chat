package com.example.androidchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidchat.Database.AppDatabase;
import com.example.androidchat.Database.MensajeViewModel;
import com.example.androidchat.Network.Conexion;

public class MainActivity extends AppCompatActivity {
    EditText et1;
    Button SendBtn;
    Conexion conexion;
    LinearLayout ly;
    Context context = this;
    private MensajeViewModel mMensajeViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.editText);
        SendBtn = findViewById(R.id.sendBtn);
        ly = findViewById(R.id.LinearLayout);

        conexion = new Conexion(context, ly, SendBtn, et1,this, new Handler());
        conexion.iniciarConexion();

        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conexion.enviarMensaje();
            }
        });

//        mMensajeViewModel = new ViewModelProvider(this).get(MensajeViewModel.class);
//        mMensajeViewModel.getAllMensajes().observe(this, mensaje -> {
//            // Update the cached copy of the words in the adapter.
//            TextView texto = new TextView(context);
//            texto.setText(mensaje);
//            texto.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            ly.addView(texto);
//        });
    }

}