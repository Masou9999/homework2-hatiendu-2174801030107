package com.example.homework2_hatiendu_2174801030107;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LinearLayout listContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listContainer = findViewById(R.id.listContainer);

        Button buttonAddItem = findViewById(R.id.buttonAddItem);
        buttonAddItem.setOnClickListener(v -> showAddItemDialog());
    }

    private void showAddItemDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);

        EditText editTextItemName = dialogView.findViewById(R.id.editTextItemName);
        EditText editTextQuantity = dialogView.findViewById(R.id.editTextQuantity);
        Button buttonAdd = dialogView.findViewById(R.id.buttonAdd);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        buttonAdd.setOnClickListener(v -> {
            String name = editTextItemName.getText().toString().trim();
            String qtyStr = editTextQuantity.getText().toString().trim();

            if (!name.isEmpty() && !qtyStr.isEmpty()) {
                try {
                    int qty = Integer.parseInt(qtyStr);
                    addItemToList(name, qty);
                    dialog.dismiss();
                } catch (NumberFormatException e) {
                    editTextQuantity.setError("Invalid number");
                }
            }
        });

        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void showEditItemDialog(View itemView, TextView textViewItem, String oldName, int oldQty) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);

        EditText editTextItemName = dialogView.findViewById(R.id.editTextItemName);
        EditText editTextQuantity = dialogView.findViewById(R.id.editTextQuantity);
        Button buttonAdd = dialogView.findViewById(R.id.buttonAdd);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

        editTextItemName.setText(oldName);
        editTextQuantity.setText(String.valueOf(oldQty));
        buttonAdd.setText("Update");

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        buttonAdd.setOnClickListener(v -> {
            String newName = editTextItemName.getText().toString().trim();
            String newQtyStr = editTextQuantity.getText().toString().trim();

            if (!newName.isEmpty() && !newQtyStr.isEmpty()) {
                try {
                    int newQty = Integer.parseInt(newQtyStr);
                    textViewItem.setText(String.format(Locale.getDefault(), "%s   Qty: %d", newName, newQty));
                    dialog.dismiss();
                } catch (NumberFormatException e) {
                    editTextQuantity.setError("Invalid number");
                }
            }
        });

        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void addItemToList(String name, int quantity) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View itemView = inflater.inflate(R.layout.item_shopping, listContainer, false);

        TextView textViewItem = itemView.findViewById(R.id.textViewItem);
        textViewItem.setText(String.format(Locale.getDefault(), "%s   Qty: %d", name, quantity));

        ImageView buttonEdit = itemView.findViewById(R.id.buttonEdit);
        ImageView buttonDelete = itemView.findViewById(R.id.buttonDelete);

        buttonDelete.setOnClickListener(v -> listContainer.removeView(itemView));
        buttonEdit.setOnClickListener(v -> showEditItemDialog(itemView, textViewItem, name, quantity));

        listContainer.addView(itemView);
    }
}
