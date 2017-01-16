package iut.unice.fr.geocatching.Views;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import iut.unice.fr.geocatching.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button) findViewById(R.id.ConnectButton);

        View.OnClickListener Connect = new View.OnClickListener() {
            public void onClick(View v) {
                SeebeckJob job = new SeebeckJob();
                EditText login = (EditText) findViewById (R.id.login);
                EditText pwd = (EditText) findViewById (R.id.pwd);
                job.execute(login.getText().toString(), pwd.getText().toString());

            }
        };
        b1.setOnClickListener(Connect);
    }

     class SeebeckJob extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String[] params) {
            String response = null;
            URL url = null;
            try {
                url = new URL("http://iut-outils-gl.i3s.unice.fr/jetty/authentication/");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                HashMap<String,String> Data = new HashMap<>();
                Data.put("username",params[0]);
                Data.put("password", getEncodedPassword(params[1]));

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(Data));
                writer.flush();
                writer.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int responseCode = 0;
            try {
                responseCode = conn.getResponseCode();
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
                }
                try {
                    while ((line = br.readLine()) != null) {
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
        protected void onPostExecute(String message) {
            if(message == "Fail"){
                findViewById(R.id.MessageErreurLogin).setVisibility(View.VISIBLE);

            }else {
                setContentView(R.layout.activity_maps);
            }
        }

        public String getEncodedPassword(String key) {
            byte[] uniqueKey = key.getBytes();
            byte[] hash = null;
            try {
                hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
            } catch (NoSuchAlgorithmException e) {
                throw new Error("no MD5 support in this VM");
            }
            StringBuffer hashString = new StringBuffer();
            for (int i = 0; i < hash.length; ++i) {
                String hex = Integer.toHexString(hash[i]);
                if (hex.length() == 1) {
                    hashString.append('0');
                    hashString.append(hex.charAt(hex.length() - 1));
                } else {
                    hashString.append(hex.substring(hex.length() - 2));
                }
            }
            return hashString.toString();
        }

        private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            return result.toString();
        }
    }
}
