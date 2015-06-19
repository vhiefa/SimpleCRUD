package com.vhiefa.simplecrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Afifatul on 6/18/2015.
 */
public class CreateActivity extends ActionBarActivity{

    JSONParser jsonParser = new JSONParser();
    String url_create_mhs= "http://api.vhiefa.net76.net/simple_crud/create_mhs.php";
    // JSON Node names, ini harus sesuai yang di API
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_NAMA_MHS = "nama";
    public static final String TAG_NIM_MHS = "nim";

    EditText EditTxtnama, EditTxtnim;
    Button addBtn;
    String namaStr, nimStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        EditTxtnama = (EditText) findViewById(R.id.editTextNama);
        EditTxtnim = (EditText) findViewById(R.id.editTextNim);
        addBtn = (Button) findViewById(R.id.buttonAdd);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaStr = EditTxtnama.getText().toString();
                nimStr = EditTxtnim.getText().toString();
                new CreateMhsTask().execute();
            }
        });
    }


    class CreateMhsTask extends AsyncTask<String, String, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CreateActivity.this);
            dialog.setMessage("Tambah Mahasiswa...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... args) {

                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair(TAG_NAMA_MHS, namaStr));
                params.add(new BasicNameValuePair(TAG_NIM_MHS, nimStr));

                // getting JSON Object
                // Note that create Post url accepts POST method
                JSONObject json = jsonParser.makeHttpRequest(url_create_mhs, "POST", params);

                // check for success tag
                try {
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // closing this screen
                        finish();
                    } else {
                        return "gagal_database";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "gagal_koneksi_or_exception";
                }

            return "sukses";

        }


        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            if (result.equalsIgnoreCase("gagal_database")){
                dialog.dismiss();
                Toast.makeText(CreateActivity.this, "Terjadi masalah! Silahkan cek koneksi Anda!", Toast.LENGTH_SHORT).show();
            }
            else if (result.equalsIgnoreCase("gagal_koneksi_or_exception")){
                dialog.dismiss();
                Toast.makeText(CreateActivity.this, "Terjadi masalah! Silahkan cek koneksi Anda!",  Toast.LENGTH_SHORT).show();

            }
            else if (result.equalsIgnoreCase("sukses")){
                dialog.dismiss();
                Intent i = null;
                i = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(i);
            }
        }
    }
}
