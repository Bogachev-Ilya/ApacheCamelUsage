import entity.ResultHandler;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.postgresql.ds.PGSimpleDataSource;

public class SqlOperator {
    public static void main(String[] args) throws Exception {
        /**setup your connection to database*/
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/apache_camel");
        dataSource.setUser("postgres");
        dataSource.setPassword("root");

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("dataSource", dataSource);

        CamelContext context = new DefaultCamelContext(registry);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:start")
                        .to("jdbc:dataSource")
                        /**configure bean to print sql query result from database*/
                        .bean(new ResultHandler(), "printResult");
            }
        });

        context.start();

        ProducerTemplate producerTemplate = context.createProducerTemplate();
        /**create table customer and add a few data*/
        producerTemplate.sendBody("direct:start", "select * from customer");
    }
}
