package br.unisinos.db2realm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.unisinos.db2realm.model.Anunciante;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Button addAnuncianteButton;
    private Button editAnuncianteButton;
    private Button deleteButton;
    private Button visualizarTodosButton;
    private Button addAnuncioButton;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        addAnuncioButton = (Button) findViewById(R.id.button_add_anuncio);
        addAnuncianteButton = (Button) findViewById(R.id.button_add_usuario);
        editAnuncianteButton = (Button) findViewById(R.id.button_edit_usuario);
        deleteButton = (Button) findViewById(R.id.button_delete_usuario);
        visualizarTodosButton = (Button) findViewById(R.id.button_view_anuncios);

        realm = Realm.getDefaultInstance();

        addAnuncianteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddUpdateUsuario.class);
                i.putExtra(AddUpdateUsuario.EXTRA_ADD_UPDATE, AddUpdateUsuario.MODE_ADD);
                startActivity(i);
            }
        });

        editAnuncianteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnuncianteParaAtualizar();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnuncianteParaRemover();
            }
        });
        visualizarTodosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, VisualizarUsuarios.class);
                startActivity(i);
            }
        });
        addAnuncioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnuncianteParaCriarAnuncio();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void selectAnuncianteParaCriarAnuncio() {
        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialogo_visualizar_usuario, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(getEmpIdView);

        final EditText idInput = (EditText) getEmpIdView.findViewById(R.id.edit_text_id_anunciante_input);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(MainActivity.this, AddAnuncio.class);
                        i.putExtra(AddUpdateUsuario.EXTRA_ANUNCIANTE_ID, idInput.getText().toString());
                        startActivity(i);
                    }
                }).create()
                .show();

    }

    public void selectAnuncianteParaAtualizar() {
        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialogo_visualizar_usuario, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(getEmpIdView);

        final EditText idInput = (EditText) getEmpIdView.findViewById(R.id.edit_text_id_anunciante_input);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(MainActivity.this, AddUpdateUsuario.class);
                        i.putExtra(AddUpdateUsuario.EXTRA_ADD_UPDATE, AddUpdateUsuario.MODE_UPDATE);
                        i.putExtra(AddUpdateUsuario.EXTRA_ANUNCIANTE_ID, idInput.getText().toString());
                        startActivity(i);
                    }
                }).create()
                .show();
    }

    public void selectAnuncianteParaRemover() {

        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialogo_visualizar_usuario, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(getEmpIdView);

        final EditText idInput = (EditText) getEmpIdView.findViewById(R.id.edit_text_id_anunciante_input);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final long idUsuario = Long.parseLong(idInput.getText().toString());
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.where(Anunciante.class).equalTo("id", idUsuario)
                                        .findFirst()
                                        .deleteFromRealm();
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                Toast t = Toast.makeText(MainActivity.this, "Anunciante removido com sucesso!", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        });
                    }
                }).create()
                .show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}


