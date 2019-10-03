package br.com.senacrs.gposto.Utilities;

import android.widget.Filter;

public interface OnItemViewHolderClick {
    void onItemClicked(int itemPosition);

    Filter getFilterBairro();
}
