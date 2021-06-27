package com.example.myapplication;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private Button wBoton;
    private ActivityMainBinding binding;
    private Intent intent;
    private PendingIntent pendingIntent;

    private NotificationCompat.Builder notificacion;
    private NotificationManagerCompat nm;
    private NotificationCompat.WearableExtender wearableExtender;

    String idChannel = "Mi_Canal";
    int idNotificacion = 001;

    private NotificationCompat.BigTextStyle bigTextStyle;

    String longText = "linea1                " + "   linea 2 " +"";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wBoton = (Button) findViewById(R.id.wBoton);

        intent = new Intent(MainActivity.this, MainActivity.class);

        nm = NotificationManagerCompat.from(MainActivity.this);

        wearableExtender = new NotificationCompat.WearableExtender();

        bigTextStyle = new NotificationCompat.BigTextStyle().bigText(longText);

        wBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int importance = NotificationManager.IMPORTANCE_HIGH;
                String name = "Notificación";

                NotificationChannel notificationChannel =
                        new NotificationChannel(idChannel, name, importance);

                nm.createNotificationChannel(notificationChannel);

                Intent snoozeIntent = new Intent();
                snoozeIntent.setAction(Intent.ACTION_ANSWER);
                snoozeIntent.putExtra(Intent.EXTRA_ALARM_COUNT, 0);

                pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

                notificacion = new NotificationCompat.Builder(MainActivity.this, idChannel)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle("Notificacion wear")
                                        .setContentText(longText)
                                        .setStyle(new NotificationCompat.InboxStyle()
                                                .addLine("linea1")
                                                .addLine("linea 2")
                                                .setBigContentTitle("InboxStyle")
                                                .setSummaryText("Summary text"))
                                        .setContentIntent(pendingIntent)
                                        .addAction(R.drawable.common_google_signin_btn_icon_dark, getString(R.string.Leyenda),
                                                pendingIntent)
                                        .extend(wearableExtender)
                                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                                        ;

                nm.notify(idNotificacion, notificacion.build());
            }
        });
    }
}