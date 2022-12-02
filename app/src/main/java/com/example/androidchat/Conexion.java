package com.example.androidchat;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Conexion {
    HandlerThread ht;
    Handler h, handlerMain;
    TextView view;
    MainActivity mainActivity;
    String message;
    Socket socket;
    Button btn;

    public Conexion(Button btn,TextView view, MainActivity mainActivity, Handler handlerMain){
        this.btn = btn;
        this.view=view;
        this.mainActivity=mainActivity;
        this.handlerMain=handlerMain;
        ht = new HandlerThread("MyHandlerThread");
        ht.start();
        Looper looper = ht.getLooper();
        h = new Handler(looper);
    }

    public void clienteEscribe(){
        h.post(new Runnable() {
            @Override
            public void run() {
                while (true) {
//                    try {
                        message = view.getText().toString();
//                        if(message.trim().length() > 0){
//                            OutputStream outputStream = socket.getOutputStream();
//                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
//                            writer.write(message);
//                            writer.newLine();
//                            writer.flush();
//                        }
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
                }
            }
        });
    }

    public void clienteConecta(){
        h.post(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("localhost", 5555);
                    Log.i("message", String.valueOf(socket));
                }catch (Exception e){
                    e.printStackTrace();
                }
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clienteEscribe();
                    }
                });
            }
        });


    }

}
