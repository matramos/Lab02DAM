package mtz.dam.isi.frsf.lab02;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import mtz.dam.isi.frsf.lab02.modelo.Pedido;
import mtz.dam.isi.frsf.lab02.modelo.Utils;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private Utils utils = new Utils();
    private ToggleButton reserva;
    private Switch notificar;
    private Spinner spnHorario;
    private TextView tvPedidos;
    private RadioGroup rgOpcionesPlato;
    private Button buttonAgregar, buttonConfirmar, buttonReiniciar;
    private ListView listViewOpciones;
    private ArrayList<Utils.ElementoMenu> listElementos;
    private ArrayAdapter<Utils.ElementoMenu> listAdapterOpciones;
    private Utils.ElementoMenu pedidoActual;
    private ArrayAdapter<CharSequence> adapterSpinner;
    private double cuenta;
    private boolean pedidoConfirmado;

    private Utils.ElementoMenu[] listBebidas;
    private Utils.ElementoMenu[] listPlatos;
    private Utils.ElementoMenu[] listPostre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setParametros();

        listViewOpciones.setAdapter(listAdapterOpciones);
        listViewOpciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        spnHorario.setAdapter(adapterSpinner);

        rgOpcionesPlato.setOnCheckedChangeListener(this);
        listViewOpciones.setOnItemClickListener(this);
        buttonAgregar.setOnClickListener(this);
        buttonConfirmar.setOnClickListener(this);
        buttonReiniciar.setOnClickListener(this);

        tvPedidos.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setParametros() {
        reserva = (ToggleButton) findViewById(R.id.reserva);
        reserva.setTextOff(getResources().getText(R.string.btn_delivery));
        reserva.setTextOn(getResources().getText(R.string.btn_reservar));

        notificar = (Switch) findViewById(R.id.recordatorio);
        notificar.setText(getResources().getText(R.string.switch_notificacion));

        spnHorario = (Spinner) findViewById(R.id.horario);
        adapterSpinner = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);

        tvPedidos = (TextView) findViewById(R.id.menu);
        tvPedidos.setText(getResources().getText(R.string.menu));

        rgOpcionesPlato = (RadioGroup) findViewById(R.id.menuradio);

        buttonAgregar = (Button) findViewById(R.id.agregar);
        buttonConfirmar = (Button) findViewById(R.id.confirm);
        buttonReiniciar = (Button) findViewById(R.id.reset);
        listViewOpciones = (ListView) findViewById(R.id.lvelementos);

        listElementos = new ArrayList<>();
        listAdapterOpciones = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, listElementos);
        utils.iniciarListas();
        listPlatos = utils.getListaPlatos();
        listPostre = utils.getListaPostre();
        listBebidas = utils.getListaBebidas();
        cuenta = 0;
        pedidoConfirmado = false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case -1:
                break;
            case R.id.rb1:
                cambiarLista(listPlatos);
                break;
            case R.id.rb2:
                cambiarLista(listPostre);
                break;
            case R.id.rb3:
                cambiarLista(listBebidas);
                break;
        }
    }

    private void cambiarLista(Utils.ElementoMenu[] lista) {
        listElementos.clear();
        listElementos.addAll(Arrays.asList(lista));
        actualizarVistaLista();
    }

    private void actualizarVistaLista() {
        listViewOpciones.clearChoices();
        pedidoActual = null;
        listViewOpciones.setItemChecked(-1, true);
        listAdapterOpciones.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case -1:
                break;
            case R.id.lvelementos:
                pedidoActual = (Utils.ElementoMenu) listViewOpciones.getItemAtPosition(i);
                break;
        }
    }

    @Override
    public void onClick(View button) {
        switch (button.getId()) {
            case -1:
                break;
            case R.id.agregar:
                agregarPedido();
                actualizarVistaLista();
                break;
            case R.id.confirm:
                confirmarPedido();
                actualizarVistaLista();
                break;
            case R.id.reset:
                reiniciarPedido();
                actualizarVistaLista();
                break;
        }
    }

    private void agregarPedido() {
        if (pedidoConfirmado) {
            Toast.makeText(this, getResources().getString(R.string.toastPedidoYaConfirmado), Toast.LENGTH_SHORT).show();
        } else {
            if (pedidoActual != null) {
                String textoPedido = tvPedidos.getText() + (tvPedidos.getText().toString().equals("") ? "" : "\n") + pedidoActual;
                cuenta += pedidoActual.getPrecio();
                tvPedidos.setText(textoPedido);
            } else {
                Toast.makeText(this, getResources().getString(R.string.toastUnselected), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void confirmarPedido() {
        if (pedidoConfirmado) {
            Toast.makeText(this, getResources().getString(R.string.toastPedidoYaConfirmado), Toast.LENGTH_SHORT).show();
        } else {
            if (cuenta == 0) {
                Toast.makeText(this, getResources().getString(R.string.toastPedidoVacio), Toast.LENGTH_SHORT).show();
            } else {
                String textoPedido = getResources().getString(R.string.total);
                textoPedido = String.format(Locale.getDefault(), textoPedido, cuenta);
                textoPedido = tvPedidos.getText() + "\n" + textoPedido;
                tvPedidos.setText(textoPedido);
                pedidoConfirmado = true;
            }
        }
    }

    private void reiniciarPedido() {
        tvPedidos.setText(getResources().getText(R.string.menu));
        cuenta = 0;
        pedidoConfirmado = false;
        rgOpcionesPlato.clearCheck();
        listAdapterOpciones.clear();
        actualizarVistaLista();
    }
}