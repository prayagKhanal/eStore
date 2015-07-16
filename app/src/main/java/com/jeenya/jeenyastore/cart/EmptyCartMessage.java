package com.jeenya.jeenyastore.cart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by hp on 6/26/2015.
 */
public class EmptyCartMessage extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("JEENYA STORE");
        builder.setMessage("Your Cart is Empty. Please add item to cart to proceed.");
        builder.setCancelable(false);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        Dialog dialog = builder.create();

        return dialog;

    }

}