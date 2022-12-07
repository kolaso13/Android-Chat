package com.example.androidchat.Network;

import android.app.ActionBar;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.room.Room;

import com.example.androidchat.Database.AppDatabase;
import com.example.androidchat.Database.Mensaje;
import com.example.androidchat.Database.MensajeDAO;
import com.example.androidchat.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class Conexion {
    HandlerThread htConexion, htEnviarMsj, htRecibirMsj;
    Handler hConexion, hEnviar, hRecibir, handlerMain;
    TextView view;
    MainActivity mainActivity;
    String message;
    Socket socket;
    Button btn;
    BufferedWriter writer;
    BufferedReader reader;
    LinearLayout ly;
    Context context;


    public Conexion(Context context, LinearLayout ly, Button btn, TextView view, MainActivity mainActivity, Handler handlerMain) {
        this.context = context;
        this.ly = ly;
        this.btn = btn;
        this.view=view;
        this.mainActivity=mainActivity;
        this.handlerMain=handlerMain;

        htConexion = new HandlerThread("Conexion");
        htEnviarMsj = new HandlerThread("Enviar");
        htRecibirMsj = new HandlerThread("Recibir");

        htConexion.start();
        htEnviarMsj.start();
        htRecibirMsj.start();

        hConexion = new Handler(htConexion.getLooper());
        hEnviar = new Handler(htEnviarMsj.getLooper());
        hRecibir = new Handler(htRecibirMsj.getLooper());
        handlerMain = new Handler();

    }

    public void enviarMensaje(){
        hEnviar.post(new Runnable() {
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

    public void iniciarConexion(){
        hConexion.post(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("10.0.2.2", 6666);

                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    recibirMensaje();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void recibirMensaje(){
        hRecibir.post(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            String finalLine = line;
                            handlerMain.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("message", finalLine);
                                    TextView texto = new TextView(context);
                                    texto.setText(finalLine);
                                    texto.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    ly.addView(texto);
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
