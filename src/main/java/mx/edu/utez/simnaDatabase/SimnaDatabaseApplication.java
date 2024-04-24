package mx.edu.utez.simnaDatabase;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SimnaDatabaseApplication implements MqttCallback {

    private static final String MQTT_BROKER = "tcp://100.26.131.20:1883";
    private static final String CLIENT_ID = "springboot_client";
    public static final String TOPIC_A = "agua/porcentaje_A";
    public static final String TOPIC_B = "agua/porcentaje_B";
    public static String mensajeRecibido="1";

    public static String mensajeRecibido2="2";
    private MqttClient client;

    public static void main(String[] args) {
        SpringApplication.run(SimnaDatabaseApplication.class, args);
        SimnaDatabaseApplication mqtt = new SimnaDatabaseApplication();
        mqtt.connectToMqttBroker();
    }

    public void connectToMqttBroker() {
        try {
            System.out.println("Iniciando conexión con el broker MQTT...");
            client = new MqttClient(MQTT_BROKER, CLIENT_ID);
            System.out.println("Conectando al broker MQTT...");
            client.connect(getMqttConnectOptions());
            System.out.println("Conexión establecida con el broker MQTT.");
            client.setCallback(this);
            System.out.println("Suscribiéndose a los temas...");
            subscribeToTopics();
            System.out.println("Publicando mensajes...");
            publishMessages();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static String getMensajeRecibido(){
        return mensajeRecibido;
    }

    public static String getMensajeRecibido2(){
        return mensajeRecibido2;
    }

    private MqttConnectOptions getMqttConnectOptions() {
        System.out.println("Configurando opciones de conexión MQTT...");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        return options;
    }

    private void subscribeToTopics() throws MqttException {
        System.out.println("Suscribiéndose al tema: " + TOPIC_A);
        client.subscribe(TOPIC_A);
        System.out.println("Suscribiéndose al tema: " + TOPIC_B);
        client.subscribe(TOPIC_B);
    }

    private void publishMessages() throws MqttException {
        float porcentaje_agua_a = obtenerPorcentajeAguaSensorA();
        float porcentaje_agua_b = obtenerPorcentajeAguaSensorB();
        System.out.println("Publicando mensaje en el tema: " + TOPIC_A);
        publishMessage(TOPIC_A, Float.toString(porcentaje_agua_a));
        System.out.println("Publicando mensaje en el tema: " + TOPIC_B);
        publishMessage(TOPIC_B, Float.toString(porcentaje_agua_b));
    }

    private void publishMessage(String topic, String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        client.publish(topic, message);
    }

    private float obtenerPorcentajeAguaSensorA() {
        return 0.0f;
    }

    private float obtenerPorcentajeAguaSensorB() {
        return 0.0f;
    }

    public String getMensaje(){
        return mensajeRecibido;
    }
    public String getMensaje2() {return mensajeRecibido2;}

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String mensaje = new String(message.getPayload());
        if (topic.equals(TOPIC_A)) {
            mensajeRecibido = mensaje;
            System.out.println("Mensaje almacenado en la variable mensajeRecibido1: "+mensajeRecibido);

        }
        if (topic.equals(TOPIC_B)) {
            mensajeRecibido2 = mensaje;
            System.out.println("Mensaje almacenado en la variable mensajeRecibido2: "+mensajeRecibido2);

        }
    }


    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Conexión perdida con el broker MQTT.");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Método no utilizado en este ejemplo
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

}