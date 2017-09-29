package mtz.dam.isi.frsf.lab02;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import mtz.dam.isi.frsf.lab02.modelo.Pedido;
import mtz.dam.isi.frsf.lab02.modelo.Utils;


public class MainActivity extends AppCompatActivity {

    private ToggleButton toggle;
    private Spinner spinner;
    private RadioGroup rdgGrupo;
    private Switch swich;

    private Pedido pedido;
    private boolean confirmado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggle = (ToggleButton)findViewById(R.id.cena);
        spinner = (Spinner) findViewById(R.id.horario);
        rdgGrupo = (RadioGroup)findViewById(R.id.menuradio);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.time,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


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

        //Boton Reset
        final Button reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==reset) {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }
            }
        });

        //Boton Agregar
        Button add = (Button)findViewById(R.id.agregar);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rdgGrupo.getCheckedRadioButtonId()==-1){
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.toastUnselected, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    if(confirmado){
                        // Si el pedido ya fue confirmado:
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.toastPedidoYaConfirmado, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        //Agregar elemento
                    }
                }
            }
        });

        //Boton Confirmar
        Button confirm = (Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmado = true;
                pedido.setEsDelivery(toggle.isActivated());
                pedido.setHoraEntrega(spinner.toString());
            }
        });
    }
}
