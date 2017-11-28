package mtz.dam.isi.frsf.lab02;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
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
import java.util.Date;
import java.util.Locale;

import mtz.dam.isi.frsf.lab02.modelo.Pedido;
import mtz.dam.isi.frsf.lab02.modelo.Tarjeta;
import mtz.dam.isi.frsf.lab02.modelo.Utils;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private ToggleButton reserva;
    private Switch notificar;
    private Spinner spnHorario;
    private TextView tvPedidos;
    private TextView total;
    private RadioGroup rgOpcionesPlato;
    private Button buttonAgregar, buttonConfirmar, buttonReiniciar;
    private ListView listViewOpciones;
    private Utils utils = new Utils();
    private Pedido pedido = new Pedido();
    private Tarjeta tarjeta = new Tarjeta();
    private Utils.ElementoMenu pedidoActual;
    private ArrayList<Utils.ElementoMenu> listElementos;
    private ArrayAdapter<Utils.ElementoMenu> listAdapterOpciones;
    private ArrayAdapter<CharSequence> adapterSpinner;
    private double cuenta;
    private int pedidoConfirmado; //sin confirmar 0, confirmado 1, pago cancelado -1

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
        reserva.setTextOn(getResources().getText(R.string.btn_delivery));
        reserva.setTextOff(getResources().getText(R.string.btn_reservar));

        notificar = (Switch) findViewById(R.id.recordatorio);
        notificar.setText(getResources().getText(R.string.switch_notificacion));

        spnHorario = (Spinner) findViewById(R.id.horario);
        adapterSpinner = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        tvPedidos = (TextView) findViewById(R.id.menu);
        total = (TextView) findViewById(R.id.total);

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
        pedidoConfirmado = 0;
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

    private void mensajeToast(String mensaje,int duracion){
        Toast toast = Toast.makeText(this, mensaje, duracion);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    private void agregarPedido() {
        if (pedidoConfirmado != 0) mensajeToast(getResources().getString(R.string.toastPedidoYaConfirmado), Toast.LENGTH_SHORT);
        else {
            if (pedidoActual != null) {
                if (setearMenu() == 0) {
                    String textoPedido = tvPedidos.getText() + (tvPedidos.getText().toString().equals("") ? "" : "\n") + pedidoActual;
                    cuenta += pedidoActual.getPrecio();
                    tvPedidos.setText(textoPedido);
                } else mensajeToast(getResources().getString(R.string.toastSelected), Toast.LENGTH_SHORT);
            } else mensajeToast(getResources().getString(R.string.toastUnselected), Toast.LENGTH_SHORT);
        }
    }

    private int setearMenu(){
        switch (pedidoActual.getTipo()){
            case PRINCIPAL:
                if (pedido.getPlato() != null) return 1; else pedido.setPlato(pedidoActual);
                break;
            case POSTRE:
                if (pedido.getPostre() != null) return 1; else pedido.setPostre(pedidoActual);
                break;
            case BEBIDA:
                if (pedido.getBebida() != null) return 1; else pedido.setBebida(pedidoActual);
                break;
        }
        return 0;
    }

    private void confirmarPedido() {
        if (pedidoConfirmado ==1) mensajeToast(getResources().getString(R.string.toastPedidoYaConfirmado), Toast.LENGTH_SHORT);
        else {
            if (cuenta == 0) mensajeToast(getResources().getString(R.string.toastPedidoVacio), Toast.LENGTH_SHORT);
            else {
                String totalPedido = getResources().getString(R.string.total);
                totalPedido = String.format(Locale.getDefault(), totalPedido, cuenta);
                total.setText(totalPedido);
                total.setVisibility(View.VISIBLE);
                setearPedido();
                Intent calcularResultado = new Intent(this,PagoPedido.class);
                calcularResultado.putExtra("menu",tvPedidos.getText().toString());
                calcularResultado.putExtra("total",totalPedido);
                startActivityForResult(calcularResultado,1);
                pedidoConfirmado = 1;
            }
        }
    }

    private void setearPedido(){
        pedido.setEsDelivery(reserva.isChecked());
        pedido.setHoraEntrega(spnHorario.getSelectedItem().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            // Request entre las activities
            if (resultCode == -1) mensajeToast("ERROR resultCode", Toast.LENGTH_SHORT);
            else if (resultCode == 0) {
                Bundle datos = data.getExtras();
                pedido.setNombreCliente(datos.getString("nombre"));
                pedido.setEmail(datos.getString("mail"));
                tarjeta.setNombre(datos.getString("titular"));
                pedido.setNombre(tarjeta.getNombre());
                String toastConfirmacion = getResources().getString(R.string.toastPagado) + "\n" + total.getText().toString();
                mensajeToast(toastConfirmacion, Toast.LENGTH_LONG);
            }
            else if (resultCode == 1) {
                pedidoConfirmado = -1;
                mensajeToast(getResources().getString(R.string.toastCancelado), Toast.LENGTH_LONG);
            }
        }
    }


    private void reiniciarPedido() {
        reserva.setChecked(false);
        spnHorario.setSelection(0);
        notificar.setChecked(false);
        tvPedidos.setText(null);
        total.setText(null);
        total.setVisibility(View.GONE);
        cuenta = 0;
        pedidoConfirmado = 0;
        rgOpcionesPlato.clearCheck();
        listAdapterOpciones.clear();
        actualizarVistaLista();
        pedido = new Pedido();
    }


/*
    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        reserva.setChecked(savedInstanceState.getBoolean("esDelivery"));
        spnHorario.setSelection(savedInstanceState.getInt("spinner",0));
        notificar.setChecked(savedInstanceState.getBoolean("recordatorio"));
        tvPedidos.setText(savedInstanceState.getString("menu"));
        total.setText(savedInstanceState.getString("total"));
        rgOpcionesPlato.check(savedInstanceState.getInt("radiogroup",0));
        pedido = (Pedido) savedInstanceState.get("elPedido");
        pedidoConfirmado = savedInstanceState.getBoolean("confirmado");
    }

    @Override
    protected void onSaveInstanceState (Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("esDelivery",reserva.isChecked());
        savedInstanceState.putInt("spinner", spnHorario.getSelectedItemPosition());
        savedInstanceState.putBoolean("recordatorio",notificar.isChecked());
        savedInstanceState.putString("menu",tvPedidos.getText().toString());
        savedInstanceState.putString("total",total.getText().toString());
        savedInstanceState.putInt("radiogroup",rgOpcionesPlato.getCheckedRadioButtonId());
        savedInstanceState.putSerializable("elPedido",pedido);
        savedInstanceState.putBoolean("confirmado",pedidoConfirmado);
    }
    */
}