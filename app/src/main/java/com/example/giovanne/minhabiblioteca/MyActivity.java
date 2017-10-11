package com.example.giovanne.minhabiblioteca;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Iterator;
import java.util.List;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Livro livroPrimeiro = new Livro(1, "O cara", "Giovanne", 345);

        DbHelper data = new DbHelper(this);

        data.insertLivro(livroPrimeiro);
        data.insertLivro(new Livro(1,"Amanha tem Bravir", "O Ricardão", 400));

        List<Livro> listLivro= data.selectTodosLivros();

        for(Iterator iterator = listLivro.iterator(); iterator.hasNext();){
            Livro livro = (Livro) iterator.next();

            Log.i("MinhaBiblioteca", livro.toString());
        }


    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
