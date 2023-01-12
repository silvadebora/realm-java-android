package orm.realm.appbancodedadosmeusclientes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import orm.realm.appbancodedadosmeusclientes.model.Cliente;


public class AppDataBase extends SQLiteOpenHelper {

    public static final String DB_NAME = "MeusClientes.sqlite";
    public static final int DB_VERSION = 1;

    SQLiteDatabase db;


    public AppDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tabelaCliente = "CREATE TABLE cliente \n" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " nome TEXT,\n" +
                " salario REAL, \n" +
                " preco NUMERIC,\n" +
                " idade INTEGER,\n" +
                " ativo INTEGER,\n" +
                " dataCadastro TEXT,\n" +
                " horaCadastro TEXT\n" +
                " )";

        try {

            db.execSQL(tabelaCliente);

        }catch (SQLException e){

            Log.e("DB_LOG", "onCreate: "+e.getLocalizedMessage());

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // INSERT INTO TABLE values (c1,c2,c3)
    // INSERT INTO cliente VALUES('Maddo', 999.99, 99.9987, 18, 1,"2020-12-01","12:12:12:12");
    

    public boolean insert(String tabela, Cliente dados){

        ContentValues values = new ContentValues();

        values.put("nome",dados.getNome());
        values.put("salario",dados.getSalario());
        values.put("preco",dados.getPreco());
        values.put("idade",dados.getIdade());
        values.put("ativo",dados.isAtivo());
        values.put("dataCadastro",dados.getDataCadastro());
        values.put("horaCadastro",dados.getHoraCadastro());

        return db.insert(tabela,null,values) > 0;
    }

    // UPDATE table SET colunaA = novo_conteudoA, colunaN = novo_conteudoN WHERE condição;
    public boolean update(String tabela, Cliente dados){

        int id = dados.getId();

        ContentValues values = new ContentValues();

        values.put("nome",dados.getNome());
        values.put("salario",dados.getSalario());
        values.put("preco",dados.getPreco());
        values.put("idade",dados.getIdade());
        values.put("ativo",dados.isAtivo());
        values.put("dataCadastro",dados.getDataCadastro());
        values.put("horaCadastro",dados.getHoraCadastro());

        return db.update(tabela, values,"id=?",
                 new String[]{Integer.toString(id)}) >0;


    }

    // DELETE FROM table WHERE condição;
    public boolean delete(String tabela, int id) {

        return db.delete(tabela,"id=?",
                new String[]{Integer.toString(id)}) >0;
    }

    // SELECT * FROM cliente;
    public List<Cliente> select(String tabela){

        List<Cliente> retorno = new ArrayList<>();

        Cursor cursor;

        Cliente objCliente;

        String sqlDeConsulta = "SELECT * FROM "+tabela;

        cursor = db.rawQuery(sqlDeConsulta,null);

        if(cursor.moveToFirst()){

            do{
                // código

                objCliente = new Cliente();

                objCliente.setId(cursor.getInt(cursor.getColumnIndex("id")));
                objCliente.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                objCliente.setSalario(cursor.getDouble(cursor.getColumnIndex("salario")));
                objCliente.setPreco(cursor.getDouble(cursor.getColumnIndex("preco")));

                int ativo = cursor.getInt(cursor.getColumnIndex("ativo"));

                if(ativo == 0){
                    objCliente.setAtivo(false);
                }else{
                    objCliente.setAtivo(true);
                }

                objCliente.setDataCadastro(cursor.getString(cursor.getColumnIndex("dataCadastro")));
                objCliente.setHoraCadastro(cursor.getString(cursor.getColumnIndex("horaCadastro")));

                retorno.add(objCliente);

            }while (cursor.moveToNext());


        }

        cursor.close();

        return  retorno;


    }
}
