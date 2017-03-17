package com.dev.irving.imgurapi.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.irving.imgurapi.R;
import com.dev.irving.imgurapi.modeloimgur.ImageResponse;

import java.lang.ref.WeakReference;

/**
 * Created by Irving on 16/03/2017.
 */
public class NotificationHelper
{
    public final static String Tag=NotificationHelper.class.getSimpleName();

    private WeakReference<Context> mContext;

    public NotificationHelper(Context mContext)
    {
        this.mContext = new WeakReference<Context>(mContext);
    }
    public void createUploadingNotification()
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.get());
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_upload);
        mBuilder.setContentTitle(mContext.get().getString(R.string.notificacion_progreso));
        mBuilder.setColor(mContext.get().getResources().getColor(R.color.colorPrimary));
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager=(NotificationManager)mContext.get().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mContext.get().getString(R.string.app_name).hashCode(),mBuilder.build() );
    }

    public void createUploadedNotification(ImageResponse response)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.get());
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_gallery);
        mBuilder.setContentTitle(mContext.get().getString(R.string.notificacion_exitosa));
        mBuilder.setContentText(response.data.link);
        mBuilder.setColor(mContext.get().getResources().getColor(R.color.colorPrimary));

        Intent resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.data.link));
        PendingIntent intent = PendingIntent.getActivity(mContext.get(), 0, resultIntent, 0);
        mBuilder.setContentIntent(intent);
        mBuilder.setAutoCancel(true);

        Intent shareIntent = new Intent(Intent.ACTION_SEND, Uri.parse(response.data.link));
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, response.data.link);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent=PendingIntent.getActivity(mContext.get(),0,shareIntent,0);
        mBuilder.addAction(new NotificationCompat.Action(R.drawable.abc_ic_menu_share_mtrl_alpha,
                mContext.get().getString(R.string.compartir_link), pendingIntent));

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.get().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mContext.get().getString(R.string.app_name).hashCode(), mBuilder.build());
    }
    public void createFailedUploadNotification()
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.get());
        mBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        mBuilder.setContentTitle(mContext.get().getString(R.string.notificacion_error));


        mBuilder.setColor(mContext.get().getResources().getColor(R.color.colorPrimary));

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.get().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mContext.get().getString(R.string.app_name).hashCode(), mBuilder.build());
    }

}
