package ca.ass3cmpt276.assignment3cmpt276;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class winning_screen_fragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the view
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.winning_screen_layout, null);

        // Create a Button Listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_NEGATIVE:
                        getActivity().finish();
                        break;

                    case DialogInterface.BUTTON_POSITIVE:
                        Intent i = getActivity().getIntent();
                        getActivity().finish();
                        startActivity(i);
                        break;
                }

                getActivity().finish();
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("Congratulations! Well Done!")
                .setView(v)
                .setPositiveButton("Yes", listener)
                .setNegativeButton("No", listener)
                .create();
    }
}
