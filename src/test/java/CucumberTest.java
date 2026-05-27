import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
// Al poner "pe.edu.upeu.api_orders", Cucumber buscará en la raíz (encontrando la configuración) y también en subcarpetas como bdd (encontrando los steps)
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "pe.edu.upeu.api_orders")
public class CucumberTest {

}