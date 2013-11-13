package day3;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: ARA8376
 * Date: 13/11/13
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
public class SimpleRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:foo")
          .to("direct:bar");


        from("direct:bar")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Message inMessage = exchange.getIn();
                        inMessage.setBody("Hi there",String.class);
                    }
                });
    }
}
