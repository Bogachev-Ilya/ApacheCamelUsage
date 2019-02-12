import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FileCopier {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("config.xml");
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                /**create copyFrom/input folders in root of Project if not exists; Camel create output folder for you*/
                from("file:copyFrom/input?noop=true")
                        .to("file:copyFrom/output");
            }
        });

        camelContext.start();
        /**time for copying files*/
        Thread.sleep(5000);
        camelContext.stop();
    }
}
