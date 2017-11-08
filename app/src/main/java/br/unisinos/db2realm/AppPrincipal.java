package br.unisinos.db2realm;

import android.app.Application;

import io.realm.Realm;

public class AppPrincipal extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
