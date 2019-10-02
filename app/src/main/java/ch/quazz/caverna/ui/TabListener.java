package ch.quazz.caverna.ui;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;

class TabListener implements ActionBar.TabListener {

    private final int id;
    private final Fragment fragment;
    private final String tag;

    TabListener(final int id, final Fragment fragment, final String tag) {
        this.id = id;
        this.fragment = fragment;
        this.tag = tag;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(id, fragment, tag);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.remove(fragment);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
}
