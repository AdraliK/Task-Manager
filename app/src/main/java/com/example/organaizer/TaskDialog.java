package com.example.organaizer;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class TaskDialog {

    private Dialog dialog;
    private Button btnAccept;
    private ImageView btnClose;
    private EditText editTextTime;
    private EditText editTextTask;
    private OrganaizeActivity activity;

    private AddOrEditItem addOrEditItem;

    public TaskDialog(OrganaizeActivity activity, AddOrEditItem addOrEditItem, String time, String task){
        this.activity = activity;
        this.addOrEditItem = addOrEditItem;
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.addtask_window);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.dialog_bg));
        dialog.setCancelable(false);

        btnAccept = dialog.findViewById(R.id.buttonAccept);
        btnClose = dialog.findViewById(R.id.imageClose);

        editTextTask = dialog.findViewById(R.id.editTextTask);
        editTextTime = dialog.findViewById(R.id.editTextTime);
        if (!(time == null && task == null)) {
            editTextTime.setText(time);
            editTextTask.setText(task);
        }

        setOpenListener();
        setCloseListener();

        dialog.show();
    }

    private void setOpenListener(){
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = new Item(editTextTime.getText().toString(), editTextTask.getText().toString());
                addOrEditItem.addOrEdit(item);
                activity.getAdapter().notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    private void setCloseListener(){
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public interface AddOrEditItem {
        void addOrEdit(Item item);
    }

}
