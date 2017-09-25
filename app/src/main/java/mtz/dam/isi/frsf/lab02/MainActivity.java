package mtz.dam.isi.frsf.lab02;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rdgGrupo;
    private TextView lblChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rdgGrupo = (RadioGroup)findViewById(R.id.menuradio);
    }


    @Override
    public void onCheckedChanged(RadioGroup rdgGrupo, int checkedId) {
        // TODO Auto-generated method stub
        if (checkedId == R.id.rbop1){
            lblChecked.setText("Ha pulsado el botón 1");
        }else if (checkedId == R.id.rbop2){
            lblChecked.setText("Ha pulsado el botón 2");
        }else if (checkedId == R.id.rbop3){
            lblChecked.setText("Ha pulsado el botón 3");
        }

    }

}
