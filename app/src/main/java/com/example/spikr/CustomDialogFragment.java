package com.example.spikr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    public interface CustomDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    private String mTitle;
    private String mMessage;
    private CustomDialogListener mListener;

    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);
    }

    public static CustomDialogFragment newInstance(String mTitle, String mMessage, CustomDialogListener mListener) {
        CustomDialogFragment dialogFragment = new CustomDialogFragment();
        dialogFragment.mTitle = mTitle;
        dialogFragment.mMessage = mMessage;
        dialogFragment.mListener = mListener;
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mMessage).setTitle(mTitle);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mListener != null) {
                    mListener.onDialogPositiveClick(CustomDialogFragment.this);
                    Toast.makeText(getContext(),"Redirecting...",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();
    }

    public Dialog showAlertDialog(int layout, Activity context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        //create a new layout xml for individual dialog
        //=LayoutInflater layoutInflater = LayoutInflater.from(context.getApplicationContext());
        //final View customLayout = layoutInflater.inflate(layout, null);
        //alertDialog.setView(customLayout);
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                //EditText editText = customLayout.findViewById(R.id.edit_studentName);
                Toast.makeText(context,"Redirecting...",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(ScannerActivity.this, ScannerResult.class);
//                intent.putExtra("laptop_info", li); //return the object li as a serialized object
//                startActivity(intent);
            }
        });

        //AlertDialog alert = alertDialog.create();
        //alert.show();
        return alertDialog.create();
    }
}
