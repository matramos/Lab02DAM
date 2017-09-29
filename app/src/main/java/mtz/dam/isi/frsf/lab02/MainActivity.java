package mtz.dam.isi.frsf.lab02;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private RadioGroup rdgGrupo;
    private TextView lbMenu;
    private TextView lblChecked;

    private class Pedido {
        private Integer id;
        private boolean reservar;
        private CharSequence time;
        private Utils.ElementoMenu[] food[];
        private Integer amount;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pedido menu = new Pedido();

        ToggleButton toggle = new ToggleButton().findViewById(R.id.cena);
        menu.reservar = toggle.isChecked();



        Spinner spinner = (Spinner) findViewById(R.id.horario);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.time,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
                    //Agregar elemento


                    // Si el pedido ya fue confirmado:
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.toastPedidoYaConfirmado, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        //Boton Confirmar
        Button confirm = (Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
