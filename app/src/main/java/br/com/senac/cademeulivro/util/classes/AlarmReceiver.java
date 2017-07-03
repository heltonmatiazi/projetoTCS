package br.com.senac.cademeulivro.util.classes;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.SystemClock;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.dao.ContainerDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Container;
import br.com.senac.cademeulivro.util.constante.Constantes;

public class AlarmReceiver extends BroadcastReceiver {

    private ContainerDAO containerDAO;
    private SQLiteDatabase mDatabase;
    private Container container;
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    public static int idNotificacao;
    private AlarmManager alarm;

    @Override
    public void onReceive(Context context, Intent intent) {
        int acao = intent.getExtras().getInt("action");

        if (intent.getExtras().getInt("id") != 0) {
            idNotificacao = intent.getExtras().getInt("id");
        }

        NotificationManager notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        mDatabase = new DatabaseHelper(context.getApplicationContext()).getWritableDatabase();
        containerDAO = new ContainerDAO(mDatabase);
        container = containerDAO.getById(idNotificacao);

        //verifica se o usuário quer receber notificação
        String PREF_NAME = "MyPrefsFile";
        SharedPreferences settingsShared = context.getSharedPreferences(PREF_NAME, 0);

        //se estiver como false, ele cancela todas as notificações
        if(settingsShared.getBoolean("receberNotificacao", false)==false){
            PendingIntent pendingIntent=PendingIntent.getBroadcast(context,
                    Constantes.BROADCAST_NOTIFICAR,
                    intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            alarm.cancel(pendingIntent);
        }

        //verifica se tem obra dentro do container
        if(container.getTotalObras()>0){

            Notification notif = intent.getParcelableExtra(NOTIFICATION);
            notifManager.notify(idNotificacao, notif);

            scheduleNotification(getNotification(context, container.getNomeContainer()), intent,
                    container.getIdContainer(), context);

        }else{

            PendingIntent pendingIntent=PendingIntent.getBroadcast(context,
                    Constantes.BROADCAST_NOTIFICAR,
                    intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            alarm.cancel(pendingIntent);
        }
    }

    //criando nova notificação
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Notification getNotification(Context context, String nomeContainer){

        Notification.Builder notificacao = new Notification.Builder(context);
        notificacao.setContentTitle(nomeContainer);
        notificacao.setContentText(context.getString(R.string.notificacao_manutencao));
        notificacao.setSmallIcon(R.mipmap.ic_logo);
        notificacao.setVibrate(new long[]{1000});

        notificacao.setAutoCancel(true);
        return notificacao.build();
    }

    private void scheduleNotification(Notification notification, Intent notificationIntent, int idContainer, Context context){

        notificationIntent.putExtra("id", idContainer);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification);
        notificationIntent.putExtra("action", Constantes.BROADCAST_NOTIFICAR);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                Constantes.BROADCAST_NOTIFICAR,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        long futureMillis = SystemClock.elapsedRealtime()+2592000000L;

        alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.ELAPSED_REALTIME, futureMillis, pendingIntent);
    }
}
