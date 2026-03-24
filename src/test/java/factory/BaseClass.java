package factory;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseClass {

		 static WebDriver driver;
	     static Properties p;
	     static Logger logger;
	  	     
	public static WebDriver initilizeBrowser() throws IOException
	{
		String gridURL = getProperties().getProperty("gridURL");
		if(getProperties().getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			/*DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//os
			if (getProperties().getProperty("os").equalsIgnoreCase("windows")) {
			    capabilities.setPlatform(Platform.WIN11);
			} else if (getProperties().getProperty("os").equalsIgnoreCase("mac")) {
			    capabilities.setPlatform(Platform.MAC);
			} else {
			    System.out.println("No matching OS..");
			      }
			//browser
			switch (getProperties().getProperty("browser").toLowerCase()) {
			    case "chrome":
			        capabilities.setBrowserName("chrome");
			        break;
			    case "edge":
			        capabilities.setBrowserName("MicrosoftEdge");
			        break;
			    default:
			        System.out.println("No matching browser");
			     }
	       
	        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);*/

			try {
		        if (getProperties().getProperty("browser").equalsIgnoreCase("chrome")) {
		            ChromeOptions options = new ChromeOptions();
		            options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
		            driver = new RemoteWebDriver(new URL(gridURL), options);
		        } else if (getProperties().getProperty("browser").equalsIgnoreCase("firefox")) {
		            FirefoxOptions options = new FirefoxOptions();
		            //options.addArguments("-headless");
		            options.addArguments("--window-size=1920,1080");
		            driver = new RemoteWebDriver(new URL(gridURL), options);
		        } else if (getProperties().getProperty("browser").equalsIgnoreCase("edge")) {
		            EdgeOptions options = new EdgeOptions();
		            options.addArguments("--headless=new", "--disable-gpu","--no-sandbox","--disable-dev-shm-usage");
		            driver = new RemoteWebDriver(new URL(gridURL), options);
		        } else {
		            //throw new IllegalArgumentException("Browser Not Supported: " + getProperties().getProperty("browser"));
		        	throw new IllegalArgumentException("Browser Not Supported");
		        }
		        //logger.info("RemoteWebDriver instance created for Grid in headless mode");
		    } catch (MalformedURLException e) {
		        throw new RuntimeException("Invalid Grid URL", e);
		    }
			
			
		}
		else if(getProperties().getProperty("execution_env").equalsIgnoreCase("local"))
			{
				switch(getProperties().getProperty("browser").toLowerCase()) 
				{
				case "chrome":
			        driver=new ChromeDriver();
			        break;
			    case "edge":
			    	driver=new EdgeDriver();
			        break;
			    default:
			        System.out.println("No matching browser");
			        driver=null;
				}
			}
		 driver.manage().deleteAllCookies(); 
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
		 
		 return driver;
		 
	}
	
	public static WebDriver getDriver() {
			return driver;
		}

	public static Properties getProperties() throws IOException
	{		 
        FileReader file=new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
       		
        p=new Properties();
		p.load(file);
		return p;
	}
	
	public static Logger getLogger() 
	{		 
		logger=LogManager.getLogger(); //Log4j
		return logger;
	}
	
	public static String randomeString()
	{
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	
	public static String randomeNumber()
	{
		String generatedString=RandomStringUtils.randomNumeric(10);
		return generatedString;
	}
	
		
	public static String randomAlphaNumeric()
	{
	String str=RandomStringUtils.randomAlphabetic(5);
	 String num=RandomStringUtils.randomNumeric(10);
	return str+num;
	}
	
	
}
