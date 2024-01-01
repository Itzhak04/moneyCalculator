package software.ulpgc.moneycalculator.fixerws;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import software.ulpgc.moneycalculator.Currency;
import software.ulpgc.moneycalculator.ExchangeRate;
import software.ulpgc.moneycalculator.ExchangeRateLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

public class FixerExchangeRateLoader implements ExchangeRateLoader {
    @Override
    public ExchangeRate load(Currency from, Currency to) throws IOException {
        return new ExchangeRate(from, to, LocalDate.now(), dameRate(from,to));
    }

    private double dameRate(Currency from, Currency to) throws IOException {
        URL url = new URL("https://api.apilayer.com/exchangerates_data/latest?symbols="+ to.code()+"&base="+
                from.code()+"&apikey=" + FixerAPI.key);
        try(InputStream is = url.openStream()) {
            String json = new String(is.readAllBytes());
            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
            double exchangeRate = jsonObject.getAsJsonObject("rates").get(to.code()).getAsDouble();
            return exchangeRate;
            } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
