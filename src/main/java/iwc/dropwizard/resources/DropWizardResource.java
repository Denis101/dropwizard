package iwc.dropwizard.resources;

import iwc.dropwizard.api.Saying;
import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class DropWizardResource {
	private final String template;
	private final String defaultName;
	private final AtomicLong counter;
	
	public DropWizardResource(String template, String defaultName) {
		this.template = template;
		this.defaultName = defaultName;
		this.counter = new AtomicLong();
	}
	
	@GET
	@Timed
	public Saying sayHello(@QueryParam("name") Optional<String> name) {
		final String value = String.format(template, name.or(this.defaultName));
		return new Saying(counter.incrementAndGet(), value);
	}
	
	@GET
	@Timed
	@Path("/{name}")
	public Saying sayHelloName(@PathParam("name") Optional<String> name) {
		final String value = String.format(template, name.or(this.defaultName));
		return new Saying(counter.incrementAndGet(), value);
	}
}
