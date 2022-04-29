package top.yztz.sched.pojo;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Weather {
    String shidu;
    String wendu;
    String pm25;
    String pm10;
    String quality;

    List<Forecast> forecast;

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "shidu='" + shidu + '\'' +
                ", wendu='" + wendu + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", quality='" + quality + '\'' +
                ", forecast=" + forecast +
                '}';
    }

    static public class Forecast {
        String date;
        String high;
        String low;
        String type;
        String notice;
        String week;

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high.split(" ")[1];
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low.split(" ")[1];
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        @Override
        public String toString() {
            return "Forecast{" +
                    "date='" + date + '\'' +
                    ", high='" + high + '\'' +
                    ", low='" + low + '\'' +
                    ", type='" + type + '\'' +
                    ", notice='" + notice + '\'' +
                    '}';
        }
    }
}
