package br.com.senacrs.gposto.GUI.Callback;

import java.util.List;

import br.com.senacrs.gposto.LibClass.Bandeira;

public interface BandeiraCallback {
    void onBandeiraSuccess(List<Bandeira> bandeira);
    void onBandeiraFailure (String message);
}
