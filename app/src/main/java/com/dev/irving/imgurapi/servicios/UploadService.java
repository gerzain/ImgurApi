package com.dev.irving.imgurapi.servicios;

import android.content.Context;
import android.util.Log;

import com.dev.irving.imgurapi.helpers.NotificationHelper;
import com.dev.irving.imgurapi.modeloimgur.ImageResponse;
import com.dev.irving.imgurapi.modeloimgur.Upload;
import com.dev.irving.imgurapi.utils.Constantes;
import com.dev.irving.imgurapi.utils.NetworkUtils;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Irving on 16/03/2017.
 */
public class UploadService
{
    public final static String TAG = UploadService.class.getSimpleName();

    private WeakReference<Context> mContext;

    public UploadService(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    public void Execute(Upload upload, Callback<ImageResponse> callback)
    {
        final Callback<ImageResponse> cb = callback;

        if (!NetworkUtils.isConnected(mContext.get()))
        {

            cb.failure(null);
            return;
        }

        final NotificationHelper notificationHelper = new NotificationHelper(mContext.get());
        notificationHelper.createUploadingNotification();

        RestAdapter restAdapter = buildRestAdapter();

        restAdapter.create(ImgurAPI.class).postImage(
                Constantes.getClientAuth(),
                upload.title,
                upload.description,
                upload.albumId,
                null,
                new TypedFile("image/*", upload.image),
                new Callback<ImageResponse>()
                {
                    @Override
                    public void success(ImageResponse imageResponse, Response response)
                    {
                        if (cb != null)
                            cb.success(imageResponse, response);
                        if (response == null)
                        {

                            /*
                             Notificar que ocurrio un error al subir la imagen
                            */
                            Log.e(TAG,"Error subir imagen ");
                            notificationHelper.createFailedUploadNotification();
                            return;
                        }
                        /*
                        Notificar subida correcta de la imagen
                        */
                        if (imageResponse.success)
                        {
                            notificationHelper.createUploadedNotification(imageResponse);
                            Log.d(TAG,"SubidaCorrecta");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        if (cb != null) cb.failure(error);
                        notificationHelper.createFailedUploadNotification();
                    }
                });
    }

    private RestAdapter buildRestAdapter()
    {
        RestAdapter imgurAdapter = new RestAdapter.Builder()
                .setEndpoint(ImgurAPI.server)
                .build();

        if (Constantes.LOGGING)
            imgurAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return imgurAdapter;
    }
}
