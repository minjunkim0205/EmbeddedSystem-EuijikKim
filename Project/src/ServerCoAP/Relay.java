package ServerCoAP;

import java.util.List;

import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.rest.BasicCoapResource;
import org.ws4d.coap.core.rest.CoapData;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Relay extends BasicCoapResource {
    /* Field */
    private GpioController gpio;
    private GpioPinDigitalOutput pin;
    private boolean state; // Boolean으로 상태 관리

    /* Constructor */
    Relay(String path, String value, CoapMediaType mediaType) {
        super(path, value, mediaType);
    }

    Relay() {
        this("/relay", "off", CoapMediaType.text_plain);
        this.gpio = GpioFactory.getInstance();
        this.pin = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, PinState.LOW); // Relay 모듈의 GPIO 핀은 27번
        this.state = false; // 초기 상태는 off(false)
    }

    /* Method */
    @Override
    public synchronized CoapData get(List<String> query, List<CoapMediaType> mediaTypesAccepted) {
        return this.get(mediaTypesAccepted);
    }

    @Override
    public synchronized CoapData get(List<CoapMediaType> mediaTypesAccepted) {
        // 텍스트 형식으로 반환 (보통 CoAP는 텍스트일 때 string으로 응답하지만, byte로 변환한 후 적절한 미디어 타입 지정)
        String data = state ? "ON" : "OFF";  // boolean 상태를 "ON" 또는 "OFF"로 문자열 변환
    
        return new CoapData(data.getBytes(), CoapMediaType.text_plain);
    }
    

    @Override
    public synchronized boolean setValue(byte[] value) {
        // byte[] 값을 boolean으로 변환
        if (value != null && value.length > 0) {
            this.state = value[0] != 0; // 0이 아니면 true
        }

        // Relay 핀 상태 변경
        if (this.state) {
            pin.high(); // Relay ON
        } else {
            pin.low(); // Relay OFF
        }

        return true;
    }

    @Override
    public synchronized boolean post(byte[] data, CoapMediaType type) {
        return this.setValue(data);
    }

    @Override
    public synchronized boolean put(byte[] data, CoapMediaType type) {
        return this.setValue(data);
    }

    @Override
    public synchronized String getResourceType() {
        return "RaspberryPi-4 Relay";
    }
}
