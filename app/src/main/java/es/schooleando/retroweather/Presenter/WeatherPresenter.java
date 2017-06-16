package es.schooleando.retroweather.Presenter;

import android.content.Context;

import es.schooleando.retroweather.Service.WeatherService;
import es.schooleando.retroweather.model.WeatherModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ruben on 23/01/17.
 */

public class WeatherPresenter {
    private final WeatherPresenterListener mListener;
    private final WeatherService weatherService;
    private final Context context;

    //private final String API_KEY = "2baccf769dd2ccacc2fcb22ca0a435a4";
    private final String API_KEY = "3bd52ce2bf3f673cdb99feae185b3c77";

    public interface WeatherPresenterListener{
        void weatherReady(WeatherModel weather);
    }

    public WeatherPresenter(WeatherPresenterListener listener, Context context){
        this.mListener = listener;
        this.weatherService = new WeatherService();
        this.context = context;
    }

    public void getWeather(String ciudad) {
        // Aquí llamamos al método de countryService, encolamos y implementamos el callback
        // También deberemos llamar a la Activity a traves del listener (WeatherPresenterListerer)

        // TODO: obtener weatherModel a través del weatherService
        //WeatherModel weathermodel = weatherService.getAPI()....

        // Enviar el weatherModel a la Activity
        //this.mListener.weatherReady(weatherModel);

        weatherService.getAPI().getWeather(ciudad,API_KEY).enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                WeatherModel resultado = response.body();
                if (resultado != null) {
                    mListener.weatherReady(resultado);
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                try {
                    throw new InterruptedException("Error al conectar con el servidor");
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
