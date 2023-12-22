package com.example.calculatorjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

public class CalculatorActivity extends AppCompatActivity {
    TextView resultTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        resultTv = findViewById(R.id.result_tv);
    }


    public void onDigitClick(View view) {
        if (view instanceof Button) {
            Button btn = ((Button) view);
            if (btn.getText().equals(".") && resultTv.getText().toString().contains(".")) {
                return;
            }
            resultTv.append(btn.getText());
            operatorClicked = false;
        }
    }

    String savedNum = "";
    String savedOperator = "";
    private boolean operatorClicked = false;



    public void onOperatorClick(View view) {
        if (view instanceof Button) {
            Button clickedOperator = ((Button) view);

            if (!operatorClicked) {
                if (savedNum.isEmpty()) {
                    savedNum = resultTv.getText().toString();
                } else if (!savedOperator.isEmpty()) {

                    String rhs = resultTv.getText().toString();
                    savedNum = calculate(savedNum, savedOperator, rhs);
                }

                savedOperator = clickedOperator.getText().toString();
                resultTv.setText("");
                operatorClicked = true;
            }
        }
    }
    public void onClearClick(View view){
        if (view instanceof Button) {
            resultTv.setText("");
            savedNum = "";
            savedOperator = "";
        }
    }
    public void onBackSpcClick(View view) {
        if (view instanceof Button) {
            int length = resultTv.getText().length();
            if (length > 0) {
                resultTv.setText(resultTv.getText().subSequence(0, length - 1));
            }
        }
    }

    public void onSqrtClick(View view) {
        if (view instanceof Button) {
            if (!resultTv.getText().toString().isEmpty()) {
                double num = Double.parseDouble(resultTv.getText().toString());
                if (num >= 0) {
                    double sqrtResult = Math.sqrt(num);
                    resultTv.setText(String.valueOf(sqrtResult));
                }else {
                    Toast.makeText(this, "can't sqrt -ve num", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onEqualClick(View view) {
        if (view instanceof Button) {
            if (savedOperator.isEmpty()) {
                savedNum = resultTv.getText().toString();
            } else {
                String rhs = resultTv.getText().toString();
                if (rhs.isEmpty()) {
                    return;
                }
                String res = calculate(savedNum, savedOperator, rhs);
                if (res.equals("Infinity") || res.equals("NaN")) {
                    Toast.makeText(this, "can't divide by 0", Toast.LENGTH_SHORT).show();
                    res = "";
                }
                resultTv.setText(res);
                savedNum = "";
                savedOperator = "";
            }
        }
        }


    public String calculate(String lhs, String operator, String rhs) {
        double n1 = Double.parseDouble(lhs); //double parses infinity too
        double n2 = Double.parseDouble(rhs);
        double res = 0;
        if ("+".equals(operator)) {
            res = n1 + n2;
        } else if ("-".equals(operator)) {
            res = n1 - n2;
        } else if ("÷".equals(operator)) {
            res = n1 / n2;
        } else if ("×".equals(operator)) {
            res = n1 * n2;
        }else if("%".equals(operator)){
            res=n1%n2;
        }
        return String.valueOf(res);

    }
}
