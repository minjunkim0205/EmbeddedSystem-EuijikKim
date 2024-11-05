import java.io.IOException;
import com.pi4j.io.spi.*;

public class MCP3204 {
	public static SpiDevice spi = null;
	public MCP3204() {
		try {
			//SPI 객체 선언 
			spi = SpiFactory.getInstance(SpiChannel.CS0, SpiDevice.DEFAULT_SPI_SPEED, SpiDevice.DEFAULT_SPI_MODE);
		}catch (Exception e) {
			System.out.println("Fail to create a SPI instance");
		}		
	}
	
	public static String byteToBinaryString(byte n) {
		// Byte를 binary ���� String���� ��ȯ
	    StringBuilder sb = new StringBuilder("00000000");
	    for (int bit = 0; bit < 8; bit++) {
	        if (((n >> bit) & 1) > 0) {
	            sb.setCharAt(7 - bit, '1');
	        }
	    }
	    return sb.toString();
	}
	
	public int readMCP3204(int adcChannel) throws IOException {
		
		byte[] sending_data = {0,0,0};
		byte[] receiving_data;
		
		// ������ ������ (5 bits) ���� (Raspberry Pi), D2�� 1�� ����		
		sending_data[0] = (byte)(sending_data[0] | 0b11100000); // Start Bit, SGL/DIFF, D2
		
		if (adcChannel >= 2) { // D1 ����
			sending_data[0] = (byte)(sending_data[0] | 0b00010000);
		} 
			
		if (adcChannel % 2 == 1) { // D0 ����
			sending_data[0] = (byte)(sending_data[0] | 0b00001000);
		}
		
		// SPI ��ü�� ����� ������ ����
		receiving_data = spi.write(sending_data); // Full-duplex ��� - sending/receiving ���ÿ� ����

		// 3 ���� �������� �����͸� ������ String���� ���� 
		String binaryString = byteToBinaryString(receiving_data[0])
								+ byteToBinaryString(receiving_data[1])
								+ byteToBinaryString(receiving_data[2]);
		
		// Example of Binary String: 000000011111010000000001 (1 2800 1)
		// ������ data�� ���� 7 bits�� �����ϰ� �������� ��ȯ
		int value = Integer.parseInt(binaryString.substring(7,19), 2); // �� 24-bit���� �ʿ��� 12-bit�� ����
						
		return value;
	}

	public static void main(String[] args) {
		MCP3204 obj = new MCP3204();
		while(true) {
			try {
				int value = obj.readMCP3204(0); // CH0 
				System.out.println(value); // �������� �� ���
				Thread.sleep(1000);
			}catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
