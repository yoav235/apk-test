package com.john.reference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        UserView userView = new UserView();
        recyclerView.setAdapter(userView);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> users) {
                // Update RecyclerView
                userView.setUsers(users);
            }
        });

        // Find the buttons
        Button addButton = findViewById(R.id.btn_add_user);
        Button updateButton = findViewById(R.id.btn_update_user);
        Button deleteButton = findViewById(R.id.btn_delete_user);

        addButton.setOnClickListener(v -> showAddUserDialog());

        updateButton.setOnClickListener(v -> {
            List<UserModel> selectedUsers = userView.getSelectedUsers();
            if (selectedUsers.size() > 1) {
                showErrorDialog("You cannot update multiple users at once.");
            } else if (selectedUsers.size() == 1) {
                showUpdateUserDialog(selectedUsers.get(0));
            } else {
                Toast.makeText(MainActivity.this, "No user selected", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(v -> {
            List<UserModel> selectedUsers = userView.getSelectedUsers();
            if (!selectedUsers.isEmpty()) {
                for (UserModel user : selectedUsers) {
                    userViewModel.delete(user);
                }
                userView.clearSelections();
                Toast.makeText(MainActivity.this, "Selected users deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "No user selected", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showAddUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_user, null);
        builder.setView(dialogView);

        final EditText editTextFirstName = dialogView.findViewById(R.id.editTextFirstName);
        final EditText editTextLastName = dialogView.findViewById(R.id.editTextLastName);
        final EditText editTextEmail = dialogView.findViewById(R.id.editTextEmail);
        final EditText editTextAvatar = dialogView.findViewById(R.id.editTextAvatar);
        Button buttonSave = dialogView.findViewById(R.id.buttonSave);

        final AlertDialog dialog = builder.create();

        buttonSave.setOnClickListener(v -> {
            String firstName = editTextFirstName.getText().toString().trim();
            String lastName = editTextLastName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String avatar = editTextAvatar.getText().toString().trim();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty()) {
                UserModel newUser = new UserModel();
                newUser.setFirst_name(firstName);
                newUser.setLast_name(lastName);
                newUser.setEmail(email);
                newUser.setAvatar(avatar);
                userViewModel.insert(newUser);
                dialog.dismiss();
            } else {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void showUpdateUserDialog(UserModel selectedUser) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_user, null);
        builder.setView(dialogView);

        final EditText editTextFirstName = dialogView.findViewById(R.id.editTextFirstName);
        final EditText editTextLastName = dialogView.findViewById(R.id.editTextLastName);
        final EditText editTextEmail = dialogView.findViewById(R.id.editTextEmail);
        final EditText editTextAvatar = dialogView.findViewById(R.id.editTextAvatar);
        Button buttonSave = dialogView.findViewById(R.id.buttonSave);

        // Pre-fill the dialog with the selected user's current information
        editTextFirstName.setText(selectedUser.getFirst_name());
        editTextLastName.setText(selectedUser.getLast_name());
        editTextEmail.setText(selectedUser.getEmail());
        editTextAvatar.setText(selectedUser.getAvatar());

        final AlertDialog dialog = builder.create();

        buttonSave.setOnClickListener(v -> {
            String firstName = editTextFirstName.getText().toString().trim();
            String lastName = editTextLastName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String avatar = editTextAvatar.getText().toString().trim();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty()) {
                selectedUser.setFirst_name(firstName);
                selectedUser.setLast_name(lastName);
                selectedUser.setEmail(email);
                selectedUser.setAvatar(avatar);

                // Update the user in the database
                userViewModel.update(selectedUser);
                dialog.dismiss();
            } else {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void showDeleteUserDialog(UserModel selectedUser) {
        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete " + selectedUser.getFirst_name() + " " + selectedUser.getLast_name() + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    userViewModel.delete(selectedUser);
                    Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

}
