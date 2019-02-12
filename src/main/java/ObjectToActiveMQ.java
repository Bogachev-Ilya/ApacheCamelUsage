import entity.User;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;
import java.util.Date;

public class ObjectToActiveMQ {
    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();

        ConnectionFactory factory = new ActiveMQConnectionFactory();
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(factory));
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                /**Start ActiveMq server with queue - first_queue; Camel transfer new entity.User to server**/
                from("direct:start")
                        .to("activemq:queue:first_queue");
            }
        });

        context.start();

        ProducerTemplate producerTemplate = context.createProducerTemplate();
        producerTemplate.sendBody("direct:start", new User("Nikolas", new Date(), "some_emal@gmal.com"));
    }
}
