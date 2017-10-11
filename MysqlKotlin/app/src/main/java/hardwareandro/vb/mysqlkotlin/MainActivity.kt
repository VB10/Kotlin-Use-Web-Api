package hardwareandro.vb.mysqlkotlin

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }
    fun getData(view:View){

        var etName=_etName.text.toString()
        var etPas=_etPass.text.toString()
        val url ="http://10.0.2.2:5000/api/User?name=$etName&password=$etPas"
        CustomAsync().execute(url)


    }
    inner class CustomAsync: AsyncTask<String, String, String>() {


        //inner classlar üst sınıflarından değerleri almak için kullandığımz yapı



        override fun doInBackground(vararg params: String?): String {


            try {

                //execute ettiğimiz paremeteriyi url olarak veriyoruz
                val url=URL(params[0])

                //connectionları açıyoruz
                val urlConnect = url.openConnection() as HttpURLConnection

                //zaman aşımı durumuna karşı süreyi belirleme işlemi
                urlConnect.connectTimeout=7000

                //olayın döndüğü kısım aslında burada bizim urlmizi istediğimiz formata çeviriyor
                var inString = ConvertStreamToString(urlConnect.inputStream)


                //publish progress diyerek onProgresUpdate ye değerimizi yolluyoruz
                publishProgress(inString)

            }catch (ex:Exception){}
            return  " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                //gelen değerden json olduğundan bir object olarka yaklaşıyoruz
                var json = JSONObject(values[0])

                //bize sadece tek değer döndüğü için result içerisindeki değeri boolen olarak yaklıyoruz
                var res = json.getBoolean("result")
                //son metodumuza sonucu atıyoruz
                onPostExecute(res.toString())



            }catch (ex:Exception){}
        }

        override fun onPostExecute(result: String?) {
            //gelen sonucu ekrana basıyoruz
            Toast.makeText(this@MainActivity,result,Toast.LENGTH_SHORT).show()


        }

        private fun  ConvertStreamToString(inputStream: InputStream): String {

            //bizim verdiğimiz url üzerinden okumamız için bir diziye dönüştürüyor string
            val _bufferReader = BufferedReader(InputStreamReader(inputStream))


            //satır satır okumak
            var line:String
            //bu satırları biryerde toplamak için işlemlerimiz
            var AllString:String=""

            try {

                do {
                    //line gelen değerleri alıp eğer boş veya null değil ise topluya  yazdıyor
                    line=_bufferReader.readLine()
                    if (line!=null) {
                        AllString += line
                    }


                }while (line!=null)

                //döngü satır null olduğunda bitiyor ve akışı kapatıyoruz
                inputStream.close()
            }catch (ex:Exception){}

            //fonksiyon son olarak değerleri döndürüp ömrünü tamamlıyor
            return AllString

        }


    }
}