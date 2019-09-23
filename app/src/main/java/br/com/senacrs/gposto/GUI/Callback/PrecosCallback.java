package br.com.senacrs.gposto.GUI.Callback;

import java.util.List;

import br.com.senacrs.gposto.LibClass.Combustivel;

public interface PrecosCallback {
    void onPrecosSuccess(List<Combustivel> list);
    void onPrecosFailure (String message);
}
