package com.vhiefa.simplecrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Afifatul on 6/18/2015.
 */
public class UpdateDeleteActivity extends ActionBarActivity {

    JSONParser jParser = new JSONParser();
    String url_update_mhs= "http://api.vhiefa.net76.net/simple_crud/update_mhs.php";
    String url_delete_mhs= "http://api.vhiefa.net76.net/simple_crud/delete_mhs.php";
    // JSON Node names, ini harus sesuai yang di API
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_ID_MHS = "id_mhs";
    public static final String TAG_NAMA_MHS = "nama";
    public static final String TAG_NIM_MHS = "nim";

    EditText EditTxtnama, EditTxtnim;
    TextView TxtViewId;
    Button UpdateBtn, DeleteBtn;
    String namaStr, nimStr, idStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatedelete);

        EditTxtnama = (EditText) findViewById(R.id.editTextNama);
        EditTxtnim = (EditText) findViewById(R.id.editTextNim);
        TxtViewId = (TextView) findViewById(R.id.id_mhs);

        UpdateBtn = (Button) findViewById(R.id.buttonUpdate);
        DeleteBtn = (Button) findViewById(R.id.buttonDelete);

        //menangkap bundle yang telah di-parsed dari MainActivity
        Bundle b = getIntent().getExtras();
        String isi_id_mhs = b.getString("id_mhs");
        String isi_nama_mhs= b.getString("nama_mhs");
        String isi_nim_mhs= b.getString("nim_mhs");
        //meng-set bundle tersebut
        EditTxtnama.setText(isi_nama_mhs);
        EditTxtnim.setText(isi_nim_mhs);
        TxtViewId.setText(isi_id_mhs);

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idStr = TxtViewId.getText().toString();
                namaStr = EditTxtnama.getText().toString();
                nimStr = EditTxtnim.getText().toString();
                new UpdateMhsTask().execute();
            }
        });

        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idStr = TxtViewId.getText().toString();
                new DeleteMhsTask().execute();
            }
        });
    }

    class UpdateMhsTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateDeleteActivity.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {

            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair(TAG_ID_MHS, idStr));
            parameter.add(new BasicNameValuePair(TAG_NAMA_MHS, namaStr));
            parameter.add(new BasicNameValuePair(TAG_NIM_MHS, nimStr));

            try {
                JSONObject json = jParser.makeHttpRequest(url_update_mhs,"POST", parameter);

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    return "OK";
                }
                else {

                    return "FAIL";

                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            pDialog.dismiss();

            if(result.equalsIgnoreCase("Exception Caught"))
            {

                Toast.makeText(UpdateDeleteActivity.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
            }

            if(result.equalsIgnoreCase("FAIL"))
            {
                Toast.makeText(UpdateDeleteActivity.this, "Fail.. Try Again", Toast.LENGTH_LONG).show();
            }

            else {
                Intent i = null;
                i = new Intent(UpdateDeleteActivity.this, MainActivity.class);
                startActivity(i);
            }

        }
    }


    class DeleteMhsTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateDeleteActivity.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {

            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair(TAG_ID_MHS, idStr));

            try {
                JSONObject json = jParser.makeHttpRequest(url_delete_mhs,"POST", parameter);

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    return "OK";
                }
                else {

                    return "FAIL";

                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            pDialog.dismiss();

            if(result.equalsIgnoreCase("Exception Caught"))
            {

                Toast.makeText(UpdateDeleteActivity.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
            }

            if(result.equalsIgnoreCase("FAIL"))
            {
                Toast.makeText(UpdateDeleteActivity.this, "Fail.. Try Again", Toast.LENGTH_LONG).show();
            }

            else {
                Intent i = null;
                i = new Intent(UpdateDeleteActivity.this, MainActivity.class);
                startActivity(i);
            }

        }
    }
}

