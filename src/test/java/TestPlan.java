import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class TestPlan {
        WebDriver driver;

    @BeforeClass
    @Parameters("browser")
    public void setUp(String browser) {
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            driver.manage().window().maximize();
        }
    }
   /* @BeforeClass
        public void letsBegin(){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();

        }*/
        @Test
        public void thatsOne(){
            WebDriverWait wait = new WebDriverWait(driver,10);
            Actions act = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;

            //getting on site
            String myLink = "http://automationpractice.com/index.php";
            driver.get(myLink);
            //Move to 'Women' and select 'T-shirts'
            WebElement whamenDropDown = driver.findElement(By.xpath("//div[@id ='block_top_menu']//a[contains (text(), 'Women')]"));
            act.moveToElement(whamenDropDown).perform();
            WebElement tShirt = driver.findElement(By.xpath("//div[@id ='block_top_menu']//a[contains(text(), 'T-shirts')]"));
            wait.until(ExpectedConditions.visibilityOf(tShirt));
            tShirt.click();
            //t shirts selected

            //now lets select a quickview
            try {
                Thread.sleep(5000);
            }catch (Exception e){

            }

            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[starts-with(@class, 'product_list')]"))));
            js.executeScript("arguments[0].scrollIntoView()",driver.findElement(By.xpath("//ul[starts-with(@class, 'product_list')]")));
            act.moveToElement(driver.findElement(By.cssSelector("div.product-container"))).perform();
            WebElement quickViewButton = driver.findElement(By.className("quick-view"));
            wait.until(ExpectedConditions.visibilityOf(quickViewButton));
            js.executeScript("arguments[0].click()", quickViewButton);
            //switchTo frame of quickview and act thereქ
            WebElement quickViewFrame = driver.findElement(By.cssSelector("iframe[id^='fancybox-frame']"));
            driver.switchTo().frame(quickViewFrame);
            //hovering pictures and making sure they change
            List<WebElement> thumbnailImages = driver.findElements(By.xpath("//ul[@id='thumbs_list_frame']/child::li"));
            for(int i=1; i<=thumbnailImages.size();i++){
                String startOfxPath = "//ul[@id='thumbs_list_frame']/li[@id='thumbnail_";
                String xPath = startOfxPath + i + "']";
                WebElement picToHover =driver.findElement(By.xpath(xPath));
                WebElement bigImage = driver.findElement(By.id("bigpic"));
                act.moveToElement(picToHover).perform();
                String srcAttributeChangable = "p/" + i + "/" + i;
                wait.until(ExpectedConditions.attributeContains(bigImage, "src", srcAttributeChangable));
                if (!bigImage.getAttribute("src").contains(srcAttributeChangable)) {
                    System.out.println("something wrong");
                }else{
                    System.out.println("everything seems okay");
                }
            }
            //adding to cart
            WebElement addButton = driver.findElement(By.cssSelector("i.icon-plus"));
            addButton.click();
            Select sel = new Select(driver.findElement(By.id("group_1")));
            sel.selectByValue("2");
            WebElement sendToCartButton = driver.findElement(By.xpath("//p[@id='add_to_cart']/button"));
            sendToCartButton.click();
            //continue shopping
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("layer_cart"))));
            WebElement continueShopping = driver.findElement(By.xpath("//div[@id='layer_cart']//span[@title='Continue shopping']"));
            continueShopping.click();
            //going to main menu

            js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
            WebElement ShopLogoButton = driver.findElement(By.id("header_logo"));
            wait.until(ExpectedConditions.visibilityOf(ShopLogoButton));
            ShopLogoButton.click();



            WebElement dressDropDown = driver.findElement(By.xpath("//div[@id='block_top_menu']/ul/li[2]/a"));
            WebElement casualDress = driver.findElement(By.xpath("//ul[@class ='submenu-container clearfix first-in-line-xs']/li/a[contains (text(), 'Casual')]"));
            act.moveToElement(dressDropDown).perform();
            casualDress.click();
            try {
                Thread.sleep(5000);
            }catch (Exception e){

            }
            js.executeScript("arguments[0].scrollIntoView()", driver.findElement(By.cssSelector("div[class$='product-image-container']")));
            act.moveToElement(driver.findElement(By.cssSelector("div[class$='product-image-container']")));
            act.click(driver.findElement(By.xpath("//a[@title='Add to cart']/span[contains(text(), 'Add to cart')]")));
            act.perform();
            System.out.println("breaks here");
            WebElement continueShoping2 = driver.findElement(By.xpath("//i[@class ='icon-chevron-left left']"));
            wait.until(ExpectedConditions.visibilityOf(continueShoping2));
            continueShoping2.click();

            //Move to the Cart and Check out
            js.executeScript("window.scrollTo(document.body.scrollHeight,0)");

            act.moveToElement(driver.findElement(By.partialLinkText("Cart"))).perform();

            WebElement checkoutButton = driver.findElement(By.cssSelector("a#button_order_cart"));
            wait.until(ExpectedConditions.visibilityOf(checkoutButton));
            checkoutButton.click();

            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("order-detail-content"))));

            act.moveToElement(driver.findElement(By.xpath("//*[@id='order-detail-content']"))).perform();

            List<WebElement> summaryItems = driver.findElements(By.cssSelector("tr[id^='product']"));
            String[] expectedItems = {"Faded Short Sleeve T-shirts", "Printed Dress"};

            for (int i = 0; i < summaryItems.size(); i++) {
                List<WebElement> itemDesc = driver.findElements(By.xpath("//tr[starts-with(@id,'product')]/child::td[contains(@class, 'description')]/p"));

                if (itemDesc.get(i).getText().equals(expectedItems[i])) {
                    System.out.println("Correct Item");
                } else {
                    System.out.println("somethings wrong");
                }
            }
            // I will need this later i believe
            String priceSum = driver.findElement(By.id("total_price")).getText();

          //prociding to checkut
            js.executeScript("arguments[0].scrollIntoView()", driver.findElement(By.cssSelector("p[class^='cart_navigation']")));
            WebElement proceedToCheckOutButton = driver.findElement(By.xpath("//p[@class='cart_navigation clearfix']/a"));
            proceedToCheckOutButton.click();
            System.out.println("clicked");

            //creating a registration form with email
            String myName = "mySampleName";
            Random randomInt = new Random();
            String myEmailSample = myName + randomInt.nextInt() + "@yahoo.com";
            WebElement emailField = driver.findElement(By.id("email_create"));
            emailField.sendKeys(myEmailSample);
            WebElement  submitCreationButton = driver.findElement(By.id("SubmitCreate"));
            submitCreationButton.click();
            //Fill in info

            ///
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("uniform-id_gender1")));
            WebElement firstName = driver.findElement(By.id("customer_firstname"));
            WebElement lastName = driver.findElement(By.id("customer_lastname"));
            String myFirstName = "Ricardo";
            String myLastName = "Martinez";
            firstName.sendKeys(myFirstName);
            lastName.sendKeys(myLastName);
            WebElement passwordField = driver.findElement(By.id("passwd"));
            String myPass = "1000011";
            passwordField.sendKeys(myPass);
            WebElement adressField = driver.findElement(By.id("address1"));
            String myAdress = "Pushkin Street Kalatushkin House";
            adressField.sendKeys(myAdress);
            WebElement cityField = driver.findElement(By.id("city"));
            String myCity = "Alcatraz";
            cityField.sendKeys(myCity);
            WebElement postField =  driver.findElement(By.id("postcode"));
            String myPost = "60000";
            postField.sendKeys(myPost);
            WebElement phoneField = driver.findElement(By.id("phone_mobile"));
            String myPhone = "555555555";
            phoneField.sendKeys(myPhone);

            Select date = new Select(driver.findElement(By.id("days")));
            date.selectByValue("25");
            Select month = new Select(driver.findElement(By.id("months")));
            month.selectByValue("6");
            Select year = new Select(driver.findElement(By.id("years")));
            year.selectByValue("2001");
            Select state = new Select(driver.findElement(By.id("id_state")));
            state.selectByValue("1");
            Select country = new Select(driver.findElement(By.id("id_country")));
            country.selectByValue("21");

            WebElement submittionFinal = driver.findElement(By.id("submitAccount"));
            submittionFinal.click();
            System.out.println("submitted");
            WebElement proceedToCheckOutButton2 = driver.findElement(By.name("processAddress"));
            wait.until(ExpectedConditions.visibilityOf(proceedToCheckOutButton2));
            proceedToCheckOutButton2.click();
            System.out.println("proceed");
           /////
            driver.findElement(By.name("processCarrier")).click();
            WebElement alertWindow = driver.findElement(By.xpath("//p[contains(text(),'must agree')]"));
            if (alertWindow.isDisplayed()) {
                js.executeScript("arguments[0].click()", driver.findElement(By.cssSelector("div[class^='fancybox-overlay']")));
            }
            js.executeScript("arguments[0].scrollIntoView()", driver.findElement(By.id("cgv")));
            js.executeScript("arguments[0].click()", driver.findElement(By.id("cgv")));
            driver.findElement(By.name("processCarrier")).click();

            WebElement newPriceWithTaxes = driver.findElement(By.id("total_price"));

            if (!priceSum.equals(newPriceWithTaxes.getText())) {
                System.out.println(priceSum);
                System.out.println(newPriceWithTaxes.getText());
            }
            WebElement checkButton = driver.findElement(By.partialLinkText("Pay by check "));
            checkButton.click();
            WebElement confirmButton =  driver.findElement(By.xpath("//span[contains(text(),'I confirm my order')]"));
           wait.until(ExpectedConditions.visibilityOf(confirmButton));
            confirmButton.click();
            WebElement customerService = driver.findElement(By.linkText("customer service department."));
            wait.until(ExpectedConditions.visibilityOf(customerService));
            act.moveToElement(customerService).click().perform();

            WebElement formPage =driver.findElement(By.className("clearfix"));
            wait.until(ExpectedConditions.visibilityOf(formPage));
            Select Heading = new Select(driver.findElement(By.id("id_contact")));
            Select referenceOrder = new Select(driver.findElement(By.name("id_order")));
            WebElement textArea = driver.findElement(By.id("message"));
            Heading.selectByValue("2");
            referenceOrder.selectByIndex(1);
            textArea.sendKeys("Hello World");
            WebElement uploadField = driver.findElement(By.id("fileUpload"));
            uploadField.sendKeys("D:\\JavaProjects\\SeleniumProject\\text.txt");

            driver.findElement(By.id("submitMessage")).click();

        }

}
