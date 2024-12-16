package ServerCoAP;

import java.util.List;

import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.rest.BasicCoapResource;
import org.ws4d.coap.core.rest.CoapData;

public class DHT11 extends BasicCoapResource {
    /* Field */
    private float temperature; // 온도 값
    private float humidity; // 습도 값

    /* Constructor */
    DHT11(String path, String value, CoapMediaType mediaType) {
        super(path, value, mediaType);
    }

    DHT11() {
        this("/dht11", "0", CoapMediaType.text_plain);
        this.temperature = 0.0f;
        this.humidity = 0.0f;
    }

    /* Method */
    private void readDHT11() {
        final boolean FOR_TEST = true;
        if(FOR_TEST){
            this.temperature = 25.5f; // 가상의 온도 값
            this.humidity = 60.0f; // 가상의 습도 값
        }else{
            final EasyDHT11 dht = new EasyDHT11();
            float[] read = dht.getData(28); // Read data from GPIO 28
            this.temperature = read[0];
            this.humidity = read[1];
        }
    }

    @Override
    public synchronized CoapData get(List<String> query, List<CoapMediaType> mediaTypesAccepted) {
        return this.get(mediaTypesAccepted);
    }

    @Override
    public synchronized CoapData get(List<CoapMediaType> mediaTypesAccepted) {
        // DHT11 센서 데이터를 읽음
        this.readDHT11();

        // 온도와 습도 정보를 문자열로 결합하여 반환
        String data = "Temperature: " + this.temperature + ", Humidity: " + this.humidity;
        return new CoapData(data.getBytes(), CoapMediaType.text_plain);
    }

    @Override
    public synchronized String getResourceType() {
        return "RaspberryPi-4 DHT11";
    }
}
