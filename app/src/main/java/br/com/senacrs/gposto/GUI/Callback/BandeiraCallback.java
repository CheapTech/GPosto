package br.com.senacrs.gposto.GUI.Callback;

import br.com.senacrs.gposto.LibClass.Bandeira;

public interface BandeiraCallback {
    void onBandeiraSuccess(Bandeira bandeira);
    void onBandeiraFailure (String message);
}
