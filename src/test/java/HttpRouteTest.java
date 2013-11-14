import day3.HttpRoute;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.net.ConnectException;

/**
 * Created with IntelliJ IDEA.
 * User: ARA8376
 * Date: 14/11/13
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
public class HttpRouteTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {

        return new HttpRoute();
    }

    @Test
    public void testIntercept() throws Exception{
        RouteDefinition route = context.getRouteDefinitions().get(0);
        route.adviceWith(context, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("http://*")
                        .skipSendToOriginalEndpoint()
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                exchange.getIn().setBody("Dummy return from here instead of actual http service");
                            }
                        });
            }

        });

        template.sendBody("direct:foohttp","This is just a test message");
    }

    /**
     * Though we are throwing ConnectException from processor, but Camel wraps it up and
     * throws CamelExecutionException
     * @throws Exception
     */
    @Test(expected = CamelExecutionException.class)
    public void testInterceptSimulateException() throws Exception{
        RouteDefinition route = context.getRouteDefinitions().get(0);
        route.adviceWith(context, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("http://*")
                        .skipSendToOriginalEndpoint()
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                throw new ConnectException("Connection Refused");
                            }
                        });
            }

        });

        template.sendBody("direct:foohttp","This is just a test message");
    }






}
