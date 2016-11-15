package mz.org.fgh.mentoring.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.dao.TutoredDao;
import mz.org.fgh.mentoring.dao.TutoredDaoImpl;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.util.TutoredUtil;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */

public class TutoredActivity  extends AppCompatActivity {

    private TutoredUtil tutoredUtil;
    private TutoredDao tutoredDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutored_activity);
        tutoredUtil = new TutoredUtil(TutoredActivity.this);
        showMenuBar();

    }

    private void showMenuBar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.tutored_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        tutoredDao = new TutoredDaoImpl(this);
        switch (item.getItemId()){
            case R.id.save_tutored:
                Tutored tutored = tutoredUtil.getTutored();
                tutoredDao.create(tutored);
                tutoredDao.close();
                Toast.makeText(TutoredActivity.this, "Save Tutored", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
