package br.com.senac.cademeulivro.util.classes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.model.Obra;
import cz.msebera.android.httpclient.Header;

/**
 * Created by joaos on 22/05/2017.
 */

public class Scanner {

    private List<Obra> obrasEncontradas;
    private Activity activityForToast;
    private Obra obra;
    private Bitmap thumbImg;
    private String ISBN;

    public Scanner(Activity activityForToast) {

        this.activityForToast = activityForToast;
    }

    public List<Obra> pesquisar(String isbn){

        this.ISBN=isbn;
        obrasEncontradas= new ArrayList<>();
        String url="https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn;

        //Requisição HTTP Asyncrona
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Toast.makeText(activityForToast, R.string.requisicao, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int k, Header[] headers, byte[] bytes) {
                //Resgatar o retorno do http
                String res = new String(bytes);

                try {

                    JSONObject resultObject = new JSONObject(res);

                    if(resultObject.getInt("totalItems")==0) {
                        Toast.makeText(activityForToast, R.string.codigo_invalido, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray bookArray = resultObject.getJSONArray("items");
                    StringBuilder authorBuild = new StringBuilder("");

                    //preenchendo a lista e verificando a existência dos campos
                    for (int j=0; j<bookArray.length();j++) {

                        obra = new Obra();

                        JSONObject bookObject = bookArray.getJSONObject(j);

                        JSONObject volumeObject = bookObject.getJSONObject("volumeInfo");

                        //pegando capa do livro
                        if(!volumeObject.isNull("imageLinks")){

                            JSONObject imageInfo = volumeObject.getJSONObject("imageLinks");
                            new GetBookThumb().execute(imageInfo.getString("smallThumbnail")).get();
                        }

                        if(!volumeObject.isNull("authors")){
                            JSONArray authorArray = volumeObject.getJSONArray("authors");
                            if(authorArray !=null) {
                                for (int a = 0; a < authorArray.length(); a++) {
                                    if (a > 0) authorBuild.append(", ");
                                    authorBuild.append(authorArray.getString(a));
                                }
                            }
                            obra.setAutor(authorBuild.toString());
                        }

                        obra.setTitulo((!volumeObject.isNull("title")) ? volumeObject.getString("title") : null);
                        obra.setEditora((!volumeObject.isNull("publisher") ? volumeObject.getString("publisher") : null));
                        obra.setDescricao(!volumeObject.isNull("description") ? volumeObject.getString("description") : null);
                        obra.setIsbn(ISBN);

                        //capturando a data e pegando apenas o ano
                        if(!volumeObject.isNull("publishedDate")){
                            String[] parts = volumeObject.getString("publishedDate").split("-");
                            obra.setAnoPublicacao(Integer.parseInt(parts[0]));
                        }

                        obrasEncontradas.add(obra);
                    }

                } catch (JSONException e ) {
                    e.printStackTrace();
                } catch (RuntimeException r ) {
                    r.printStackTrace();
                    Toast.makeText(activityForToast, R.string.erro_requisicao, Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(activityForToast, R.string.erro_requisicao, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
            }
        });
        return obrasEncontradas;
    }



    private class GetBookThumb extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... thumbURLs) {

            try{//capturando thumbnail pela url do json

                URL thumbURL = new URL(thumbURLs[0]);
                URLConnection thumbConn = thumbURL.openConnection();
                thumbConn.connect();

                InputStream thumbIn = thumbConn.getInputStream();
                BufferedInputStream thumbBuff = new BufferedInputStream(thumbIn);
                thumbImg = BitmapFactory.decodeStream(thumbBuff);
                obra.setCapa(thumbImg);

                thumbBuff.close();
                thumbIn.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return "";
        }
        protected void onPostExecute(String result) {
        }
    }
}
