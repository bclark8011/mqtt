import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PahoDemo implements MqttCallback {

MqttClient client;

public PahoDemo() {
}

public static void main(String[] args) {
    new PahoDemo().doDemo();
}

public void doDemo() {
    try {
        MemoryPersistence persistence = new MemoryPersistence();
        client = new MqttClient("tcp://localhost:1883", "Sending", persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        //connOpts.setCleanSession(true);
        connOpts.setUserName("icads");
        connOpts.setPassword("icads".toCharArray());
        client.connect(connOpts);
        client.setCallback(this);
        client.subscribe("#");
        MqttMessage message = new MqttMessage();
        message.setPayload("A single message from my computer fff"
                .getBytes());
        client.publish("foo", message);
    } catch (MqttException e) {
        e.printStackTrace();
    }
}

@Override
public void connectionLost(Throwable cause) {
    // TODO Auto-generated method stub

}

@Override
public void messageArrived(String topic, MqttMessage message)
        throws Exception {
    if (topic.equalsIgnoreCase("command") && message.toString().equalsIgnoreCase("exit")) {
        System.exit(0);
    }
 System.out.println("Received:" + message);   
}

@Override
public void deliveryComplete(IMqttDeliveryToken token) {
    // TODO Auto-generated method stub

}

}