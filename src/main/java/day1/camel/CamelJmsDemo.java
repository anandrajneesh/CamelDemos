package day1.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Created with IntelliJ IDEA.
 * User: ARA8376
 * Date: 08/11/13
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
public class CamelJmsDemo {

    public static void main(String [] args){
        try {
            final CamelContext context = new DefaultCamelContext();
            context.setTracing(true);

            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {

                    errorHandler(loggingErrorHandler());

                    from("activemq:bar.demo")
                            .log("jms.demo?showAll=true")
                            .process(new Processor() {
                                @Override
                                public void process(Exchange exchange) throws Exception {
                                    System.out.println("Received body from mq queue");
                                    System.out.println(exchange.getIn().getBody(String.class));
                                }
                            })
                    .end();


                    from("direct:foo")
                            .process(new Processor() {
                                @Override
                                public void process(Exchange exchange) throws Exception {
                                    System.out.println("This is what i expect");
                                }
                            })
                            .to("activemq:queue:bar.demo");


                }
            });

            Runnable producer = new Runnable() {
                @Override
                public void run() {
                    ProducerTemplate producerT = context.createProducerTemplate();
                    producerT.sendBody("direct:foo", "This is hello world message !");
                }
            };
            Thread t = new Thread(producer);
            t.start();
            context.start();
            context.stop();


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
