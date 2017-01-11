package pt.isec.a21240321.pplfinder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by drmor on 30/12/2016.
 */

public class ErrorDialog extends DialogFragment {
    private int title;
    private int message;
    private int icon;
    private boolean isToClose;

    public ErrorDialog(int title, int message, int icon){
        this.title = title;
        this.message = message;
        this.icon = icon;
        this.isToClose = false;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder .setTitle(this.title)
                .setMessage(this.message)
                .setIcon(this.icon)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isToClose = true;
                    }
                });

        return builder.create();
    }

    public boolean isToClose(){return this.isToClose;}
}
