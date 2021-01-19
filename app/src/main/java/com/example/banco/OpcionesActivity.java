package com.example.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ListMenuPresenter;

import android.app.FragmentTransaction;
import android.app.FragmentManager;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.CheckBox;
import android.widget.Toast;

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
                            Configuration conf_esp = getResources().getConfiguration();
                            conf_esp.setLocale(esp);
                            getResources().updateConfiguration(conf_esp, getResources().getDisplayMetrics());
                            break;
                        case "EN":
                            Locale eng = new Locale("en", "EN");
                            Locale.setDefault(eng);
                            Configuration conf_eng = getResources().getConfiguration();
                            conf_eng.setLocale(eng);
                            getResources().updateConfiguration(conf_eng, getResources().getDisplayMetrics());
                            break;
                    }
                    Toast.makeText(getActivity(), R.string.app_cambiarIdioma, Toast.LENGTH_LONG).show();
                    return true;
                }
            });

            ListPreference datos = (ListPreference)findPreference("datos");
            datos.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object obj) {
                    switch (obj.toString()){
                        case "BD":
                            Toast.makeText(getActivity(), R.string.app_datosBD, Toast.LENGTH_LONG).show();
                            break;
                        case "SW":
                            Toast.makeText(getActivity(), R.string.app_datosSW, Toast.LENGTH_LONG).show();
                            break;
                    }

                    return false;
                }
            });

            /* Música

            MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.musica);
            mp.setLooping(true);
            mp.setVolume(1.0f, 1.0f);

            final CheckBoxPreference musica = (CheckBoxPreference) findPreference("reproducirMusica");
            musica.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (musica.isChecked())
                        mp.start();
                    else
                        mp.stop();

                    return true;
                }
            });
             */
        }
    }
}