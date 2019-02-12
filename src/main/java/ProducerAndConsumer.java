import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProducerAndConsumer {
    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();

        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                /**Camel transfer message from producerTemplate to comsumerTemplate and modify message by process() method*/
                from("direct:start")
                        .process(new Processor() {
                            public void process(Exchange exchange) {
                                String message = exchange.getIn().getBody(String.class);
                                message = message +" We can change body if we want!";
                                exchange.getOut().setBody(message);
                            }
                        })
                        .to("seda:end");
            }
        });
        camelContext.start();
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "Hello everyBody on Camel!");

        ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();
        String message = consumerTemplate.receiveBody("seda:end", String.class);
        System.out.println(message);
    }
}
