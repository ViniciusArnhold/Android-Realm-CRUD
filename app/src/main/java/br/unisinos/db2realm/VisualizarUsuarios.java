package br.unisinos.db2realm;

import android.app.ListActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.unisinos.db2realm.model.Anunciante;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;

import javax.annotation.Nullable;

public class VisualizarUsuarios extends ListActivity {

    private RealmResults<Anunciante> anuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_employees);

        //Lazy list
        anuncios = Realm.getDefaultInstance().where(Anunciante.class)
                .findAllAsync();


        anuncios.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Anunciante>>() {
            @Override
            public void onChange(@NonNull RealmResults<Anunciante> anuncios, @Nullable OrderedCollectionChangeSet changeSet) {
                //Este metodo é chamado quando o select completa pela primeira vez e toda vez que os usuarios sofrem modificacao, assim temos uma view dinamica.
                setListAdapter(new ArrayAdapter<>(VisualizarUsuarios.this, android.R.layout.simple_list_item_1, anuncios));
            }
        });

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idAn = ((Anunciante) parent.getItemAtPosition(position)).getId();

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(idAn, idAn);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(VisualizarUsuarios.this, "Copiado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        anuncios.removeAllChangeListeners();
    }
}


