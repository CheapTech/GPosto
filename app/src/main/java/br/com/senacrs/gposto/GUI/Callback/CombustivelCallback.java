package br.com.senacrs.gposto.GUI.Callback;

import java.util.List;

import br.com.senacrs.gposto.LibClass.Combustivel;

public interface CombustivelCallback {
    void onCombustivelSuccess(List<Combustivel> list);
    void onCombustivelFailure (String message);
}
