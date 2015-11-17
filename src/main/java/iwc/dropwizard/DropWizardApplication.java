package iwc.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import iwc.dropwizard.resources.DropWizardResource;
import iwc.dropwizard.health.TemplateHealthCheck;

public class DropWizardApplication extends Application<DropWizardConfiguration>
{
	public static void main(String[] args) throws Exception {
		new DropWizardApplication().run(args);
	}
	
	@Override
	public String getName() {
		return "drop-wizard";
	}
	
	@Override
	public void initialize(Bootstrap<DropWizardConfiguration> bootstrap) {
		
	}
	
	@Override
	public void run(DropWizardConfiguration configuration, Environment environment) {
		final DropWizardResource resource = new DropWizardResource(
				configuration.getTemplate(),
				configuration.getDefaultName()
		);
		
		final TemplateHealthCheck healthCheck = 
				new TemplateHealthCheck(configuration.getTemplate());
		
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(resource);
	}
}
