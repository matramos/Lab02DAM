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
        rdgGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                TextView txtRB = (TextView)findViewById(R.id.textRB);
                switch (checkedId) {
                    case R.id.plato:
                        txtRB.setText("plato");
                        break;
                    case R.id.postre:
                        txtRB.setText("postre");
                        break;
                    case R.id.bebida:
                        txtRB.setText("bebida");
                        break;
                    default:
                        txtRB.setText("NONE");
                }
            }
        });
    }
}
