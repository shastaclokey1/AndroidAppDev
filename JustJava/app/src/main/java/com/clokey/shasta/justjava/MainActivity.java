/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.clokey.shasta.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

//import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity
{
    int numCoffees = 0;
    int coffeeUnitPrice = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     * gets check states from choc and whip checkboxes
     * sends order information to email application
     * Can display the order summary to the screen
     */
    public void submitOrder(View view)
    {
        CheckBox whippedCreamCheckBox = findViewById(R.id.add_whip_checkbox);
        CheckBox chocolateSauceCheckBox = findViewById(R.id.add_chocolate_checkbox);
        CheckBox chocoSprinklesCheckBox = findViewById(R.id.add_sprinkles_checkbox);
        CheckBox cocoPowderCheckBox = findViewById(R.id.coco_powder_checkbox);
        CheckBox cinnamonCheckBox = findViewById(R.id.cinnamon_checkbox);
        CheckBox creamerCheckBox = findViewById(R.id.creamer_checkbox);
        CheckBox nonDairyCreamerCheckBox = findViewById(R.id.non_dairy_creamer_checkbox);
        CheckBox coconutOilCheckBox = findViewById(R.id.coconut_oil_checkbox);
        CheckBox decafCheckBox = findViewById(R.id.decaf_checkbox);
        EditText nameField = findViewById(R.id.name_field_edit_text);
        boolean hasWhip = whippedCreamCheckBox.isChecked();
        boolean hasChoco = chocolateSauceCheckBox.isChecked();
        boolean hasCSprink = chocoSprinklesCheckBox.isChecked();
        boolean hasCocoPow = cocoPowderCheckBox.isChecked();
        boolean hasCinn = cinnamonCheckBox.isChecked();
        boolean hasCream = creamerCheckBox.isChecked();
        boolean hasNonDairyC = nonDairyCreamerCheckBox.isChecked();
        boolean hasCoconut = coconutOilCheckBox.isChecked();
        boolean isDecaf = decafCheckBox.isChecked();
        String message = createOrderSummary(calculatePrice(numCoffees,coffeeUnitPrice,hasWhip,hasChoco), hasWhip, hasChoco, hasCSprink, hasCocoPow, hasCinn, hasCream, hasNonDairyC, hasCoconut, isDecaf, nameField.getText().toString());

        //For sending message to email application
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(intent.EXTRA_SUBJECT, "Coffee order for " + nameField.getText().toString());
        intent.putExtra(intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);

        displayOrderSummary(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayOrderSummary(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    /**
     * This method adds a coffee to the order.
     */
    public void addCoffeeToOrder(View view)
    {
        numCoffees++;
        if (numCoffees > 100) //logic to ensure you don't overdose on coffee
        {
            numCoffees = 100;
            Toast.makeText(this, "You cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
        }
        display(numCoffees);
    }

    /**
     * This method adds a coffee to the order.
     */
    public void subtractCoffeeFromOrder(View view)
    {
        numCoffees--;
        if (numCoffees < 0) //logic to ensure you can't order a negative number of coffees
        {
            numCoffees = 0;
            Toast.makeText(this, "You cannot order a negative number of coffees", Toast.LENGTH_SHORT).show();
        }
        display(numCoffees);
    }

    /**
     * This method creates a summary of all the important order information
     * @param price is the total cost of coffees before toppings
     * @param hasWhip is the true/false value identifying whether to add whipped cream
     * @param hasChoc is the true/false value identifying whether to add chocolate sauce
     * @param name is the name that the user enters in the edit text field
     * @return passes a summary of the user's order back to the calling method
     */
    private String createOrderSummary(int price, boolean hasWhip, boolean hasChoc, boolean hasSprink, boolean hasCocoP, boolean hasCinna, boolean hasCream, boolean hasNonDairyC, boolean hasCoconut, boolean isDecaf, String name)
    {
        String temp = "Name: " + name;
        temp += "\nAdd Whipped Cream? " + hasWhip;
        temp += "\nAdd Chocolate Sauce? " + hasChoc;
        temp += "\nAdd Chocolate Sprinkles? " + hasSprink;
        temp += "\nAdd Coco Powder? " + hasCocoP;
        temp += "\nAdd Cinnamon? " + hasCinna;
        temp += "\nAdd Creamer? " + hasCream;
        temp += "\nAdd Non Dairy Creamer? " + hasNonDairyC;
        temp += "\nAdd Coconut Oil? " + hasCoconut;
        temp += "\nDecaf? " + isDecaf;
        temp += "\nQuantity: " + numCoffees;
        temp += "\nTotal: $" + price;
        temp += "\nThank you!";
        return temp;
    }

    private int calculatePrice(int numCoffees, int coffeeUnitPrice, boolean hasWhip, boolean hasChoco)
    {
        int price = coffeeUnitPrice;
        if (hasWhip)
            price += 1;
        if (hasChoco)
            price += 2;
        price *= numCoffees;
        return price;
    }

}