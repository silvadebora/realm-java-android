package orm.realm.appbancodedadosmeusclientes.view;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AppApplication extends Application {

    public static final String DB_NAME = "MeusClientes.realm";
    public static final int DB_VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(DB_NAME)
                .schemaVersion(DB_VERSION)
                .allowWritesOnUiThread(true)               //ele executa algumas operações como thread em segundo plano
                .build();

        // é necessário esse objeto para poder criar uma instancia do banco de dados
        Realm realm = Realm.getInstance(config);

        Log.d("db_log", "onCreate: Realm com sucesso: " + DB_NAME + " versão " + DB_VERSION);

    }
}
