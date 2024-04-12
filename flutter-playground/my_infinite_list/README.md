# Infinite List widget locators

A Flutter sample app based on [infinite_list sample](https://github.com/flutter/samples/tree/main/infinite_list) that shows different strategies to locate widgets.

![An animated gif of the app in action](https://user-images.githubusercontent.com/919717/81858860-3a1e3280-9519-11ea-8e9c-9d22ac1bf0ed.gif)


## Goals for this sample

* Flutter mobile app
  - Expose widgets by key and text to Flutter test
  - Expose widgets by resource-id and content-desc to Appium driver when running the app on Android emulator
  - Expose widgets by accessibilityIdentifier and label to Appium driver when running the app on iOS simulator
  
* Flutter test
  - Find widgets by key and text
  - Retrieve and Assert on text from a located widget

## Limitations

### iOS identifiers
Could not get property "Semantics.identifier" to be exposed as accessibilityIdentifier when Appium Inspector connects to this Flutter App using "xcuitest" or "flutter" drivers, as described [here](https://forums.developer.apple.com/forums/thread/743902). On the other hand, property "Semantics.label" did transate into the element label, as expected.
- Could not debug presence of accessibilityIdentifier from Xcode "Debug View Hierarchy". The views we want to access via accessibilityIdentifier are all rendered inside a CAMetalLayer, and this tool [shows Metal rendered content in black](https://forums.developer.apple.com/forums/thread/743902). 

## Code relevant to the following flutter versions

Flutter 3.19.5 • channel stable • https://github.com/flutter/flutter.git
Framework • revision 300451adae (2 weeks ago) • 2024-03-27 21:54:07 -0500
Engine • revision e76c956498
Tools • Dart 3.3.3 • DevTools 2.31.1

## Sources
* [Flutter Widget Finders](https://docs.flutter.dev/cookbook/testing/widget/finders)
* [Interacting with widgets by Semantic Label by Maestro](https://maestro.mobile.dev/platform-support/flutter)
* [Preparing for Flutter cloud testing - testgrid](https://testgrid.io/blog/appium-flutter-testing)

 
