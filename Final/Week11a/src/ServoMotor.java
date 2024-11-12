import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.RaspiPin;

public class ServoMotor {
	public static void main(String[] args) {
		GpioController gpio = GpioFactory.getInstance();
		GpioPinPwmOutput pwm = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_15);
		MCP3204 adc_obj = new MCP3204();
		pwm.setPwmRange(200); // 100us = 1 --> 100us * 200 = 20ms (10 --> 1ms)
		
		try {
			pwm.setPwm(24); // Right (2.4ms) 
			Thread.sleep(1000);
			pwm.setPwm(15); // Middle (1.48ms) 
			Thread.sleep(1000);
			pwm.setPwm(6); // Left (0.55ms)
			Thread.sleep(1000);

			while(true) {
				int value = adc_obj.readMCP3204(0); // Channel 0 ���, 0~4096
				float ranged_value = ((float)value/4096 * 20) + 4; // 0 ~ 1 -> 0 ~ 20 --> 4 ~ 24
				System.out.println(ranged_value);
				pwm.setPwm((int)ranged_value);
				Thread.sleep(100);
			}			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
