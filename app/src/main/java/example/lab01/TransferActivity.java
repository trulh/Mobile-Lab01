package example.lab01;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TransferActivity extends AppCompatActivity {

    public static final String PARAM_TRANS = "trans";
    private TextView txtAmount;
    private Button pay_btn;
    private String newBalance;
    private Spinner friend;
    private String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        this.txtAmount = findViewById(R.id.txt_amount);
        this.pay_btn = findViewById(R.id.btn_pay);
        this.friend = findViewById(R.id.spinner);
        txtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableButton();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    public void enableButton() {
        Boolean isReady = false;
        this.txtAmount = findViewById(R.id.txt_amount);
        final String strAmount = txtAmount.getText().toString();

        final String balance = getIntent().getStringExtra(MainActivity.PARAM_NAME);

        final TextView amountCheck = findViewById(R.id.lbl_amount_check);

        if (!strAmount.equals("") && !strAmount.equals("......") && !balance.equals("") && !balance.equals("......")) {
            int intAmount = Integer.parseInt(strAmount);
            int intBalance = Integer.parseInt(balance);
            if (intAmount < intBalance && intAmount > 0) {
                isReady=true;
                onButtonPress();
                amountCheck.setText("");
            }
            else{
                amountCheck.setText("Amount is outside of bounds");
            }
        }
        pay_btn.setEnabled(isReady);
    }
    public void onButtonPress(){
        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent transferDone = new Intent(TransferActivity.this, MainActivity.class);
                final String strAmount = txtAmount.getText().toString();
                final String balance = getIntent().getStringExtra(MainActivity.PARAM_NAME);
                if (!strAmount.equals("") && !strAmount.equals("......") && !balance.equals("") && !balance.equals("......")) {
                    int intAmount = Integer.parseInt(strAmount);
                    int intBalance = Integer.parseInt(balance);
                    newBalance = Integer.toString((intBalance - intAmount));
                }

                String transaction = "";
                currentTime = getCurrentTimeUsingDate();
                getCurrentTimeUsingDate();
                transaction = String.format("%-15s| %-15s| %-15s| %-15s", currentTime, friend.getSelectedItem().toString(), strAmount, newBalance);
                transferDone.putExtra(MainActivity.PARAM_NAME, newBalance);
                transferDone.putExtra(PARAM_TRANS, transaction);
                setResult(Activity.RESULT_OK, transferDone);
                finish();
            }
        });
    }

    public static String getCurrentTimeUsingDate() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}

