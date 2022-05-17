package com.exercicios.imc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ImcActivity extends AppCompatActivity {

    private EditText editHeight;
    private EditText editWeight;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);
        getView();
        setBtnSend();
    }

    public void getView() {
        editHeight = findViewById(R.id.edit_imc_height);
        editWeight = findViewById(R.id.edit_imc_weight);
        btnSend = findViewById(R.id.btn_imc_send);
    }

    public void setBtnSend() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDados();
            }
        });
    }

    private double getDados() {
        String sHeight = editHeight.getText().toString();
        String sWeight = editWeight.getText().toString();

        int height = Integer.parseInt(sHeight);
        int weight = Integer.parseInt(sWeight);

        double result = calculateIMC(height, weight);

        return result;
    }

    private boolean alertErro() {
        if (!validate()) {
            Toast.makeText(ImcActivity.this, R.string.fields_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void alertDialog() {
        double result = getDados();
        int imcResponseId = respostaIMC(result);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, result))
                .setMessage(imcResponseId)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();

        dialog.show();

        InputMethodManager imn = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imn.hideSoftInputFromWindow(editWeight.getWindowToken(), 0);
        imn.hideSoftInputFromWindow(editHeight.getWindowToken(), 0);
    }

    private void startDados() {
        if (!alertErro()) {
            return;
        }

        getDados();
        alertDialog();
    }

    private double calculateIMC(int height, int weight) {
        return weight / (((double) height / 100) * ((double) height / 100));
    }

    private int respostaIMC(double imc) {
        if (imc < 15) {
            return R.string.imc_severely_high_weight;
        } else if (imc < 16) {
            return R.string.imc_very_low_weight;
        } else if (imc < 18.5) {
            return R.string.imc_low_weight;
        } else if (imc < 25) {
            return R.string.normal;
        } else if (imc < 30) {
            return R.string.imc_high_weight;
        } else if (imc < 35) {
            return R.string.imc_so_high_weight;
        } else if (imc < 40) {
            return R.string.imc_severely_high_weight;
        } else
            return R.string.imc_extreme_weight;
    }


    private boolean validate() {
        return (
                !editHeight.getText().toString().startsWith("0")
                        && !editWeight.getText().toString().startsWith("0") &&
                        !editHeight.getText().toString().isEmpty()
                        && !editWeight.getText().toString().isEmpty());
    }
}