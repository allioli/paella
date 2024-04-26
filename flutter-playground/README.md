# Overview Flutter Playground
This folder contains modified Flutter app samples and companion appium mobile test automation projects.

## Infinite List Sample
- Modified sample in [my_infinite_list](my_infinite_list)
- Appium mobile automation project in [appium_flutter_infinite_list](appium_flutter_infinite_list)

# Set up & usage Appium - Flutter environment
1. Install [appium](https://appium.io/docs/en/2.0/quickstart/install/)
2. Install appium drivers uiautomator2, xcuitest, [flutter](https://github.com/appium/appium-flutter-driver/blob/master/README.md)
3. Install [appium inspector](https://appium.github.io/appium-inspector/latest/quickstart/)
4. Start appium server locally
5. Go to Flutter sample app project
  - Example [my_infinite_list](my_infinite_list)
6. Build the app package
   - `flutter build apk --debug` on Android
   - `flutter build ios --simulator` on iOS
   - Notice the generated APK / Runner.app path in the terminal logs.
7. Copy iOS app package to the appium project folder
   - `cp -r build/ios/iphonesimulator/Runner.app ../appium_flutter_infinite_list/apps/Runner.app`
8. Copy Android app package to the appium project folder
   - `build/app/outputs/flutter-apk/app-debug.apk ../appium_flutter_infinite_list/apps`
9. Go to Appium project
   - Example [appium_flutter_infinite_list](appium_flutter_infinite_list)  
10. Open appium inspector
11. Create a capability set for Android or iOS
   - Set `platformName`, `deviceName`, `udid` accordingly
   - Specify `automationName` with the desired driver. You can go with `flutter` driver with enhanced Flutter finders or keep it appium standard with `xcuitest`or `uiautomator2`. More on the pros / cons [here](https://github.com/appium/appium-flutter-driver/tree/master?tab=readme-ov-file#appium-flutter-driver-or-appium-uiautomator2xcuitest-driver)
   - Fill `app` with the path you copied the app packages to in Steps 7, 8 [appium_flutter_infinite_list/apps](appium_flutter_infinite_list/apps)
   - Set `disableIdLocatorAutocompletion` to `true` on Android, to expose the Semantics.identifier value as defined in the .dart code. Otherwise, the driver will compose that value with app package.
10. Start session with the local appium server
   - The app is installed to the running virtual device and started
   - You should see a fresh app screenshot with the view tree in Appium Inspector
   - On Android emulator with appium driver `flutter`, sometimes Appium Inspector gets stuck loading. As a workaround, click alternatively on the "Native" and "Hybrid" mode buttons quickly to get the view tree loaded.
11. Now you are ready to open the appium project
   - Refer to the README in the appium project to run tests. Example [appium_flutter_infinite_list/README.md](appium_flutter_infinite_list/README.md)
