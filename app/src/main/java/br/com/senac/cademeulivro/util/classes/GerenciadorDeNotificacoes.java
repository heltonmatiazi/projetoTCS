package br.com.senac.cademeulivro.util.classes;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.util.constante.Constantes;


/**
 * Created by joaos on 26/06/2017.
 */

public class GerenciadorDeNotificacoes {

    private Context context;
    private Intent notificationIntent;
    private String nomeContainer;

    public GerenciadorDeNotificacoes(Context context, Intent notificationIntent, String nomeContainer){
        this.context=context;
        this.notificationIntent=notificationIntent;
        this.nomeContainer=nomeContainer;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Notification getNotification(String conteudo){

        Notification.Builder notificacao = new Notification.Builder(context);
        notificacao.setContentTitle(nomeContainer);
        notificacao.setContentText(conteudo);
        notificacao.setSmallIcon(R.mipmap.ic_logo);
        notificacao.setVibrate(new long[]{1000});

        notificacao.setAutoCancel(true);
        return notificacao.build();
    }

    private void scheduleNotification(int idContainer, Notification notification, long delay){

        notificationIntent.putExtra("id", idContainer);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification);
        notificationIntent.putExtra("action", Constantes.BROADCAST_NOTIFICAR);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                Constantes.BROADCAST_NOTIFICAR,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        long futureMillis = SystemClock.elapsedRealtime()+delay;
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.ELAPSED_REALTIME, futureMillis, pendingIntent);
    }

    public void criarNotification(int idContainer){

        String texto = context.getString(R.string.notificacao_manutencao);

        //2592000000 milisegundos - 30dias
        notificationIntent = new Intent(context, AlarmReceiver.class);
        scheduleNotification(idContainer, getNotification(texto), 2592000000L);
    }

    //id do notification é igual ao o id do container
    public static void cancelNotification(int idNotification, Context context){

        //por fazer
    }

}
