import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigLoader {
    private String property;
    private static String configLoc;

    public void loadConfig(String property) {
        try {
            Path configFilePath = Paths.get(configLoc+"/config.properties");
            FileInputStream propsInput = new FileInputStream(String.valueOf(configFilePath));
            Properties prop = new Properties();
            prop.load(propsInput);
            this.property = prop.getProperty(property);
        } catch (FileNotFoundException e) {
            System.out.println("config file not found");
        } catch (IOException e) {
            System.out.println("something went wrong");
        }
    }

    public String getProperty() { return property; }

    public void setConfigLoc(String configLoc) { ConfigLoader.configLoc = configLoc; }
}

