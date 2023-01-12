package orm.realm.appbancodedadosmeusclientes.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import orm.realm.appbancodedadosmeusclientes.model.ClienteORM;

public class ClienteORMController {

    public void insert(ClienteORM clienteORM){

        // acesso ao db
        Realm realm = Realm.getDefaultInstance();

        Number primaryKey = realm.where(ClienteORM.class).max("id");

        final int autoIncrementPrimaryKey = (primaryKey == null) ? 1 : primaryKey.intValue() + 1;

        clienteORM.setId(autoIncrementPrimaryKey);

        realm.beginTransaction();
        realm.copyToRealm(clienteORM);
        realm.commitTransaction();
        realm.close();

        Log.d("db_log", "insert: " +clienteORM.getId());

    }

    public void update(ClienteORM clienteORM){

        Realm realm = Realm.getDefaultInstance();

        // retorna o primeiro dado que satisfaça o pedido
        ClienteORM cliente = realm.where(ClienteORM.class).equalTo("id", clienteORM.getId())
                .findFirst();

        if(cliente != null){

            realm.beginTransaction();

            cliente.setNome(clienteORM.getNome());
            cliente.setSalario(clienteORM.getSalario());
            cliente.setPreco(clienteORM.getPreco());
            cliente.setDataCadastro(clienteORM.getDataCadastro());
            cliente.setHoraCadastro(clienteORM.getHoraCadastro());
            cliente.setAtivo(clienteORM.isAtivo());

            realm.commitTransaction();

        }
        realm.close();


    }

    public void delete(ClienteORM clienteORM){

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        // pega todos os registros
        RealmResults<ClienteORM> results = realm.where(ClienteORM.class).equalTo("id", clienteORM.getId())
                .findAll();

        results.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();

    }

    public void deleteById(int id){

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        // pega todos os registros
        RealmResults<ClienteORM> results = realm.where(ClienteORM.class).equalTo("id", id)
                .findAll();

        results.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();

    }

    public List<ClienteORM> listar(){

        Realm realm = null;

        // retorna todos os registros do banco de dados na tabela cliente
        RealmResults<ClienteORM> results = null;

        List<ClienteORM> list = new ArrayList<>();

        try{

            realm = Realm.getDefaultInstance();

            //pega os resultados
            results = realm.where(ClienteORM.class).findAll();

            // copia para a lista
            list = realm.copyFromRealm(results);

        }catch (RealmException e){

        }finally {
            realm.close();
        }

        return list;
    }
    
    public ClienteORM getById(int id){

        Realm realm = Realm.getDefaultInstance();

        ClienteORM objeto = null;

        try{

            //pega o registro que está no db e transforma em objeto
            objeto = realm.copyFromRealm(Objects.requireNonNull(realm.where(ClienteORM.class))
                    .equalTo("id", id)
                    .findFirst());

        }catch (Exception e){

            Log.e("db_log", "Erro ao executar getById: " + e.getMessage());

        }

        return objeto;
    }



}
