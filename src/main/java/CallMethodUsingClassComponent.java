import entity.User;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

import java.util.Date;

public class CallMethodUsingClassComponent {
    public static void main(String[] args) throws Exception {
        /**create User bean*/
        User user = new User();
        SimpleRegistry registry = new SimpleRegistry();
        /**put user bean to registry*/
        registry.put("user", user);
        /**set registry with user bean to context*/
        CamelContext context = new DefaultCamelContext(registry);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure()  {
                from("direct:start")
                        /**call bean by key instead of call class*/
                        .to("bean:user?method=printUserCredentials");
            }
        });

        context.start();

        User john = new User("John", new Date(), "john@gmail.com");

        ProducerTemplate producerTemplate = context.createProducerTemplate();
        producerTemplate.sendBody("direct:start", john);
    }
}
