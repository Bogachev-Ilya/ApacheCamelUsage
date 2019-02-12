import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

public class FileTransferToActiveMQ {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        ConnectionFactory factory = new ActiveMQConnectionFactory();
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(factory));
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                /**start ActiveMq server and create queue with name - first_queue; file will be copy to server*/
                from("file:copyFrom?noop=true")
                        .to("activemq:queue:first_queue");
            }
        });


        context.start();
        Thread.sleep(5000);
        context.stop();
    }

}
