package orm.realm.appbancodedadosmeusclientes.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import orm.realm.appbancodedadosmeusclientes.R;
import orm.realm.appbancodedadosmeusclientes.controller.ClienteORMController;
import orm.realm.appbancodedadosmeusclientes.model.ClienteORM;


public class MainActivity extends AppCompatActivity {

    ClienteORMController clienteORMController;

    List<ClienteORM> listaDeClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clienteORMController = new ClienteORMController();

        /*ClienteORM orm;



        for (int i = 20; i < 25; i++) {


            clienteORMController.deleteById(i);
        }*/

        /*listaDeClientes = clienteORMController.listar();

        for(ClienteORM obj: listaDeClientes){
            Log.d("db_log", "onCreate: " + obj.getId()+ " " +obj.getNome());
        }*/


        ClienteORM clienteORMConsulta = clienteORMController.getById(28);

        if(clienteORMConsulta != null){

            Log.d("db_log", "onCreate: " + clienteORMConsulta.getId() + " " + clienteORMConsulta.getNome());
        } else {

            Log.d("db_log", "onCreate: ID não registrado");

        }


    }
}