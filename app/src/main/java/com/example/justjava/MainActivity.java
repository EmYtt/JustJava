package com.example.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_for) + " " + name);
        intent.putExtra (Intent.EXTRA_TEXT, createOrderSummary(name, price, hasWhippedCream, hasChocolate));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     * @param hasWhippedCream is whether ór not the user wants whipped cream topping
     * @param hasChocolate is whether or not the user wants chocolate topping
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int coffeePrice = 5;
        int whippedCreamPrice = 1;
        int chocolatePrice = 2;

        int price = coffeePrice;

        if (hasWhippedCream)
        {
            price = price + whippedCreamPrice;
        }

        if (hasChocolate)
        {
            price = price + chocolatePrice;
        }

        return price * quantity;
    }

    /**
     * Creates summary of the order.
     *
     * @param name of the customer
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\n" + getString(R.string.add_whipped_cream) + "? " + addWhippedCream;
        priceMessage += "\n" + getString(R.string.add_chocolate) + "? " + addChocolate;
        priceMessage += "\n" + getString(R.string.quantity) + ": " + quantity;
        priceMessage += "\n" + getString(R.string.total_price, price);
        priceMessage += "\n" + getString(R.string.thank_you) + "!";
        return priceMessage;
    }

    public void increment(View view) {
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.max_order);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

        if (quantity == 100)
        {
            toast.show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.min_order);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

        /*Shorter version:
        Toast.makeText(this, "You cannot order less than 1 coffee", Toast.LENGTH_SHORT).show();*/

        if (quantity < 2)
        {
            toast.show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}