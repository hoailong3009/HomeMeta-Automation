package HomeMeta.PageObjects;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import HomeMeta.TestComponents.BaseTest;

public class RegisterBroker extends BaseTest {
	@Test(dataProvider = "getData")
	public void RegisterBrokerFailed(HashMap<String, String> input) throws IOException, InterruptedException {
		landingPage.goToRegisterBroker();
		landingPage.regisBrokerFailed(input.get("company"), input.get("firstName"), input.get("lastName"),
				input.get("numberPhone"), input.get("linkURL"), input.get("email"), input.get("password"));

	}

	@Test(dataProvider = "getData1")
	public void RegisterBrokerTest(HashMap<String, String> input) throws IOException, InterruptedException {
		String roleName1 = "Broker";
		landingPage.goToRegisterBroker();
		landingPage.regisBrokerRole(input.get("company"), input.get("firstName"), input.get("lastName"),
				input.get("numberPhone"), input.get("linkURL"), input.get("email"), input.get("password"));
		Thread.sleep(500);

		driver.switchTo().newWindow(WindowType.TAB);
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		String parentWindowId = it.next();
		String childWindowId = it.next();
		driver.switchTo().window(childWindowId);

		landingPage.verifyEmail(input.get("email"));

		driver.switchTo().window(parentWindowId);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[normalize-space()='Okay']")).click();
		Thread.sleep(2500);
		landingPage.login(input.get("email"), input.get("password"));
		Boolean match = landingPage.VerifyBrokerRole(roleName1);
		Assert.assertTrue(match);
		System.out.println("Register Broker role Passed");

	}

	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonData(
				System.getProperty("user.dir") + "/src/main/java/HomeMeta/data/DataRegisterBroker.json");
		return new Object[][] { { data.get(0) } };
	}

	@DataProvider
	public Object[][] getData1() throws IOException {
		List<HashMap<String, String>> data = getJsonData(
				System.getProperty("user.dir") + "/src/main/java/HomeMeta/data/DataRegisterBroker.json");
		return new Object[][] { { data.get(1) } };
	}

}
