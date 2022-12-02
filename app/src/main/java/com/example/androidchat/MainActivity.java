package com.example.androidchat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText InputText;
    Button SendBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputText = findViewById(R.id.inputText);
        SendBtn = findViewById(R.id.sendBtn);

        InputStream inputStream = null;

        Conexion conexion = new Conexion(SendBtn, InputText,this, new Handler());
        conexion.clienteConecta();


        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conexion.clienteEscribe();
            }
        });
    }
}