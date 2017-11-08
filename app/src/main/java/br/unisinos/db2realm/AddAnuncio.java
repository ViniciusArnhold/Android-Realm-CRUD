package br.unisinos.db2realm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import br.unisinos.db2realm.model.Anunciante;
import br.unisinos.db2realm.model.Anuncio;
import io.realm.Realm;
import io.realm.RealmChangeListener;

public class AddAnuncio extends AppCompatActivity {

    private EditText idAnuncianteText;
    private Button addAnuncioBotao;
    private EditText tituloText;
    private EditText descText;
    private EditText valorText;
    private CheckBox disponivelRadio;
    private String idAnunciante;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_anuncio);

        idAnuncianteText = (EditText) findViewById(R.id.edit_text_anunciante_anuncio);
        tituloText = (EditText) findViewById(R.id.edit_text_titulo);
        descText = (EditText) findViewById(R.id.edit_text_desc);
        valorText = (EditText) findViewById(R.id.edit_text_valor);
        disponivelRadio = (CheckBox) findViewById(R.id.edit_radio_disponivel);

        idAnunciante = getIntent().getStringExtra(AddUpdateUsuario.EXTRA_ANUNCIANTE_ID);

        Realm.getDefaultInstance().where(Anunciante.class)
                .equalTo("id", idAnunciante)
                .findFirstAsync().addChangeListener(new RealmChangeListener<Anunciante>() {
            @Override
            public void onChange(Anunciante anunciante) {
                idAnuncianteText.setText("Anunciante: " + anunciante.getNomeFantasia());

            }
        });

        addAnuncioBotao = (Button) findViewById(R.id.button_add_update_anuncio);

        addAnuncioBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(new Anuncio(idAnunciante,
                                tituloText.getText().toString(),
                                descText.getText().toString(),
                                Double.valueOf(valorText.getText().toString()),
                                disponivelRadio.isChecked()));
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast t = Toast.makeText(AddAnuncio.this, "Anuncio criado com sucesso!", Toast.LENGTH_SHORT);
                        t.show();
                        finish();
                    }
                });
            }
        });
    }
}
