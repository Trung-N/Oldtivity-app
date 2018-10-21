package com.example.oldivity;

import android.provider.Contacts;
import android.widget.Button;
/**
 * This is a controller for a simple view that allows
 * the user to input two post codes and generate an
 * appropriate freight cost to deliver goods from
 * one to the other. The user should enter data in to
 * both text fields, then press the calculate button.
 * The result should be displayed in a text label.
 */
public class Controller {

    // Button that is pressed to do the calculation.
    Button calculateButton =
            (Button) UI.getElementById("CALCULATE_BUTTON");

    // Source and destination post code for frieght.
    TextField sourecPostCodeField =
            (TextField) UI.getElementById("SOURCE_POST_CODE");
    TextField destinationPostCodeField =
            (TextField) UI.getElementById("DESTINATION_POST_CODE");

    // Label that displays the cost of the freight to the user.
    TextLabel costLabel = (TextLabel)
            UI.getElementById("COST_LABEL");

    // List of all valid post codes, used during validation.
    List<String> validPostCodes = new ArrayList<String>();
    AddressTools addressTools = new AddressTools();

    public Controller() {
        // Fill in the list of valid post codes.
 ...
 ...
 ...
    }

    public void calculateButtonPressed {

        if (!this.validPostCodes.contains(
                this.sourcePostCodeField.text())) {
            new Alert("Source post code isn't valid.").show();
            return;
        }

        if (!this.validPostCodes.contains(
                this.destinationPostCodeField.text())) {
            new Alert("Destination post code isn't valid.").show();
            return;
        }

        Location sloc = this.addressTools.locationFromAddress(
                Address.fromPostCode(this.sourcePostCodeField.text()));

        Location dloc = this.addressTools.locationFromAddress(

                Address.fromPostCode(this.destinationPostCodeField.text()));

        Float distance = sloc.distanceTo(dloc);

        if (distance > 1000.0)
        {
            this.costLabel.setText("$10");
        }
        else if (distance > 100.0)
        {
            this.costLabel.setText("$5");
        }
        else
        {
            this.costLabel.setText("$2");
        }
    }
}