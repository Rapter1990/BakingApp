package company.example.android.bakingapp.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import timber.log.Timber;

/**
 * Created by user on 2.04.2018.
 */

// TODO 45 ) Creating NetworkUtils class to define getResponseFromUrl converting url to string,
    // getBitmapFromURL to convert to "thumbnail" key of url to bitmap and lastly
    // getUrlContentType to determine the content type of url
public class NetworkUtils {

    // TODO 46 ) Defining LOG attribute to determine whether the issue is thrown or not
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    // TODO 47 ) Defining bitmap for getting it from getBitmapFromURL
    private static Bitmap thumbnailBitmapImage;

    // TODO 48 ) Defining  ontentType to check the type of url
    private static String contentType;

    // TODO 49 ) Defining  getResponseFromUrl to get Url
    public static String getResponseFromUrl(URL requestUrl) throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String resultJson = null;

        try {
            if (requestUrl != null) {
                urlConnection = (HttpURLConnection) requestUrl.openConnection();
            }
            if (urlConnection != null) {
                inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Scanner scanner = new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()) {
                        resultJson = scanner.next();
                    }
                }
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return resultJson;
                } else {
                    Timber.e(LOG_TAG + " / Error in getResponseFromUrl: "
                            + urlConnection.getResponseCode());

                }
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    // TODO 50 ) Defining  getBitmapFromURL to loading Image from Url by using AsynTask
    public static Bitmap getBitmapFromURL(String src) {
        String[] params = new String[]{src};
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    Timber.i(LOG_TAG + "/ Bitmap : " + myBitmap);
                    return myBitmap;
                } catch (IOException exception) {
                    Timber.e(LOG_TAG + " / Error in getBitmapFromURL  : " + exception);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                thumbnailBitmapImage = bitmap;
            }
        }.execute(params);
        return thumbnailBitmapImage;
    }

    // TODO 51 ) Defining  getUrlContentType to loading content type of url
    public static String getUrlContentType(String url) {
        String[] params = new String[]{url};
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    String UrlcontentType = connection.getHeaderField("Content-Type");
                    Timber.i(LOG_TAG + "/ Content-Type : " + UrlcontentType);
                    return UrlcontentType;
                } catch (IOException exception) {
                    Timber.e(LOG_TAG + " / Error in getUrlContentType  : " + exception);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String UrlcontentType) {
                super.onPostExecute(UrlcontentType);
                contentType = UrlcontentType;
            }
        }.execute(params);
        return contentType;
    }


    // TODO 85 ) Checking whether Internet is avaiable or not
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
