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

        conexion = new Conexion(this);
        conexion.iniciarConexion();


        mMensajeViewModel = new ViewModelProvider(this).get(MensajeViewModel.class);
        mMensajeViewModel.getAllMensajes().observe(this, mensaje -> {
            if (ly.getChildCount() != 0){
                TextView textView = new TextView(this);
                textView.setText(mensaje.get(mensaje.size()-1).usuario + " " + mensaje.get(mensaje.size()-1).texto);
                ly.addView(textView);
            }else{
                for (int i = 0; i < mensaje.size();i++){
                    TextView textView = new TextView(this);
                    textView.setText(mensaje.get(i).usuario + " " + mensaje.get(i).texto);
                    ly.addView(textView);
                }
            }
        });


        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conexion.enviarMensaje(et1.getText().toString());
            }
        });
    }

}