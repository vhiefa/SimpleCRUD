package com.vhiefa.simplecrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    ListView list;

    JSONParser jParser = new JSONParser();
    ArrayList<Mahasiswa> daftar_mhs = new ArrayList<Mahasiswa>();
    JSONArray daftarMhs = null;
    String url_read_mhs = "http://api.vhiefa.net76.net/simple_crud/read_mhs.php";
    // JSON Node names, ini harus sesuai yang di API
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MHS = "mahasiswa";
    public static final String TAG_ID_MHS = "id_mhs";
    public static final String TAG_NAMA_MHS = "nama";
    public static final String TAG_NIM_MHS = "nim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        list = (ListView) findViewById(R.id.listview_mhs);

        //jalankan ReadMhsTask
        ReadMhsTask m= (ReadMhsTask) new ReadMhsTask().execute();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int urutan, long id) {
                //dapatkan data id, nama, nim mahasiswa dan simpan dalam variable string
                String idmhs = ((TextView) view.findViewById(R.id.id_mhs)).getText().toString();
                String nama = ((TextView) view.findViewById(R.id.nama_mhs)).getText().toString();
                String nim = ((TextView) view.findViewById(R.id.nim_mhs)).getText().toString();

                //varible string tadi kita simpan dalam suatu Bundle, dan kita parse bundle tersebut bersama intent menuju kelas UpdateDeleteActivity
                Intent i = null;
                i = new Intent(MainActivity.this, UpdateDeleteActivity.class);
                Bundle b = new Bundle();
                b.putString("id_mhs", idmhs);
                b.putString("nama_mhs", nama);
                b.putString("nim_mhs", nim);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_add) {
            Intent i = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class ReadMhsTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            String returnResult = getMhsList(); //memanggil method getMhsList()
            return returnResult;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if(result.equalsIgnoreCase("Exception Caught"))
            {
                Toast.makeText(MainActivity.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
            }

            if(result.equalsIgnoreCase("no results"))
            {
                Toast.makeText(MainActivity.this, "Data empty", Toast.LENGTH_LONG).show();
            }
            else
            {
                list.setAdapter(new MahasiswaAdapter(MainActivity.this,daftar_mhs)); //Adapter menampilkan data mahasiswa ke dalam listView
            }
        }


        //method untuk memperoleh daftar mahasiswa dari JSON
        public String getMhsList()
        {
            Mahasiswa tempMhs = new Mahasiswa();
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            try {
                JSONObject json = jParser.makeHttpRequest(url_read_mhs,"POST", parameter);

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) { //Ada record Data (SUCCESS = 1)
                    //Getting Array of daftar_mhs
                    daftarMhs = json.getJSONArray(TAG_MHS);
                    // looping through All daftar_mhs
                    for (int i = 0; i < daftarMhs.length() ; i++){
                        JSONObject c = daftarMhs.getJSONObject(i);
                        tempMhs = new Mahasiswa();
                        tempMhs.setMhsId(c.getString(TAG_ID_MHS));
                        tempMhs.setMhsName(c.getString(TAG_NAMA_MHS));
                        tempMhs.setMhsNIM(c.getString(TAG_NIM_MHS));
                        daftar_mhs.add(tempMhs);
                    }
                    return "OK";
                }
                else {
                    //Tidak Ada Record Data (SUCCESS = 0)
                    return "no results";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }
        }

    }
}


