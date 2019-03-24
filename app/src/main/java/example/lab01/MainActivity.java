package example.lab01;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String PARAM_NAME = "balance";
    public static final int RESULT_CODE_TRANSFER = 0;
    public int bankBalance;
    public TextView changingText;
    public ArrayList<String> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bankBalance = (int) (Math.random() * 20 + 90);
        transactions = new ArrayList<String>();
        String start = getCurrentTime();
        start = String.format("%-15s| %-15s| %-15s| %-15s",start, "Angel", bankBalance, bankBalance);
        transactions.add(start);

        changingText = findViewById(R.id.lbl_balance);
        changingText.setText(bankBalance+"");

        Button transferButton = findViewById(R.id.btn_transfer);
        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String balance = changingText.getText().toString();
                final Intent actionTransfer = new Intent(MainActivity.this, TransferActivity.class);
                final Bundle bundle = new Bundle();
                bundle.putString(PARAM_NAME, balance);
                actionTransfer.replaceExtras(bundle);
                startActivityForResult(actionTransfer, RESULT_CODE_TRANSFER);
            }
        });
        Button transactionsButton = findViewById(R.id.btn_transactions);
        transactionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent actionTransactions = new Intent(MainActivity.this, TransactionsActivity.class);
                final Bundle trans = new Bundle();

                for (int i = 0; i < transactions.size(); i++){
                    trans.putString("transaction" + i, transactions.get(i));
                }
                trans.putString("Size", Integer.toString(transactions.size()));
                actionTransactions.replaceExtras(trans);
                startActivity(actionTransactions);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if (requestCode != RESULT_CODE_TRANSFER) return;

        if (resultCode != Activity.RESULT_OK) return;

        final String bal = data.getStringExtra(PARAM_NAME);
        transactions.add(data.getStringExtra(TransferActivity.PARAM_TRANS));
        changingText.setText(bal);
    }

    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        System.out.println(formattedDate);
        return formattedDate;
    }
}
