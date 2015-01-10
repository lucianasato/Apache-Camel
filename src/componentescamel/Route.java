package componentescamel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class Route {
	public Route() throws Exception{
		final long sleep = 10000 ;
		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder(){
			@Override
			public void configure(){
				/* Verifica a extensao do arquivo. Se for xml, o arquivo sera colocado 
				 * em uma pasta. Caso contrario, o arquivo
				 * sera direcionado para uma pasta de arquivos invalidos. 
				 */
				from("file:data/inbox/route?noop=true")
				.choice()
					.when(header("CamelFileName").endsWith(".xml"))
						.to("file:target/route/messages/files")
					.otherwise()
						.to("file:target/route/messages/files/invalids") ;
				
				/* Le o arquivo e separa os xmls onde state for SP.
				 * Essa pasta sera lida por uma aplicacao externa.
				 * Caso contario, o xml sera colocado em outra pasta.
				 */
				from("file:target/route/messages/files?noop=true")
	            .choice()
	                .when(xpath("/person/state = 'SP'"))
	                	// Caminho do diretorio onde os arquivos selecionados serao colocados
	                    .to("file:/Users/lucianasato/Desktop/Repositorio/arquivos/processados/sp/")
	                .otherwise()
	                	.to("file:/Users/lucianasato/Desktop/Repositorio/arquivos/processados/others/");
			}
		});
		
		context.start();
		Thread.sleep( sleep );
		context.stop();
	}
}
