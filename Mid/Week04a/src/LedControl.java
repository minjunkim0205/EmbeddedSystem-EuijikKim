import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class LedControl{
    public static void main(String[] args){
        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput r_led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, PinState.LOW);
        GpioPinDigitalOutput g_led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09, PinState.LOW);
        GpioPinDigitalOutput b_led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, PinState.LOW);
        GpioPinDigitalInput btn = gpio.provisionDigitalInputPin(RaspiPin.GPIO_29);
    
        int state = 0; // 0:Off, 1:Red, 2:Green, 3:Blue

        while(true){
            boolean btn_pressed = btn.isHigh();
            if(btn_pressed){
                state = (state+1)%4;
                System.out.println("Btn pressed!");
                // =====================
                if(state == 0){
                    r_led.low();
                    g_led.low();
                    b_led.low();
                }else if(state == 1){
                    r_led.high();
                    g_led.low();
                    b_led.low();
                }else if(state == 2){
                    r_led.low();
                    g_led.high();
                    b_led.low();
                }else if(state == 3){
                    r_led.low();
                    g_led.low();
                    b_led.high();
                }   
                // =====================
                try{
                    Thread.sleep(300);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}