package br.com.senacrs.gposto.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import br.com.senacrs.gposto.Controller.UsuarioLoginController;
import br.com.senacrs.gposto.GUI.Callback.UsuarioCallback;
import br.com.senacrs.gposto.LibClass.Usuario;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.Utils;

public class LoginActivity extends AppCompatActivity implements UsuarioCallback {
    public static final String SHARED_PREFERENCES = "loginref";
    TextInputEditText editEmail,editSenha;
    Button btnLogin;
    CheckBox chkBoxLogin;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //facebook login
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        editor=sharedPreferences.edit();

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnLogin = findViewById(R.id.btnLogin);
        chkBoxLogin = findViewById(R.id.chkBoxLogin);

        //facebook login
        loginButton = findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startActivity(new Intent(LoginActivity.this, DestaquesActivity.class));

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken ==null){
                Utils.longToast(LoginActivity.this, "Deslogado do Facebook");
            }else{
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken newAccessToken){
        GraphRequest graphRequest = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

    }

    public void login(View view) {
        if(!areEmptyFields()){
            userLogin();
        }
    }

    private void userLogin() {
        UsuarioLoginController controller = new UsuarioLoginController();
        try {
            controller.getUsuarioWeb(editEmail.getText().toString(), editSenha.getText().toString(), this);
        } catch (Exception e) {
            android.widget.Toast.makeText(this, e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
        }
    }

    private boolean areEmptyFields() {
        boolean isEmpty = true;

        if (editEmail.getText().toString().trim().isEmpty()) {
            editEmail.setError("CAMPO VAZIO!");
        } else {
            if (editSenha.getText().toString().trim().isEmpty()) {
                editSenha.setError("CAMPO VAZIO!");
            } else {
                isEmpty = false;
            }

        }
        return isEmpty;
    }

    @Override
    public void onUsuarioSuccess(Usuario usuario) {
        if(editEmail.getText().toString().equals(usuario.getEmail())){
            if(chkBoxLogin.isChecked()){
                editor.putBoolean("savelogin",true);
                editor.putString("user",usuario.getEmail());
                editor.putString("senha",usuario.getSenha());
                editor.putInt("id", usuario.getId());
                editor.commit();
            }
            Utils.longToast(this, "LOGIN BEM SUCEDIDO!");
            startActivity(new Intent(this, DestaquesActivity.class));
        }else{
            Utils.longToast(this, "USUÁRIO E/OU SENHA INVÁLIDOS");
        }

    }

    @Override
    public void onUsuarioFailure(String message) {
        Utils.longToast(this, "LOGIN FALHOU!");
    }

    private void isLogged(SharedPreferences preferences, Usuario usuario) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", usuario.getEmail());
        editor.putString("senha", usuario.getSenha());
        editor.putInt("id", usuario.getId());
        editor.commit();
    }
}
