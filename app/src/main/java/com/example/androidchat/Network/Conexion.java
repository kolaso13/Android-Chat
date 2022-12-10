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

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.androidchat.Database.AppDatabase;
import com.example.androidchat.Database.Mensaje;
import com.example.androidchat.Database.MensajeDAO;
import com.example.androidchat.Database.MensajeViewModel;
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
    MainActivity mainActivity;
    Socket socket;
    BufferedWriter writer;
    BufferedReader reader;

    public Conexion(MainActivity mainActivity) {
        this.mainActivity=mainActivity;

        handlerMain = new Handler();

        htConexion = new HandlerThread("Conexion");
        htConexion.start();
        hConexion = new Handler(htConexion.getLooper());

        htEnviarMsj = new HandlerThread("Enviar");
        htEnviarMsj.start();
        hEnviar = new Handler(htEnviarMsj.getLooper());

        htRecibirMsj = new HandlerThread("Recibir");
        htRecibirMsj.start();
        hRecibir = new Handler(htRecibirMsj.getLooper());
    }

    public void enviarMensaje(String mensaje){
        hEnviar.post(new Runnable() {
            @Override
            public void run() {
                if (mensaje.trim().length() > 0) {
                    try {
                        writer.write(mensaje);
                        writer.newLine();
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void iniciarConexion(){
        hConexion.post(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("10.0.2.2", 5555);
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
                                    Mensaje mensaje = new Mensaje();

                                    mensaje.usuario = finalLine.substring(8,10) + ":";
                                    mensaje.texto= finalLine.substring(15);

                                    MensajeViewModel mensajeViewModel;
                                    mensajeViewModel = new ViewModelProvider(mainActivity).get(MensajeViewModel.class);
                                    mensajeViewModel.insert(mensaje);
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
