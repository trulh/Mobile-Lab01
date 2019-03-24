package example.lab01;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Toast;


public class TransactionsActivity extends AppCompatActivity {

    private int sizeOfList = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_transactions);
        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        if (!getIntent().getStringExtra("Size").equals("") && !getIntent().getStringExtra("Size").equals("......"))
            sizeOfList = Integer.parseInt(getIntent().getStringExtra("Size"));

        for (int i = 0; i < sizeOfList; i++){
            final TextView tv = new TextView(this);
            tv.setText(getIntent().getStringExtra("transaction"+i));
            tv.setClickable(true);
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String[] toost = tv.getText().toString().split("\\|");
                    Toast.makeText(getBaseContext(), toost[1] + " " + toost[2].trim(), Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            ll.addView(tv);
        }

        this.setContentView(sv);
    }
}
