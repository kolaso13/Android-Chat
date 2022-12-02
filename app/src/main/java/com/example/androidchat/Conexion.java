package com.example.androidchat;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Conexion {
    HandlerThread ht, ht1, ht2;
    Handler h, handlerMain, h1, h2;
    TextView view;
    MainActivity mainActivity;
    String message;
    Socket socket;
    Button btn;
    BufferedWriter writer;
    BufferedReader reader;

    public Conexion(Button btn, TextView view, MainActivity mainActivity, Handler handlerMain){
        this.btn = btn;
        this.view=view;
        this.mainActivity=mainActivity;
        this.handlerMain=handlerMain;
    }

    public void clienteEscribe(){
        h1.post(new Runnable() {
            @Override
            public void run() {
                try {
                    message = view.getText().toString();
                    Log.i("message", message);
                    if(message.trim().length() > 0){
                        writer.write(message);
                        writer.newLine();
                        writer.flush();
                    }
                    view.setText("");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public void clienteConecta(){
        ht = new HandlerThread("Connect");
        ht.start();
        Looper looper = ht.getLooper();
        h = new Handler(looper);

        h.post(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("10.0.2.2", 5555);

                    OutputStream outputStream = socket.getOutputStream();
                    writer = new BufferedWriter(new OutputStreamWriter(outputStream));

                    //Cliente Escribe
                    ht1 = new HandlerThread("Writte");
                    ht1.start();
                    Looper looper = ht1.getLooper();
                    h1 = new Handler(looper);

                    //Cliente Lee
                    ht2 = new HandlerThread("Read");
                    ht2.start();
                    Looper looper2 = ht2.getLooper();
                    h2 = new Handler(looper2);
                    clienteLee();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void clienteLee(){
        h2.post(new Runnable() {
            @Override
            public void run() {
                while (true){
                        InputStream inputStream = null;
                        try {
                            inputStream = socket.getInputStream();
                            reader = new BufferedReader(new InputStreamReader(inputStream));
                            String line;
                            while ((line = reader.readLine()) != null){

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                }
            }
        });
    }
}
