package br.com.senacrs.gposto.GUI.Callback;

public interface AvaliacaoCallback {
    void onAvaliacaoSucces(String avaliacao);
    void onAvaliacaoFailure(String message);
}
