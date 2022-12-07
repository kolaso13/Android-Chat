package com.example.androidchat.Database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MensajeViewModel extends AndroidViewModel {
    private MensajeRepository mRepository;

    private final LiveData<List<Mensaje>> mAllMensajes;

    public MensajeViewModel (Application application) {
        super(application);
        mRepository = new MensajeRepository(application);
        mAllMensajes = mRepository.getAllMensajes();
    }

    public LiveData<List<Mensaje>> getAllMensajes() { return mAllMensajes; }

    public void insert(Mensaje mensaje) { mRepository.insert(mensaje); }
}
