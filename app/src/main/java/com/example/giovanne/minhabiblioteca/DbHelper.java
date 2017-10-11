package com.example.giovanne.minhabiblioteca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giovanne on 01/10/17.
 */
public class DbHelper extends SQLiteOpenHelper{

    public static final String NOME_BASE = "MinhaBiblioteca";
    public static final int VERSAO_BASE = 1;
    public DbHelper(Context context) {
        super(context, NOME_BASE, null, VERSAO_BASE);
    }


    /* Este metodo on create recebe um obj do tipo SQLiteDataBase e assim criamos
    uma string que na verdade é uma query do tipo SQL que guardamos em uma string
    apos isso chamamos o metodo execSQL() utilizando o proprio obj do parametro

    esse obj cria a tabela livro
    */

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCreateTabelaLivro = "CREATE TABLE livro("
                                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                        + "titulo TEXT,"
                                        + "autor TEXT,"
                                        + "paginas INTEGER"
                                        + ")";

            db.execSQL(sqlCreateTabelaLivro);
    }

    /*
    Este metodo faz o update da tabela, o primeiro parametro interiro é o valor
     da versão anterior e o segundo inteiro é o valor da nova versao do db
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
            String sqlDropTableLivros = "DROP TABLE livro";

        db.execSQL(sqlDropTableLivros);

        onCreate(db);
    }

    /*
    O obj do tipo SQLiteDatabase nos dá um metodo que é melhor do que a query
    INSERT INTO. assim recuperamos o db da proria classe e selecionamos um com
    permissao de escrita;
     */
    //Aqui tambem podemos criar uma query
    public void insertLivro(Livro livro){

        /*
        Aqui recuperamos a instancia de db utilizado nos metodos acima e colocamos dentroo
        de um obj do tipo SQLiteDatabase
         */
        SQLiteDatabase db = getWritableDatabase();
        /*
        crio um content values e dentro do obj do content values e
        fazemos objContentValues.put("nomeColuna", objDoDB.getAtributo());
         */
        ContentValues cv = new ContentValues();

        cv.put("titulo", livro.getTitulo());
        cv.put("autor", livro.getAutor());
        cv.put("paginas", livro.getPaginas());

        db.insert("livro", null, cv);

        db.close();
    }

    /*
    O proximo metodo é para slecionar os valores que estao no DB
    como nao vamos retornar simente um valor de com.example.giovanne.minhabiblioteca.Livro e sim todos os atributos, nós
    criamos uma lista do tipo da Classe
    - Com o valor List<Classe> crio uma lista dos objetos desta class em questao
     */
    public List<Livro> selectTodosLivros(){

        //Criamos um obj de List<com.example.giovanne.minhabiblioteca.Livro> e colocamos no ArrayListde<livros>
        List<Livro> listaLivro = new ArrayList<Livro>();

        /*
        Para que ele nao retorne livroVazio eu recupero uma instancia de
        SQLiteDatabase mas agora do tipo Readable
         */
        //Logica
        SQLiteDatabase db = getReadableDatabase();

        //fazemos a query para select do db

        String sqlSelectTodosLivros = "SELECT * FROM livro";

        //aqui nos nao usamos um execSql e sim um rowQuery
        /*
        Colocamos isso dentro de um Cursor e verificamos a partir do primeiro
        item objCursor.moveToFirst().

        Nele temos o resultado da query
        criamos um if para verificar se ele conseguiu mover para o primeiro isso
        retorna um boolean, se conseguiu criamos um obj do tipo com.example.giovanne.minhabiblioteca.Livro e colocamos
        dentro dele com.example.giovanne.minhabiblioteca.Livro recebe com.example.giovanne.minhabiblioteca.Livro.setID(cursor.getInt(0) esse numero é o
        identificador da coluna e fazemos para todos os outros
         */
       Cursor c = db.rawQuery(sqlSelectTodosLivros, null);

        //varrendo os resultados verificando se possui os resuldados

        if(c.moveToFirst()){
            do {
                Livro livro = new Livro();
                livro.setId(c.getInt(0)); //0 é o index da coluna começando por 0
                livro.setTitulo(c.getString(1));
                livro.setAutor(c.getString(2));
                livro.setPaginas(c.getInt(3));

                listaLivro.add(livro);
            /*
            coloco dentro da lista de livro os valores

     retornados a partir da pesquisa
            */
            } while(c.moveToNext()); //continuar repetindo enquanto o cursor conseguir mover para o proximo
        }

        //Apos a transação é sempre importante fechar o DB
        db.close();
        //retornamos o objDeListaDeLivro
        return listaLivro;
    }
}
