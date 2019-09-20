package br.com.senacrs.gposto.GUI.Callback;

import android.view.MenuItem;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.senacrs.gposto.LibClass.TopPostos;

public interface TopPostosCallback {
    void onTopPostosSuccess(List<TopPostos> list);
    void onTopPostosFailure (String message);

    boolean onNavigationItemSelected(@NonNull MenuItem menuItem);
}
