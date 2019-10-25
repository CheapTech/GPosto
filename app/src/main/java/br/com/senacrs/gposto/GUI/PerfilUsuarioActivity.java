package br.com.senacrs.gposto.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import br.com.senacrs.gposto.GUI.Callback.UsuarioCallback;
import br.com.senacrs.gposto.LibClass.Usuario;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.Utils;

public class PerfilUsuarioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UsuarioCallback{

    public static final String USER_REF = "user_ref";

    TextView txtEmail, txtUsuario,nav_user,nav_email;
    ImageView imageEditPerfil,imageViewPerfil,nav_photo_user;

    Uri mCropImageUri;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        Usuario usuario = getSavedUserReference();

        navigationDrawer(usuario);

        imageViewPerfil = findViewById(R.id.imagePerfil);
        imageEditPerfil = findViewById(R.id.imageEditPerfil);
        txtEmail = findViewById(R.id.txtEmail);
        txtUsuario = findViewById(R.id.txtUsuario);

        if (usuario != null){
            txtUsuario.setText(usuario.getUser());
            txtEmail.setText(usuario.getEmail());
        }
    }

    private Usuario getSavedUserReference(){
        Usuario usuario;

        SharedPreferences editorGetSavedUser = getSharedPreferences(USER_REF, MODE_PRIVATE);

        String user = editorGetSavedUser.getString("user", null);
        if(user != null){
            usuario = new Usuario();
            usuario.setId(editorGetSavedUser.getInt("id", 0));
            usuario.setUser(editorGetSavedUser.getString("user", ""));
            usuario.setSenha(editorGetSavedUser.getString("senha", ""));
            usuario.setEmail(editorGetSavedUser.getString("email", ""));

            return usuario;
        }else{
            return null;
        }
    }

    //Get Image Perfil(Usuario)
    public void getImagePerfil(View view) {
        startCropImageActivity();
    }

    private void startCropImageActivity(){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();

                Bitmap thePic = null;

                try {
                    thePic = MediaStore.Images.Media.getBitmap(this.getContentResolver(),resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Glide.with(this).load(thePic).into(imageViewPerfil);


            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE){
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startCropImageActivity(mCropImageUri);
            }else {
                Utils.longToast(this,"Cancelling, required permissions are not granted");
            }
        }
    }

    //CustomAlertDialog && Update User, Email, Password
    public void editarPerfil(View view) {
        setAlertDialog(view);
    }

    private void setAlertDialog(final View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilUsuarioActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.alertdialog_edit_perfil,null);

        Usuario getUser = getSavedUserReference();

        final EditText editEmail = mView.findViewById(R.id.editEmail);
        final EditText editUsuario = mView.findViewById(R.id.editUsuario);
        final EditText editSenha = mView.findViewById(R.id.editSenha);
        final EditText editConfirmarSenha = mView.findViewById(R.id.editConfirmarSenha);
        final Button btnUpdateUser = mView.findViewById(R.id.btnUpdateUser);
        final Button btnCloseAlertDialog = mView.findViewById(R.id.btnCloseAlertDialog);

        if (getUser != null){
            editEmail.setText(getUser.getEmail());
            editUsuario.setText(getUser.getUser());
        }

        builder.setMessage("Deseja Modificar seu Perfil?");

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editEmail.getText().toString().trim().isEmpty()){
                    editEmail.setError("Campo Obrigatorio");
                    editEmail.requestFocus();
                } else {
                    if (editUsuario.getText().toString().trim().isEmpty()){
                        editUsuario.setError("Campo Obrigatorio");
                        editUsuario.requestFocus();
                    } else {
                        if (editSenha.getText().toString().trim().isEmpty()){
                            editSenha.requestFocus();
                            Utils.shortToast( PerfilUsuarioActivity.this, "Error: Campo: SENHA Obrigatorio");
                            //editSenha.setError("Campo Obrigatorio");
                        } else {
                            if (editConfirmarSenha.getText().toString().trim().isEmpty()){
                                editConfirmarSenha.requestFocus();
                                Utils.shortToast( PerfilUsuarioActivity.this, "Error: Campo: CONFIRMAR SENHA Obrigatorio");
                                //editConfirmarSenha.setError("Campo Obrigatorio");
                            } else {
                                if (editSenha.getText().toString().trim().equals(editConfirmarSenha.getText().toString().trim())){
                                    Usuario usuario = new Usuario();
                                    usuario.setUser(editUsuario.getText().toString());
                                    usuario.setSenha(editSenha.getText().toString());
                                    usuario.setEmail(editEmail.getText().toString());

                                    // UsuarioController usuarioController = new UsuarioController();
                                    try {
                                        //usuarioController.updateUserWeb(body,PerfilUsuarioActivity.this);
                                    } catch (Exception e) {
                                        //Utils.longToast(PerfilUsuarioActivity.this, e.getMessage());
                                    }
                                } else {
                                    editSenha.getText().clear();
                                    editConfirmarSenha.getText().clear();
                                    editSenha.requestFocus();
                                    Utils.shortToast( PerfilUsuarioActivity.this, "Senhas Diferentes");
                                }
                            }
                        }
                    }
                }
            }
        });

        btnCloseAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        builder.setView(mView);
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onUsuarioSuccess(Usuario usuario) { Utils.longToast(PerfilUsuarioActivity.this,"Usuario Alterado Com Sucesso"); }

    @Override
    public void onUsuarioFailure(String message) { Utils.longToast(PerfilUsuarioActivity.this,message); }

    //NavigationDrawer (Menu)
    private void navigationDrawer(Usuario usuario) {
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);

        View navView = navigationView.getHeaderView(0);

        nav_photo_user = navView.findViewById(R.id.nav_header_photo);
        nav_user= navView.findViewById(R.id.nav_header_user);
        nav_email = navView.findViewById(R.id.nav_header_email);

        if (usuario != null){
            nav_user.setText(usuario.getUser());
            nav_email.setText(usuario.getEmail());
        }else {
            String userNull = "Modo Visitante";
            nav_user.setText(userNull);
            nav_email.setVisibility(View.GONE);
            nav_photo_user.setVisibility(View.INVISIBLE);
        }
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setTitle("Perfil Usuario");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_destaques:{
                Intent intent = new Intent(this,DestaquesActivity.class);
                startActivity(intent);
                finish();
                break;
            }

            case R.id.menu_cadastrar_posto: {
                Intent intent = new Intent(this,CadastroPostosActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.menu_editar_perfil: {
                Utils.shortToast(this, "?????___??????");
                break;
            }
            case R.id.menu_sair: {
                clearUserReference();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void clearUserReference(){
        SharedPreferences.Editor editorSaveUser = getSharedPreferences(USER_REF, MODE_PRIVATE).edit();
        editorSaveUser.clear();
        editorSaveUser.commit();
    }
}
