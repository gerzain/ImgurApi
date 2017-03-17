package com.dev.irving.imgurapi.servicios;

import com.dev.irving.imgurapi.modeloimgur.ImageResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by Irving on 16/03/2017.
  * Aqui se encuentra nuestra  API. Se genera usando Retrofit .
 *
 */
public interface ImgurAPI {
    String server = "https://api.imgur.com";


    /****************************************
     *
     * Subir Imagen
     */

    /**
     * @param auth        #Autorizacion la cual se obtiene al crear una aplicacion en Imgur
     * @param title       #Tiulo de la imagen
     * @param description #Descripcion de la imagen
     * @param albumId     #ID del album (Si se esta agregando a un album)
     * @param username    Nombre de usuario (Si es requerido)
     * @param file        Imagen
     * @param cb          Callback donde verificamos  success/failures
     */
    @POST("/3/image")
    void postImage(
            @Header("Authorization") String auth,
            @Query("title") String title,
            @Query("description") String description,
            @Query("album") String albumId,
            @Query("account_url") String username,
            @Body TypedFile file,
            Callback<ImageResponse> cb

    );
}
