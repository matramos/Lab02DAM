package mtz.dam.isi.frsf.lab02;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;

import mtz.dam.isi.frsf.lab02.modelo.Pedido;
import mtz.dam.isi.frsf.lab02.modelo.Utils;


public class MainActivity extends AppCompatActivity {

    private ToggleButton toggle;
    private Spinner spinner;
    private RadioGroup rdgGrupo;
    private Switch swich;
    private TextView txtRB;
    private ListView listView1;
    private Button add;
    private Button confirm;
    private Button reset;

    private Pedido pedido;
    private boolean confirmado;

    private ArrayAdapter<String> listAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggle = (ToggleButton)findViewById(R.id.cena);
        spinner = (Spinner)findViewById(R.id.horario);
        swich = (Switch)findViewById(R.id.reserva);
        rdgGrupo = (RadioGroup)findViewById(R.id.menuradio);
        txtRB = (TextView)findViewById(R.id.textRB);

        add = (Button)findViewById(R.id.agregar);
        confirm = (Button)findViewById(R.id.confirm);
        reset = (Button)findViewById(R.id.reset);
        confirmado = false;

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,R.array.time,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);


        rdgGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case -1:
                        txtRB.setText("NONE");
                        break;
                    case R.id.rb1:
                        txtRB.setText("plato");
                        break;
                    case R.id.rb2:
                        txtRB.setText("postre");
                        cargarLista();
                        break;
                    case R.id.rb3:
                        txtRB.setText("bebida");
                        break;
                    default:
                        txtRB.setText("NONE");
                }
            }
        });


        //Boton Reset
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==reset) {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }
            }
        });


        //Boton Agregar
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
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmado = true;
                /*
                pedido.setEsDelivery(toggle.isActivated());
                pedido.setHoraEntrega(spinner.toString());
                */
            }
        });

    }

    private void cargarLista () {
        String[] someColors = new String[]{"Red", "Orange", "Violet", "Black", "White"};
        ArrayList<String> colorArrayList = new ArrayList<String>();
        colorArrayList.addAll(Arrays.asList(someColors));
        listView1 = (ListView) findViewById(R.id.lvelementos);
        listAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, colorArrayList);
        listView1.setAdapter(listAdapter1);
        listView1.setVisibility(View.VISIBLE);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positon, long id) {
                String itemValue = (String) listView1.getItemAtPosition(positon);
                Toast.makeText(getApplicationContext(), itemValue + "...Pos:" + positon, Toast.LENGTH_SHORT).show();
                TextView sel = (TextView) findViewById(R.id.textRB);
                sel.setText(itemValue);
            }
        });
    }
}
