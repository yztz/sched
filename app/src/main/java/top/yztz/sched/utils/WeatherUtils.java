package top.yztz.sched.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.yztz.sched.R;
import top.yztz.sched.pojo.Weather;

public class WeatherUtils {
    public static int CITY_CODE_HANGZHOU = 101210101;
    private static Map<String, Integer> weatherMap = new HashMap<>();
    private static Weather weather;

    static {
        weatherMap.put("晴", R.drawable.sunny);
        weatherMap.put("阴", R.drawable.overcast);
        weatherMap.put("雨", R.drawable.rainy);
        weatherMap.put("小雨", R.drawable.rainy);
        weatherMap.put("大雨", R.drawable.rainy);
        weatherMap.put("雪", R.drawable.snowy);
        weatherMap.put("多云", R.drawable.cloudy);
    }

    public static int getWeatherResID(String weather) {
        if (!TextUtils.isEmpty(weather) && weatherMap.containsKey(weather)) {
            return weatherMap.get(weather);
        }
        return R.drawable.n_a;
    }

    public static Weather getWeather() {
        return weather;
    }

    public static void updateWeather() {
        updateWeather(null, true);
    }

    public static void updateWeather(WeatherCallback callback) {
        updateWeather(callback, false);
    }

    private static void updateWeather(WeatherCallback callback, boolean update) {
        if (null == callback) {
            if (update) updateWeather(CITY_CODE_HANGZHOU, null);
        } else {
            if (update) updateWeather(CITY_CODE_HANGZHOU, callback);
            else {
                if (null != weather) {
                    callback.callback(weather);
                } else {
                    updateWeather(CITY_CODE_HANGZHOU, callback);
                }
            }
        }
    }

    public static void updateWeather(int cityCode, WeatherCallback callback) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://t.weather.itboy.net/api/weather/city/" + cityCode)
                        .build();
                Response response = client.newCall(request).execute();
                WeatherUtils.weather = JSON.parseObject(response.body().string()).getObject("data", Weather.class);
                Log.d("Weather", weather.toString());
                if (null != callback) callback.callback(weather);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public interface WeatherCallback {
        void callback(Weather weather);
    }

}
