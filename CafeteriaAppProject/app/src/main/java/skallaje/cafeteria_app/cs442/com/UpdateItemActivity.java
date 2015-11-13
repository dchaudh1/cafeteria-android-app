package skallaje.cafeteria_app.cs442.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateItemActivity extends Activity {

    Integer getid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MenuActivity.Val);
        String[] values = message.split(":");
        setContentView(R.layout.activity_update_item);
        EditText et_i = (EditText)findViewById(R.id.item_u);
        et_i.setText(values[1]);

        EditText et_p = (EditText)findViewById(R.id.price_u);
        et_p.setText(values[2]);

        EditText et_d = (EditText)findViewById(R.id.desc_u);
        et_d.setText(values[3]);

        getid = Integer.valueOf(values[0]);

        Log.d("Hi ", "" + values[0] + "" + values[1]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean updateNew(View v){
        DatabaseFunctions dbf = new DatabaseFunctions(this);
        dbf.open();

        EditText item = (EditText) findViewById(R.id.item_u);
        EditText price = (EditText) findViewById(R.id.price_u);
        EditText desc = (EditText) findViewById(R.id.desc_u);

        if(item.getText().toString().trim().length() == 0 ) {
            Toast.makeText(this, "ITEM cannot be blank!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(price.getText().toString().trim().length() == 0 ) {
            Toast.makeText(this, "PRICE cannot be blank!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(isFloat(price.getText().toString().trim())== false){
            Toast.makeText(this, "PRICE must be a number!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(desc.getText().toString().trim().length() == 0 ) {
            Toast.makeText(this, "DESCRIPTION cannot be blank!", Toast.LENGTH_LONG).show();
            return false;
        }

        dbf.updateFoodItem(getid,item.getText().toString(),price.getText().toString(),desc.getText().toString());

        Intent original_intent = new Intent(this,MenuActivity.class);
        startActivity(original_intent);
        return true;
    }

    public  boolean isFloat (String amount){
        boolean isValid = true;
        try {
            Float.parseFloat(amount);
        }
        catch(NumberFormatException e){
            isValid = false;
        }
        return isValid;
    }
}
