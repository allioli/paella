// ActionBot class encapsulates common user actions on MobileElements
public class ActionBot {

    private WebDriver driver;
    private final int defaultExplicitTimeoutInSeconds = 10;

    public ActionBot(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isElementPresent(MobileElement element) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean checkElementPresentAndTap(MobileElement element) {

        if (!isElementPresent(element))
            return false;

        element.click();
        return true;
    }

    public boolean checkElementPresentAndScroll(MobileElement element, String direction) {

        if (!isElementPresent(element))
            return false;

        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("direction", direction);
        scrollObject.put("element", ((RemoteWebElement) element).getId());
        js.executeScript("mobile: scroll", scrollObject);

        return true;
    }

    public boolean checkElementPresentAndEnterText(MobileElement element, String text) {

        if (!isElementPresent(element))
            return false;

        element.sendKeys(text);

        return true;
    }
}

// Abstract class Page with all common Page behaviour
public abstract class Page {

    protected WebDriver driver;
    protected ActionBot actionBot;

    // Allows test to run on slow emulators
    private int implicitTimeoutInSeconds = 10;

    public Page(WebDriver driver) {
        this.driver = driver;
        this.actionBot = new ActionBot(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, implicitTimeoutInSeconds, TimeUnit.SECONDS), this);
    }
}

public class SearchPage extends Page {

    // Using Android resource-id without package name 
    // Relies on appium fallback package name functionality

    // top left menu button
    @iOSFindBy(id = "sideMenu")
    @AndroidFindBy(id = "android:id/sideMenu")
    public MobileElement sideMenuButton;

    // user profile button
    @iOSFindBy(id = "userProfileButton")
    @AndroidFindBy(id = "android:id/userProfileButton")
    public MobileElement userProfileButton;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public boolean tapSideMenuButton() {
        return actionBot.checkElementPresentAndTap(sideMenuButton);
    }

    public boolean tapUserProfileButton() {
        return actionBot.checkElementPresentAndTap(userProfileButton);
    }
}

public class UserProfilePage extends Page {

    // Info tab
    @iOSFindBy(id = "infoTab")
    @AndroidFindBy(id = "android:id/infoTab")
    public MobileElement infoTab;

    // Profiles tab
    @iOSFindBy(id = "profilesTab")
    @AndroidFindBy(id = "android:id/profilesTab")
    public MobileElement profilesTab;

    // Phone validation button
    @iOSFindBy(id = "phoneValidationButton")
    @AndroidFindBy(id = "android:id/phoneValidationButton")
    public MobileElement phoneValidationButton;

    // Facebook validation button
    @iOSFindBy(id = "facebookValidationButton")
    @AndroidFindBy(id = "android:id/facebookValidationButton")
    public MobileElement facebookValidationButton;

    public UserProfilePage(WebDriver driver) {
        super(driver);
    }

    public boolean scrollProfilesTabLeft() {
        return actionBot.checkElementPresentAndScroll(profilesTab, "left");
    }

    public boolean scrollFacebookValidationButtonTabUp() {
        return actionBot.checkElementPresentAndScroll(facebookValidationButton, "up");
    }

    public boolean tapInfoTab() {
        return actionBot.checkElementPresentAndTap(infoTab);
    }

    public boolean tapPhoneValidationButton() {
        return actionBot.checkElementPresentAndTap(phoneValidationButton);
    }
}

public class IdentityValidationPage extends Page {

    // identity verification phone button
    @iOSFindBy(id = "identityVerificationPhoneButton")
    @AndroidFindBy(id = "android:id/identityVerificationPhoneButton")
    public MobileElement identityVerificationPhoneButton;

    // phone number text input
    @iOSFindBy(id = "phoneNumber")
    @AndroidFindBy(id = "android:id/phoneNumber")
    public MobileElement phoneNumberTextInput;

    // country code text input
    @iOSFindBy(id = "countryCode")
    @AndroidFindBy(id = "android:id/countryCode")
    public MobileElement countryCodeTextInput;

    // send phone number button
    @iOSFindBy(id = "sendPhoneNumberButton")
    @AndroidFindBy(id = "android:id/sendPhoneNumberButton")
    public MobileElement sendPhoneNumberButton;

    // verification code text input
    @iOSFindBy(id = "verificationCode")
    @AndroidFindBy(id = "android:id/verificationCode")
    public MobileElement verificationCodeTextInput;

    // send verification code button
    @iOSFindBy(id = "sendVerificationCode")
    @AndroidFindBy(id = "android:id/sendVerificationCode")
    public MobileElement sendVerificationCodeButton;

    // verification done button
    @iOSFindBy(id = "verificationDone")
    @AndroidFindBy(id = "android:id/verificationDone")
    public MobileElement verificationDoneButton;

