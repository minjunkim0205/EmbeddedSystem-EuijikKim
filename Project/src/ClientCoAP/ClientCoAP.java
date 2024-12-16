package ClientCoAP;

import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

import org.ws4d.coap.core.CoapClient;
import org.ws4d.coap.core.CoapConstants;
import org.ws4d.coap.core.connection.BasicCoapChannelManager;
import org.ws4d.coap.core.connection.api.CoapChannelManager;
import org.ws4d.coap.core.connection.api.CoapClientChannel;
import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.enumerations.CoapRequestCode;
import org.ws4d.coap.core.messages.api.CoapRequest;
import org.ws4d.coap.core.messages.api.CoapResponse;
import org.ws4d.coap.core.rest.CoapData;

public class ClientCoAP extends JFrame implements CoapClient {
    /* Field */
    // 기본 버튼들
    JButton btn_get = new JButton("GET");
    JButton btn_post = new JButton("POST");
    JButton btn_put = new JButton("PUT");
    JButton btn_delete = new JButton("DELETE");
    // 릴레이 버튼들
    JButton btn_relay_on = new JButton("Relay On");
    JButton btn_relay_off = new JButton("Relay Off");
    JButton btn_relay_info = new JButton("Relay Info");
    // DHT11 버튼
    JButton btn_dht11_info = new JButton("DHT11 Info");
    // 나머지 라벨 등
    JLabel path_label = new JLabel("Path");
    JTextArea path_text = new JTextArea("/.well-known/core", 1, 1); // 기본 경로
    JLabel payload_label = new JLabel("Payload");
    JTextArea payload_text = new JTextArea("", 1, 1);
    JTextArea display_text = new JTextArea();
    JScrollPane display_text_jp = new JScrollPane(display_text);
    JLabel display_label = new JLabel("Display");

    CoapClientChannel clientChannel = null;

