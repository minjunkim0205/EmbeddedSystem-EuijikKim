import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

public class LCD_DHT11 {

	public static void main(String[] args) {
		try {
			DHT11 dht = new DHT11(); 
			I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
			I2CDevice dev = bus.getDevice(0x27);
			I2CLCD lcd = new I2CLCD(dev); 
			lcd.init();
			lcd.backlight(true); // Back light on 
			
			while(true) {
				float[] dht_data = dht.getData(15);
				if (dht_data[0] != -99) {
					lcd.clear();
					lcd.display_string("Humi: " + dht_data[0], 1);
					lcd.display_string("Temp: " + dht_data[1], 2);
				}
				Thread.sleep(2000);
			}			
		} catch (Exception e) {
			System.out.print(e);
		}
	}

}