    public IdentityValidationPage(WebDriver driver) {
        super(driver);
    }

    public boolean tapIdentityVerificationPhoneButton() {
        return actionBot.checkElementPresentAndTap(identityVerificationPhoneButton);
    }

    public boolean enterPhoneNumber(String text) {
        return actionBot.checkElementPresentAndEnterText(phoneNumberTextInput, text);
    }

    public boolean tapSendPhoneNumberButton() {
        return actionBot.checkElementPresentAndTap(sendPhoneNumberButton);
    }

    public boolean enterVerificationCode(String text) {
        return actionBot.checkElementPresentAndEnterText(verificationCodeTextInput, text);
    }

    public boolean tapSendVerificationCodeButton() {
        return actionBot.checkElementPresentAndTap(sendVerificationCodeButton);
    }

    public boolean tapVerificationDoneButton() {
        return actionBot.checkElementPresentAndTap(verificationDoneButton);
    }
}

public class NotificationsPage extends Page {

    // Android-only approach
    // https://qavalidation.com/2018/01/handle-android-push-notification-using-appium.html/

    // List of Notifications
    @AndroidFindBy(id = "android:id/title")
    List<MobileElement> allNotifications;

    public NotificationsPage(WebDriver driver) {
        super(driver);
    }

    public boolean wallapopSmsReceived() {

        // Assuming Android driver
        driver.openNotifications();
        Thread.sleep(2000);

        System.out.println("no of notifications " + allNotifications.size());

        for (MobileElement mobileElement : allNotifications) {
            System.out.println(mobileElement.getText());

            if (mobileElement.getText().contains("Wallapop")) {
                System.out.println("Wallapop SMS Notification found");
                return true;
            }
        }

        return false;

    }
}

public class userProfileTests {

    private WebDriver driver;
    private SearchPage searchPageObject;
    private UserProfilePage userProfilePageObject;
    private IdentityValidationPage identityValidationPageObject;
    private NotificationsPage notificationsPageObject;

    @Before
    public void setUp() throws Exception {

        // Assuming driver initialised with the required capabilities for the session
        // driver = 
        searchPageObject = new SearchPage(driver);
        userProfilePageObject = new UserProfilePage(driver);
        identityValidationPageObject = new IdentityValidationPage(driver);
        notificationsPageObject = new NotificationsPage(driver);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void userProfile_validateUserByPhoneNumber_isPhoneValidated() {

        System.out.println("---------- userProfile_validateUserByPhoneNumber_isPhoneValidated ----------");

        // Search page
        // Assuming user registered with location in Spain, logged in and Search page
        // visible and in context
        assertTrue("Search Page >> Side Menu NOT loaded", 
            searchPageObject.tapSideMenuButton());

        assertTrue("Search Page >> Side Menu >> User Profile button NOT loaded",
            searchPageObject.tapUserProfileButton());

        // User profile page
        assertTrue("User Profile Page >> Profiles tab NOT loaded",
            userProfilePageObject.scrollProfilesTabLeft());

        assertTrue("User Profile Page >> Info tab button NOT loaded",
            userProfilePageObject.tapInfoTab());

        assertTrue("User Profile Page >> Facebook validation button NOT loaded",
            userProfilePageObject.scrollFacebookValidationButtonTabUp());

        assertTrue("User Profile Page >> Phone validation button NOT loaded",
            userProfilePageObject.tapPhoneValidationButton());

        // Identity verification page
        assertTrue("Identity Verification Page >> Identity Verification Phone Button NOT loaded",
            identityValidationPageObject.tapIdentityVerificationPhoneButton());

        assertEquals("Identity Verification Page >> Default country code not set to +34 for user located in Spain",
            "+34", identityValidationPageObject.countryCodeTextInput.getAttribute("text"));

        assertTrue("Identity Verification Page >> Phone Number text input NOT loaded",
            identityValidationPageObject.enterPhoneNumber("555123456"));

        assertTrue("Identity Verification Page >> Send text message button NOT loaded",
            identityValidationPageObject.tapSendPhoneNumberButton());

        assertTrue("Wallapop SMS with Verification Code NOT received",
            notificationsPageObject.wallapopSmsReceived());

        // TODO: Read validation code from sms

        assertTrue("Identity Verification Page >> Verification code text input NOT loaded",
            identityValidationPageObject.enterVerificationCode("654321"));

        assertTrue("Identity Verification Page >> Send verification code button NOT loaded",
            identityValidationPageObject.tapSendVerificationCodeButton());

        assertTrue("Identity Verification Page >> Verification Done button NOT loaded",
            identityValidationPageObject.tapVerificationDoneButton());
    }
}
