package com.example.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ListMenuPresenter;

import android.app.FragmentTransaction;
import android.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import java.util.Locale;
import java.util.prefs.PreferencesFactory;

public class OpcionesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(android.R.id.content, new PreferenciasFragment());
        ft.commit();
    }

    public static class PreferenciasFragment extends PreferenceFragment{
        @Override
        public void onCreate(final Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.opciones);

            ListPreference idioma = (ListPreference)findPreference("idioma");
            idioma.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object obj) {
                    switch (obj.toString()){
                        case "ES":
                            Locale esp = new Locale("es", "ES");
                            Locale.setDefault(esp);
                            Configuration conf_esp = new Configuration();
                            conf_esp.locale = esp;
                            getResources().updateConfiguration(conf_esp, getResources().getDisplayMetrics());
                        case "EN":
                            Locale eng = new Locale("en", "EN");
                            Locale.setDefault(eng);
                            Configuration conf_eng = new Configuration();
                            conf_eng.locale = eng;
                            getResources().updateConfiguration(conf_eng, getResources().getDisplayMetrics());
                    }
                    return false;
                }
            });
        }
    }
}