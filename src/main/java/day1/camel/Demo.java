package day1.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: ARA8376
 * Date: 08/11/13
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
public class Demo {
    public static void main(String [] args){
        try {
            CamelContext context = new DefaultCamelContext();
            context.setTracing(true);

            URL url = Demo.class.getClassLoader().getResource("source");
            final String sourceFolder = url.getPath();


            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                        from("file:"+sourceFolder+"?noop=true&recursive=true")
                                .log("camel.demo?showAll=true")
                                .to("file:/destination");

                }
            });

            context.start();

            Thread.sleep(10000);

        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
