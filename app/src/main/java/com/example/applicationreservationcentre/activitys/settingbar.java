package com.example.applicationreservationcentre.activitys;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicationreservationcentre.R;
import com.example.applicationreservationcentre.models.compoment_;

import java.util.Locale;

public class settingbar extends AppCompatActivity {
    TextView button_notifications;
    TextView button_langues;
    TextView button_compte;
    TextView button_aide;
    TextView button_logout;
    compoment_ compoment = new compoment_();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getlocal();
        setContentView(R.layout.settingbar);

        this.setTitle(R.string.txt_setting);
        ActionBar br = getSupportActionBar();
        //br.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        button_notifications = findViewById(R.id.button_notifications);
        button_langues = findViewById(R.id.button_langues);
        button_compte = findViewById(R.id.button_compte);
        button_logout = findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(settingbar.this , login.class);
                startActivity(it);
            }
        });
        context = this;
        button_compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(settingbar.this , comptes.class);
                startActivity(it);
            }
        });
        button_aide = findViewById(R.id.button_aide);
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custum_dialog_rate_app);

        button_aide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                dialog.findViewById(R.id.doneBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                        } catch (ActivityNotFoundException e) {
                        }
                    }
                });
                dialog.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        button_langues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] listems ={"Francais" , "عربية" , "anglais"};
                AlertDialog mbuilder = new AlertDialog.Builder(settingbar.this)
                        .setTitle("Choisir votre langue")
                        .setSingleChoiceItems(listems, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == 0){
                                    Toast.makeText(settingbar.this, "Francais", Toast.LENGTH_SHORT).show();
                                    setlocal("fr");
                                    recreate();
                                }
                                else if (i == 1){
                                    Toast.makeText(settingbar.this, "Arabe", Toast.LENGTH_SHORT).show();
                                    setlocal("ar");
                                    recreate();
                                }
                                else if (i == 2){
                                    Toast.makeText(settingbar.this, "anglais", Toast.LENGTH_SHORT).show();
                                    setlocal("en");
                                    recreate();
                                }
                                //dialogInterface.dismiss();
                            }
                        }).show();
            }
        });
    }
    private void setlocal(String lang) {
        Locale l = new Locale(lang);
        Locale.setDefault(l);
        Configuration config = new Configuration();
        config.locale =l;
        getBaseContext().getResources().updateConfiguration(config , getBaseContext().getResources().getDisplayMetrics() );
        SharedPreferences.Editor editor = getSharedPreferences("settings_",MODE_PRIVATE).edit();
        editor.putString("my_lang" , lang);
        editor.apply();

    }
    private void getlocal() {
        SharedPreferences preferences = getSharedPreferences("settings_",MODE_PRIVATE);
        String lang =  preferences.getString("my_lang" ,"");
        setlocal(lang);
    }

    @Override
    public void onBackPressed() {
    }
}