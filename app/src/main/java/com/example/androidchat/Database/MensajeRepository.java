package com.example.androidchat.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MensajeRepository {
    private MensajeDAO mMensajeDao;
    private LiveData<List<Mensaje>> mAllMensajes;

    MensajeRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mMensajeDao = db.mensajeDao();
        mAllMensajes = mMensajeDao.getAll();
    }

    LiveData<List<Mensaje>> getAllMensajes() {
        return mAllMensajes;
    }

    void insert(Mensaje mensaje) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mMensajeDao.insert(mensaje);
        });
    }
}
