package br.unisinos.db2realm;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.unisinos.db2realm.model.Anunciante;
import io.realm.ObjectChangeSet;
import io.realm.Realm;
import io.realm.RealmObjectChangeListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Nullable;

public class AddUpdateUsuario extends AppCompatActivity implements DatePickerFragment.DateDialogListener {

    public static final String EXTRA_ANUNCIANTE_ID = "br.unisinnos.anuncioID";
    public static final String EXTRA_ADD_UPDATE = "br.unisinnos.add_update";
    public static final String MODE_UPDATE = "Update";
    public static final String MODE_ADD = "Add";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    private static final String DIALOG_DATE = "DialogDate";

    private ImageView calendarImage;
    private EditText nomeText;
    private EditText sobrenomeText;
    private EditText telefoneText;
    private EditText dataInicioText;
    private Button updateButton;

    private Anunciante novoUsuario;

    private String mode;
    private EditText classificacaoText;
    private EditText nomeFantasiaText;
    private EditText emailText;
    private String idUsuarioAntigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_anunciante);

        nomeText = (EditText) findViewById(R.id.edit_text_first_name);
        sobrenomeText = (EditText) findViewById(R.id.edit_text_last_name);
        dataInicioText = (EditText) findViewById(R.id.edit_text_data_inicio);

        calendarImage = (ImageView) findViewById(R.id.image_view_pick_data);

        telefoneText = (EditText) findViewById(R.id.edit_text_telefone);

        emailText = (EditText) findViewById(R.id.edit_text_email);
        classificacaoText = (EditText) findViewById(R.id.edit_text_classificacao);
        nomeFantasiaText = (EditText) findViewById(R.id.edit_text_nome_fantasia);

        updateButton = (Button) findViewById(R.id.button_add_anuncio);


        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
        if (mode.equals(MODE_UPDATE)) {
            idUsuarioAntigo = getIntent().getStringExtra(EXTRA_ANUNCIANTE_ID);
            readExistingUsuario(idUsuarioAntigo);

            updateButton.setText(getResources().getString(R.string.update_usuario));
        }

        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(manager, DIALOG_DATE);
            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Date dataInicio;

                try {
                    dataInicio = DATE_FORMAT.parse(dataInicioText.getText().toString());
                } catch (ParseException e) {
                    dataInicioText.setError("Invalida");
                    return;
                }
                Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if (mode.equals(MODE_ADD)) {
                            novoUsuario = new Anunciante(
                                    nomeText.getText().toString(),
                                    sobrenomeText.getText().toString(),
                                    telefoneText.getText().toString(),
                                    emailText.getText().toString(),
                                    dataInicio,
                                    Double.parseDouble(classificacaoText.getText().toString()),
                                    nomeFantasiaText.getText().toString());

                            //Persisimos o valor.
                            realm.copyToRealm(novoUsuario);

                        } else {
                            Anunciante antigoUsuario = realm.where(Anunciante.class)
                                    .equalTo("id", idUsuarioAntigo)
                                    .findFirst();
                            //Como estamos fazendo um UPDATE podemos apenas modificar o usuario existente.
                            antigoUsuario.setNome(nomeText.getText().toString());
                            antigoUsuario.setSobrenome(sobrenomeText.getText().toString());
                            antigoUsuario.setTelefone(telefoneText.getText().toString());
                            antigoUsuario.setEmail(emailText.getText().toString());
                            antigoUsuario.setDataInicio(dataInicio);
                            antigoUsuario.setClassificacao(Double.parseDouble(classificacaoText.getText().toString()));
                            antigoUsuario.setNomeFantasia(nomeFantasiaText.getText().toString());
                        }
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        AddUpdateUsuario.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast t = Toast.makeText(AddUpdateUsuario.this, "Concluido com sucesso!", Toast.LENGTH_SHORT);
                                t.show();
                                finish();
                            }
                        });
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast t = Toast.makeText(AddUpdateUsuario.this, "Erro ao executar a transacao: " + error.getMessage(), Toast.LENGTH_SHORT);
                        t.show();
                    }
                });
            }
        });
    }


    private void readExistingUsuario(String id) {
        Realm.getDefaultInstance().where(Anunciante.class).equalTo("id", id).findFirstAsync().addChangeListener(new RealmObjectChangeListener<Anunciante>() {
            @Override
            public void onChange(Anunciante usuario, @Nullable ObjectChangeSet changeSet) {
                Anunciante antigoUsuario = Realm.getDefaultInstance().where(Anunciante.class)
                        .equalTo("id", idUsuarioAntigo)
                        .findFirst();

                nomeText.setText(antigoUsuario.getNome());
                sobrenomeText.setText(antigoUsuario.getSobrenome());
                dataInicioText.setText(DATE_FORMAT.format(antigoUsuario.getDataInicio()));
                telefoneText.setText(antigoUsuario.getTelefone());
                classificacaoText.setText(Double.toString(antigoUsuario.getClassificacao()));
                nomeFantasiaText.setText(antigoUsuario.getNomeFantasia());
            }
        });
    }

    @Override
    public void onFinishDialog(Date date) {
        dataInicioText.setText(DATE_FORMAT.format(date));
    }
}


