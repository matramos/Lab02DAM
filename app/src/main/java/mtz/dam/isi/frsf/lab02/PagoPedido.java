package mtz.dam.isi.frsf.lab02;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class PagoPedido extends AppCompatActivity implements View.OnClickListener{
    private TextView menuTotal;
    private EditText usuario;
    private EditText mail;
    private EditText titularTarjeta;
    private EditText num1;
    private EditText num2;
    private EditText num3;
    private EditText num4;
    private EditText codSeguridad;
    private EditText mes;
    private EditText anio;
    private Button confirmar;
    private Button cancelar;
    private String menu;
    private Intent resultadoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pago_pedido);

        resultadoIntent = getIntent();

        setParametros();

        confirmar.setOnClickListener(this);
        cancelar.setOnClickListener(this);
    }


    private void setParametros() {
        menuTotal = (TextView)findViewById(R.id.compra);
        menu = "Menu: \n" + resultadoIntent.getExtras().getString("menu") + "\n" + getIntent().getExtras().getString("total");
        menuTotal.setText(menu);

        usuario = (EditText)findViewById(R.id.nombre);
        mail = (EditText)findViewById(R.id.mail);
        titularTarjeta = (EditText)findViewById(R.id.titular);
        num1 = (EditText)findViewById(R.id.num1);
        num2 = (EditText)findViewById(R.id.num2);
        num3 = (EditText)findViewById(R.id.num3);
        num4 = (EditText)findViewById(R.id.num4);
        codSeguridad = (EditText)findViewById(R.id.cod);
        mes = (EditText)findViewById(R.id.mes);
        anio = (EditText)findViewById(R.id.anio);
        confirmar = (Button)findViewById(R.id.confirmar);
        cancelar = (Button)findViewById(R.id.cancelar);
    }


    @Override
    public void onClick(View button) {
        switch (button.getId()) {
            case -1:
                break;
            case R.id.confirmar:
                if (noHayCamposVacios()) {
                    Toast.makeText(this, getResources().getString(R.string.toastPagado), Toast.LENGTH_SHORT).show();
                    resultadoIntent.putExtra("nombre", usuario.getText().toString());
                    resultadoIntent.putExtra("mail", mail.getText().toString());
                    resultadoIntent.putExtra("titular", titularTarjeta.getText().toString());
                    String numeroTarjeta = num1.getText().toString();
                    numeroTarjeta = numeroTarjeta + num2.getText().toString();
                    numeroTarjeta = numeroTarjeta + num3.getText().toString();
                    numeroTarjeta = numeroTarjeta + num4.getText().toString();
/*                resultadoIntent.putExtra("numTarjeta",numeroTarjeta);
                resultadoIntent.putExtra("codSeguridad",codSeguridad.getText().toString());
                resultadoIntent.putExtra("mes",mes.getText().toString());
                resultadoIntent.putExtra("anio",anio.getText().toString());*/
                    setResult(0, resultadoIntent);
                    finish();
                }
                break;
            case R.id.cancelar:
                setResult(1, resultadoIntent);
                finish();
                break;
        }
    }


    private boolean noHayCamposVacios(){
        if (usuario.getText().toString().isEmpty() || mail.getText().toString().isEmpty() || titularTarjeta.getText().toString().isEmpty() || num1.getText().toString().isEmpty() || num2.getText().toString().isEmpty() || num3.getText().toString().isEmpty() || num4.getText().toString().isEmpty() || codSeguridad.getText().toString().isEmpty() || mes.getText().toString().isEmpty() || anio.getText().toString().isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.completarCampos), Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }
}