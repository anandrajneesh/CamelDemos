import day3.SimpleRoute;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ARA8376
 * Date: 13/11/13
 * Time: 16:25
 * To test already defined route
 */
public class SimpleRouteTest extends CamelTestSupport {


    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new SimpleRoute();
    }



    @Test
    public void testRoute(){
        template.sendBody("direct:foo","This is just a test message!");
    }

}
