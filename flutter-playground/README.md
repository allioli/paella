

## Set up & usage Appium - Flutter environment
1. Install appium uiautomator2, xcuitest, [flutter](https://github.com/appium/appium-flutter-driver/blob/master/README.md) drivers
2. Install [appium inspector](https://appium.github.io/appium-inspector/latest/quickstart/)
3. Start appium server locally
4. Build the app package
   - `flutter build apk --debug` on Android
   - `flutter build ios --simulator` on iOS
   - Notice the generated APK / Runner.app path in the terminal logs. We'll need this app path later on.
5. Open appium inspector
6. Create a capability set for Android or iOS
   - Set `platformName`, `deviceName`, `udid` accordingly
   - Specify `automationName` with the desired driver. You can go with `flutter` driver with enhanced Flutter finders or keep it appium standard with `xcuitest`or `uiautomator2`. More on the pros / cons [here](https://github.com/appium/appium-flutter-driver/tree/master?tab=readme-ov-file#appium-flutter-driver-or-appium-uiautomator2xcuitest-driver)
   - Fill `app` with the app path from Step 3
   - Set `disableIdLocatorAutocompletion` to `true` on Android, to expose the Semantics.identifier value as defined in the .dart code. Otherwise, the driver will compose that value with app package.
7. Start session with the local appium server
   - The app is installed to the running virtual device and started
   - You should see a fresh app screenshot with the view tree in Appium Inspector
   - On Android emulator with appium driver `flutter`, sometimes Appium Inspector gets stuck loading. As a workaround, click alternatively on the "Native" and "Hybrid" mode buttons quickly to get the view tree loaded.
