package com.example.androidchat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText et1;
    Button SendBtn;
    Conexion conexion;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.editText);
        SendBtn = findViewById(R.id.sendBtn);

        conexion = new Conexion( SendBtn, et1,this, new Handler());
        conexion.clienteConecta();

        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conexion.clienteEscribe();
            }
        });

    }

}