package com.andrelara.calculadorasimples;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView visorResultado;
    private String digitado = "0";
    private String operadorSelecionado;
    private String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        visorResultado = (TextView) findViewById(R.id.visor);
        visorResultado.setText(digitado);
        digitado = "";

        // Change the action bar color and subtitle Mudando subtitulo e cor da action bar
        getSupportActionBar().setSubtitle("Paleta de cores: Blue + Lime");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1976D2")));
    }

    private void atualizaTela(){
        visorResultado.setText(digitado);
    }
    private void clear(){
        digitado = "";
        operadorSelecionado = "";
        resultado = "";
    }
    public void onClickClear(View v){
        clear();
        atualizaTela();
    }

    public void onClickNumero(View v){
        if(resultado != "") {
            clear();
            atualizaTela();
        }

        Button numero = (Button) v;
        digitado += numero.getText();
        atualizaTela();
    }

    private boolean ehOperador(char op){
        switch (op){
            case '%':
            case 'x':
            case '-':
            case '+': return true;
            default: return false;
        }
    }
    private double operacao(String n1, String n2, String op){
        switch (op){
            case "%": return Double.valueOf(n1) / Double.valueOf(n2);
            case "x": return Double.valueOf(n1) * Double.valueOf(n2);
            case "-": return Double.valueOf(n1) - Double.valueOf(n2);
            case "+": return Double.valueOf(n1) + Double.valueOf(n2);
            default: return -1;
        }
    }
    private boolean recebeResultado(){
        if (operadorSelecionado == "") return false;

        String[] numeros = digitado.split(Pattern.quote(operadorSelecionado));
        if (numeros.length < 2) return false;

        resultado = String.valueOf(operacao(numeros[0], numeros[1], operadorSelecionado));
        return true;
    }

    public void onClickOperador(View v){
        if (digitado == "") return;

        Button operador = (Button) v;

        if (resultado != ""){
            String _digitado = resultado;
            clear();
            digitado = _digitado;
        }
        if(operadorSelecionado != ""){
            if (ehOperador(digitado.charAt(digitado.length()-1))){
                digitado = digitado.replace(digitado.charAt(digitado.length()-1), operador.getText().charAt(0));
                operadorSelecionado = operador.getText().toString();

                atualizaTela();
                return;
            }else {
                recebeResultado();
                digitado = resultado;
                resultado = "";
            }
        }
        digitado += operador.getText();
        operadorSelecionado = operador.getText().toString();
        atualizaTela();
    }
    public void onClickIgual(View v){
        if (digitado == "") return;
        if (!recebeResultado())return;
        visorResultado.setText(digitado + "\n" + String.valueOf(resultado));
    }
}
