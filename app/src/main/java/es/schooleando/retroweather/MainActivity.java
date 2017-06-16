package es.schooleando.retroweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import es.schooleando.retroweather.Presenter.WeatherPresenter;
import es.schooleando.retroweather.model.WeatherModel;

public class MainActivity extends AppCompatActivity implements WeatherPresenter.WeatherPresenterListener{
    private ListView listCiudades;
    private TextView estado;
    private ImageView icono;
    private TextView temperatura;
    private TextView viento;
    private TextView humedad;
    private TextView txt1,txt2,txt3,txt4,ciudadText;

    //declaro e inicializo un array de ciudades

    private ArrayList<String> ciudades=new ArrayList<String>(){};
    private ArrayAdapter<String>adapter;
    private String ciudadSeleccionada;

   private  WeatherPresenter weatherPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listCiudades= (ListView) findViewById(R.id.listaCiudades);
        estado= (TextView) findViewById(R.id.tvEstado);
        temperatura= (TextView) findViewById(R.id.tvTemperatura);
        viento= (TextView) findViewById(R.id.tvViento);
        humedad= (TextView) findViewById(R.id.tvhumedad);
        icono = (ImageView)findViewById(R.id.ivIcono);

        ciudadText = (TextView) findViewById(R.id.cityTxt);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);

        //llenamos la lista
        ciudadesLlenar();
        //ponemos la primera ciudad de la lista como seleccionada
        ciudadSeleccionada=ciudades.get(0);

        //lenamos el listView con un adaptador
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,ciudades);
        listCiudades.setAdapter(adapter);

        listCiudades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ciudadSeleccionada=ciudades.get(position);
                weatherPresenter.getWeather(ciudadSeleccionada);
                ciudadText.setVisibility(View.VISIBLE);
                txt1.setVisibility(View.VISIBLE);
                txt2.setVisibility(View.VISIBLE);
                txt3.setVisibility(View.VISIBLE);
                txt4.setVisibility(View.VISIBLE);
            }
        });

        weatherPresenter = new WeatherPresenter(this,this);
    }

    @Override
    public void weatherReady(WeatherModel weather) {
      // aquí obtenemos las actualizaciones gracias a WeatherPresenter y actualizamos el interfaz
        ciudadText.setText(weather.getName());
        estado.setText(weather.getWeather().get(0).getDescription().toUpperCase() + ".");
        Picasso.with(this).load("http://openweathermap.org/img/w/" + weather.getWeather().get(0).getIcon() + ".png").into(icono);
        temperatura.setText(Math.round((Double.parseDouble(weather.getMain().getTemp())-273.15d)* 100.0) / 100.0 + " ºC.");     //weather.getMain().getTemp() --> Kelvin
        viento.setText(Math.round((Double.parseDouble(weather.getWind().getSpeed())*3.6d)* 100.0) / 100.0 + " km/h.");      //weather.getWind().getSpeed() --> m/s
        humedad.setText(weather.getMain().getHumidity() + " %.");
    }

    public  void actualizar(View v){
        weatherPresenter.getWeather(ciudadSeleccionada);
        ciudadText.setVisibility(View.VISIBLE);
        txt1.setVisibility(View.VISIBLE);
        txt2.setVisibility(View.VISIBLE);
        txt3.setVisibility(View.VISIBLE);
        txt4.setVisibility(View.VISIBLE);
    }
    private void ciudadesLlenar(){
        //Lista

        ciudades.add("Addis Ababa,et");
        ciudades.add("Albal,es");
        ciudades.add("Alicante,es");
        ciudades.add("Amsterdam,nl");
        ciudades.add("Barcelona,es");
        ciudades.add("Bilbao,es");
    }
}
