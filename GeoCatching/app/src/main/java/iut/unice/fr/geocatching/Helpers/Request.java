package iut.unice.fr.geocatching.Helpers;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Jérémy on 13/02/2017.
 */

public class Request extends AsyncTask<String, Void, String> {

    private String method;
    private String url;
    private String reponse;
    private HashMap<String,String> data;

    public Request(String _url, String _method, HashMap<String,String> _data){
        method = _method;
        url = _url;
        data = _data;
        reponse = null;
    }

    public String getReponse(){
        return reponse;
    }

    private static String doHttpUrlConnectionGet(String desiredUrl)     throws Exception {
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try
        {
            // create the HttpURLConnection
            url = new URL(desiredUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP GET here
            connection.setRequestMethod("GET");

            // uncomment this if you want to write output to this url
            //connection.setDoOutput(true);

            // give it 15 seconds to respond
            connection.setReadTimeout(15*1000);
            connection.connect();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        }
    }

    private static String doHttpUrlConnectionPost(String desiredUrl,HashMap<String,String> data)     throws Exception {
        String response = "";
        URL url = null;
        try {
            url = new URL(desiredUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        OutputStream os = null;
        try {
            if (url != null) {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setDoInput(true);
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestMethod("POST");

                //Create JSONObject here
                JSONObject jsonParam = new JSONObject();
                if(data != null){
                    try {
                        for (Map.Entry<String, String> entry : data.entrySet()) {
                            jsonParam.put(entry.getKey(), entry.getValue());
                        }
                        os = conn.getOutputStream();
                        os.write(jsonParam.toString().getBytes("UTF-8"));
                        os.close();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally { // Proper way to ensure that the flush and the close are done in case of problem
            try {
                if (os != null) {
                    os.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int responseCode = 0;
        try {
            if (conn != null) {
                responseCode = conn.getResponseCode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                while (br != null && (line = br.readLine()) != null) {
                    response += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response = "Fail";
        }

        return response;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        if(method.equals("POST")){
            try {
                response = doHttpUrlConnectionPost(url, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                response = doHttpUrlConnectionGet(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    @Override
    protected void onPostExecute(String message) {
        reponse = message;
    }

}
