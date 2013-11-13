import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ARA8376
 * Date: 13/11/13
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class SimpleMockRouteTest extends CamelTestSupport {

    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:foo")
                        .to("mock:bar");
            }
        };
    }



    @Test
    public void testExpectedCount() throws InterruptedException {
        MockEndpoint mock = getMockEndpoint("mock:bar");
        mock.expectedMessageCount(1);

        template.sendBody("direct:foo", "This is just a test message!");

        mock.assertIsSatisfied();
    }

    @Test
    public void testExpectedBodies() throws InterruptedException {
        MockEndpoint mock = getMockEndpoint("mock:bar");
        mock.expectedBodiesReceivedInAnyOrder("Test1","Test3","Test2","Test5");
        mock.allMessages().body().contains("Test");
        /**
         * If want to check order also, then use below commented method
         */
        //mock.expectedBodiesReceived("Test1","Test3","Test2","Test5");
        template.sendBody("direct:foo", "Test1");
        template.sendBody("direct:foo", "Test3");
        template.sendBody("direct:foo", "Test2");
        template.sendBody("direct:foo", "Test5");

        /**
         * Can use either of them for assertion
         */
        //assertMockEndpointsSatisfied();
        mock.assertIsSatisfied();
    }

    @Test
    public void testExpected() throws InterruptedException {
        MockEndpoint mock = getMockEndpoint("mock:bar");
        /**
         * Can be used for controlling any situation for eg simulating an error
         * or sending a pre defined response
         */
        mock.whenAnyExchangeReceived(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                System.out.println("your message came through successfully");
            }
        });

        mock.expectedMinimumMessageCount(1);

        template.sendBody("direct:foo", "Test message!");

        mock.assertIsSatisfied();

        List<Exchange> list = mock.getReceivedExchanges();
        String body = list.get(0).getIn()
                .getBody(String.class);
        System.out.println(body);
    }


}
