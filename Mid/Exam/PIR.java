package week5;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class PIR {

	public static void main(String[] args) {
		GpioController gpio = GpioFactory.getInstance(); // GPIO object 생성
		GpioPinDigitalOutput r_led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, PinState.LOW);
		GpioPinDigitalInput pir = gpio.provisionDigitalInputPin(RaspiPin.GPIO_25);

		boolean pir_state = false; // PIR sensor state 저장
		int led_timer = 0;

		while (true) {
			pir_state = pir.isHigh(); // PIR sensor 신호 감지 - signal in --> true

			if (pir_state == true) {
				led_timer = 50; // 0.1초에 1씩 감소 --> 즉, 5초 타이머 설정
				System.out.println("Detected --> Set LED Timer: 50");
			} else {
				System.out.println("LED Timer: " + led_timer);
			}
			if (led_timer >= 0) {
				r_led.high();
				led_timer -= 1;
			} else {
				r_led.low();
			}
			try {
				Thread.sleep(100); // Detection Delay: 0.1초
			} catch (Exception e) {
			}
		}
	}

}
