package com.example.bigcityweatherapp

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class WeatherDetail :AppCompatActivity(){
    lateinit var view_city: TextView
    lateinit var view_temp: TextView
    lateinit var view_desc: TextView
    lateinit var humid: TextView
    lateinit var rise: TextView
    lateinit var set: TextView
    lateinit var press: TextView
    lateinit var winds: TextView
    lateinit var temp_min: TextView
    lateinit var temp_max: TextView
    lateinit var updated: TextView
    lateinit var view_weather: ImageView
    lateinit var visibility: TextView

    var context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_detail)

        val tvDataReceived: TextView = findViewById(R.id.pop)
        val image: ImageView = findViewById(R.id.img_item_photo)

        val getPop        = intent.getStringExtra("POP")
        val getPhoto        = intent.getIntExtra("PHOTO",0)
        val getName        = intent.getStringExtra("NAME").toString()

        if (supportActionBar != null) {
            supportActionBar!!.setTitle("Weather Detail")
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        Glide.with(this)
            .load(getPhoto)
            .apply(RequestOptions().override(1000, 500))
            .into(image)
        tvDataReceived.text    = getPop
        humid = findViewById<TextView>(R.id.humidity)
        press = findViewById<TextView>(R.id.pressure)
        winds = findViewById<TextView>(R.id.wind)
        temp_min = findViewById<TextView>(R.id.temp_min)
        temp_max = findViewById<TextView>(R.id.temp_max)
        view_city = findViewById<TextView>(R.id.town)
        view_city = findViewById<TextView>(R.id.town)
        view_city = findViewById<TextView>(R.id.town)
        view_temp = findViewById<TextView>(R.id.temp)
        view_desc = findViewById<TextView>(R.id.desc)
        visibility = findViewById<TextView>(R.id.visibility)
        updated = findViewById<TextView>(R.id.updated_at)
        rise = findViewById<TextView>(R.id.sunrise)
        set = findViewById<TextView>(R.id.sunset)
        view_weather = findViewById(R.id.wheather_image)

        api_key(getName)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        onBackPressed()
        return true
    }
    private fun api_key(City: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?q=$City&appid=a6f41d947e0542a26580bcd5c3fb90ef&units=metric")
            .get()
            .build()
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body!!.string()
                    try {
                        val json = JSONObject(responseData)
                        val array = json.getJSONArray("weather")
                        val `object` = array.getJSONObject(0)
                        val description = `object`.getString("description")
                        val icons = `object`.getString("icon")
                        val temp1 = json.getJSONObject("main")
                        val Temperature = temp1.getDouble("temp")
                        val sys = json.getJSONObject("sys")
                        val temps =Math.round(Temperature).toString() + " °C"
                        val tempMin = "Min Temp: " +temp1.getString("temp_min")+"°C"
                        val tempMax = "Max Temp: " +temp1.getString("temp_max")+"°C"

                        val updatedAt:Long = json.getLong("dt")
                        val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                        val main = json.getJSONObject("main")
                        val wind = json.getJSONObject("wind")
                        val pressure = main.getString("pressure")+" mbar"
                        val humidity = main.getString("humidity")+"%"
                        val sunrise:Long = sys.getLong("sunrise")
                        val sunset:Long = sys.getLong("sunset")
                        val country: String = sys.getString("country")
                        val area: String = json.getString("name")
                        val ID = area+", "+country
                        val windSpeed = wind.getString("speed")+" mph"
                        val vision = json.getLong("visibility")
                        val vision_string =(vision/1000).toString() + " Km"

                        setText(updated, updatedAtText)
                        setText(rise, SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000)))
                        setText(set, SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000)))
                        setText(winds, windSpeed)
                        setText(press, pressure)
                        setText(humid, humidity)
                        setText(visibility, vision_string)
                        setText(temp_min, tempMin)
                        setText(temp_max, tempMax)
                        setText(view_city, ID)
                        setText(view_temp, temps)
                        setText(view_desc, description)
                        setImage(view_weather, icons)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setText(text: TextView, value: String) {
        runOnUiThread { text.text = value }
    }
    private fun setImage(
        imageView: ImageView,
        value: String
    ) {
        runOnUiThread { //paste switch
            when (value) {
                "01d" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d01d))
                "01n" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d01d))
                "02d" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d02d))
                "02n" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d02d))
                "03d" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d03d))
                "03n" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d03d))
                "04d" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d04d))
                "04n" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d04d))
                "09d" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d09d))
                "09n" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d09d))
                "10d" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d10d))
                "10n" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d10d))
                "11d" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d11d))
                "11n" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d11d))
                "13d" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d13d))
                "13n" -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.d13d))
                else -> imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.wheather))
            }
        }
    }


}




