package br.com.senacrs.gposto.GUI.Callback;

public interface CombustivelUpdateCallback {
    void onCombustivelUpdateSuccess(Boolean update);
    void onCombustivelUpdateFailure (String message);
}
