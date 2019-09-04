package br.com.senacrs.gposto.GUI.Callback;

import br.com.senacrs.gposto.LibClass.Postos;

public interface PostosCallback {
    void onPostosSuccess(Postos postos);
    void onPostosFailure (String message);
}
