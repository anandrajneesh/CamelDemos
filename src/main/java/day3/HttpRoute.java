package day3;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: ARA8376
 * Date: 14/11/13
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 */
public class HttpRoute extends RouteBuilder {

    public void configure() throws Exception{
        from("direct:foohttp")
          .to("http://www.google.com?bridgeEndpoint=true&throwExceptionOnFailure=true");
    }


}
