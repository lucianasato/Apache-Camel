package componentescamel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CopyFile {
	public CopyFile() throws Exception{
		final long sleep = 10000 ;
		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder(){
			public void configure(){
				
				from("file:data/inbox?noop=true")
				.to("file:data/outbox");
				
				
			}
		});
		
		context.start();
		Thread.sleep( sleep );
		context.stop();
	}
}