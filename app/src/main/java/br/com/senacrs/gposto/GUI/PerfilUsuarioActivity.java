package br.com.senacrs.gposto.GUI;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.Utils;

public class PerfilUsuarioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView txtEmail,txtUser,txtSenha;
    ImageView editEmail,editUser,editSenha,imageViewPerfil;

    Uri mCropImageUri;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        navigationDrawer();
        findViewById();
    }

    private void findViewById(){
        imageViewPerfil = findViewById(R.id.imagePerfil);
        txtEmail = findViewById(R.id.txtEmail);
        txtUser = findViewById(R.id.txtUsuario);
        txtSenha = findViewById(R.id.txtSenha);
        editEmail = findViewById(R.id.image_edit_Email);
        editUser = findViewById(R.id.image_edit_Usuario);
        editSenha = findViewById(R.id.image_edit_Senha);
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

                //Set the Image on User Perfil
                imageViewPerfil.setImageBitmap(thePic);

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

    public void editSenha(View view) {
        setAlertDialog("Sua Senha");
    }

    public void editUser(View view) {
        setAlertDialog("Seu Usuario");
    }

    public void editEmail(View view) {
        setAlertDialog("Seu Email");
    }

    private void setAlertDialog(String op){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Deseja Modificar "+op);

        builder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });

        builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.shortToast(PerfilUsuarioActivity.this,"Funciono");
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void setEditEmail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

    }




    //NavigationDrawer (Menu)
    private void navigationDrawer() {
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);

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
            case R.id.menu_cadastrar_posto: {
                Intent intent = new Intent(this,CadastroPostosActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_editar_perfil: {
                Utils.shortToast(this, "?????___??????");
                break;
            }
            case R.id.menu_sair: {
                Utils.shortToast(this, "funciono3");
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
}
