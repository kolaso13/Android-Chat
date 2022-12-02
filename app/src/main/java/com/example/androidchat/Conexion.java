package com.example.androidchat;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Conexion {
    HandlerThread ht;
    Handler h, handlerMain;
    TextView view;
    MainActivity mainActivity;
    String message;
    Socket socket;

    public Conexion(TextView view, MainActivity mainActivity, Handler handlerMain, Socket socket){
        this.socket = socket;
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
                    try {
                        handlerMain.post(new Runnable() {
                            @Override
                            public void run() {
                                message = view.getText().toString();
                            }
                        });
                        Log.i("message", message);
                        if(message.trim().length() > 0){
                            OutputStream outputStream = socket.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                            writer.write(message);
                            writer.newLine();
                            writer.flush();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

}