    /* Constructor */
    public ClientCoAP(String serverAddress, int serverPort) {
        super("스마트 환기 장치 - CoAP 클라이언트");
        this.setLayout(null);
        String sAddress = serverAddress;
        int sPort = serverPort;

        CoapChannelManager channelManager = BasicCoapChannelManager.getInstance();

        try {
            clientChannel = channelManager.connect(this, InetAddress.getByName(sAddress), sPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        if (null == clientChannel) {
            return;
        }

        // 기존 버튼 배치 및 이벤트 처리
        btn_get.setBounds(20, 670, 100, 50);
        btn_put.setBounds(130, 670, 100, 50);
        btn_post.setBounds(240, 670, 100, 50);
        btn_delete.setBounds(350, 670, 100, 50);

        btn_get.addActionListener(e -> sendRequest(CoapRequestCode.GET));
        btn_put.addActionListener(e -> sendRequest(CoapRequestCode.PUT));
        btn_post.addActionListener(e -> sendRequest(CoapRequestCode.POST));
        btn_delete.addActionListener(e -> sendRequest(CoapRequestCode.DELETE));

        // 릴레이 관련 버튼 배치 (사이즈를 줄임)
        btn_relay_on.setBounds(650, 500, 100, 40);
        btn_relay_off.setBounds(650, 560, 100, 40);
        btn_relay_info.setBounds(650, 620, 100, 40);

        btn_relay_on.addActionListener(e -> {
            path_text.setText("/relay"); // Path 설정
            payload_text.setText("1"); // Payload 설정
            sendRelayRequest("1"); // Relay On 요청
        });
        btn_relay_off.addActionListener(e -> {
            path_text.setText("/relay"); // Path 설정
            payload_text.setText("0"); // Payload 설정
            sendRelayRequest("0"); // Relay Off 요청
        });
        btn_relay_info.addActionListener(e -> {
            path_text.setText("/relay"); // Path 설정
            payload_text.setText(""); // Payload 비우기
            getRelayState(); // Relay 상태 정보 요청
        });

        // DHT11 버튼 배치
        btn_dht11_info.setBounds(500, 500, 100, 40); // Relay 버튼 왼쪽에 배치
        btn_dht11_info.addActionListener(e -> {
            path_text.setText("/dht11");
            payload_text.setText("");
            getDHT11Data();
        });

        // 레이아웃 설정
        payload_label.setBounds(20, 570, 350, 30);
        payload_text.setBounds(20, 600, 440, 30);
        payload_text.setFont(new Font("arial", Font.BOLD, 15));

        path_label.setBounds(20, 500, 350, 30);
        path_text.setBounds(20, 530, 440, 30);
        path_text.setFont(new Font("arial", Font.BOLD, 15));

        display_label.setBounds(20, 10, 100, 20);
        display_text.setLineWrap(true);
        display_text.setFont(new Font("arial", Font.BOLD, 15));
        display_text_jp.setBounds(20, 40, 740, 430);

        this.add(btn_get);
        this.add(btn_post);
        this.add(btn_put);
        this.add(btn_delete);
        this.add(path_text);
        this.add(path_label);
        this.add(payload_label);
        this.add(payload_text);
        this.add(display_text_jp);
        this.add(display_label);
        this.add(btn_relay_on);
        this.add(btn_relay_off);
        this.add(btn_relay_info);
        this.add(btn_dht11_info);

        this.setSize(800, 800);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /* Method */
    private void sendRequest(CoapRequestCode requestCode) {
        String path = path_text.getText();
        String payload = payload_text.getText();

        CoapRequest request = clientChannel.createRequest(requestCode, path, true);

        // PUT과 POST 요청일 경우, Payload 처리
        if (requestCode == CoapRequestCode.PUT || requestCode == CoapRequestCode.POST) {
            byte[] payloadBytes = new byte[] { (byte) (payload.equals("1") ? 1 : 0) };
            request.setPayload(new CoapData(payloadBytes, CoapMediaType.text_plain));
        }

        displayRequest(request);
        clientChannel.sendMessage(request);
    }

    // Relay 요청을 전송하는 메소드
    private void sendRelayRequest(String state) {
        String path = "/relay"; // 경로를 고정
        CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, path, true);
        byte[] payloadBytes = new byte[] { (byte) (state.equals("1") ? 1 : 0) };
        request.setPayload(new CoapData(payloadBytes, CoapMediaType.text_plain));
        displayRequest(request);
        clientChannel.sendMessage(request);
    }

    // Relay 상태를 GET 요청으로 확인하는 메소드
    private void getRelayState() {
        String path = "/relay"; // 경로를 고정
        CoapRequest request = clientChannel.createRequest(CoapRequestCode.GET, path, true);
        displayRequest(request);
        clientChannel.sendMessage(request);
    }

    // DHT11 데이터 요청을 전송하는 메소드
    private void getDHT11Data() {
        String path = "/dht11";  // DHT11 경로로 고정
        CoapRequest request = clientChannel.createRequest(CoapRequestCode.GET, path, true);
        displayRequest(request);  // 요청을 화면에 표시
        clientChannel.sendMessage(request);  // 요청 전송
    }
    

    private void displayRequest(CoapRequest request) {
        display_text.append("Request: " + request.toString());
        display_text.append(System.lineSeparator());
    }

    @Override
    public void onConnectionFailed(CoapClientChannel channel, boolean notReachable, boolean resetByServer) {
        display_text.append("Connection Failed");
        System.exit(-1);
    }

    @Override
    public void onResponse(CoapClientChannel channel, CoapResponse response) {
        byte[] payload = response.getPayload();
        if (payload != null && payload.length > 0) {
            String responseStr = new String(payload); // 바이트 배열을 문자열로 변환
            display_text.append("Response: " + responseStr); // 화면에 응답 데이터 표시
        } else {
            display_text.append("Response: No payload");
        }
        display_text.append(System.lineSeparator());
    }

    @Override
    public void onMCResponse(CoapClientChannel channel, CoapResponse response, InetAddress srcAddress, int srcPort) {
        // Multicast response 처리 없음
    }

    /* Main */
    public static void main(String[] args) {
        new ClientCoAP("192.168.0.12", CoapConstants.COAP_DEFAULT_PORT);
    }
}