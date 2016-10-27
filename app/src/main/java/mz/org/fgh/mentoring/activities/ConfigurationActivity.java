package mz.org.fgh.mentoring.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.callback.ConfigCallback;

public class ConfigurationActivity extends BaseAuthenticateActivity {

    private ListView configItems;
    private ActionMode actionMode;
    private ArrayAdapter<String> adapter;
    private EditText ipField;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_configuration);

        ipField = (EditText) findViewById(R.id.config_server_ip);

        configItems = (ListView) findViewById(R.id.config_items);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, Arrays.asList("Unidades Sanitárias", "Carreiras"));
        configItems.setAdapter(adapter);

        configItems.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        configItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = (String) parent.getItemAtPosition(position);

                if (actionMode == null) {
                    actionMode = startSupportActionMode(new ConfigCallback(ConfigurationActivity.this));
                }
            }
        });

        ipField.setText(application.getSharedPreferences().getString(getResources().getString(R.string.serve_address), ""));
    }

    public void setActionMode(ActionMode actionMode) {
        this.actionMode = actionMode;
    }

    public void clearSelection() {
        configItems.clearChoices();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.config_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.config_menu_ok:
                if (ipField.getText().length() == 0) {
                    ipField.setError("Servidor invalido!");
                    return true;
                }

                SharedPreferences.Editor editor = application.getSharedPreferences().edit();
                editor.putString(getResources().getString(R.string.serve_address), ipField.getText().toString());
                editor.commit();

                Toast.makeText(this, "Endereçao do servidor actulizado!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return true;
    }
}
