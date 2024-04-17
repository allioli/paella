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

## Set up & usage Flutter environment
1. [Install Flutter](https://docs.flutter.dev/get-started/install)
2. [Configure your favourite IDE](https://docs.flutter.dev/get-started/editor). I used Visual Studio Code.
3. Open [my_infinite_list](.) folder in your IDE
4. Enable Flutter driver extension
    - Based on the [driver setup guide](https://github.com/appium/appium-flutter-driver/blob/master/README.md#usage-and-requirement). There is a nice practical example in this [article](https://testgrid.io/blog/appium-flutter-testing/).
    - Check [pubspec.yaml](https://github.com/allioli/paella/blob/master/flutter-playground/my_infinite_list/pubspec.yaml#L28)) and [main.dart](https://github.com/allioli/paella/blob/master/flutter-playground/my_infinite_list/lib/main.dart#L18) to see how it looks in this app sample
5. Launch iOS simulator or Android emulator
6. Run the Flutter app from IDE, selecting iOS simulator or Android emulator on the running configuration
   - The app is build, installed and launched in the virtual device
7. In Visual Studio Code, the Flutter dev tools include the handy [Flutter Inspector](https://docs.flutter.dev/tools/devtools/inspector), to navigate the view tree accessible to the Flutter driver in real-time.
8. You can also run the stand-alone flutter test from IDE [`smoke_test.dart`](test/smoke_test.dart)

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


## Main code changes

### Exposing keys for item title, item price and loading items inside the list
For our Flutter test, we want to check item title as well as item price. This type of test is a good fit to check for loading states (mocked backend calls, simulated load time), so we are also going to need a key for loading items. 
- Adding key property to loading ListTile title - [`item_tile.dart`](https://github.com/allioli/paella/blob/master/flutter-playground/my_infinite_list/lib/src/item_tile.dart#L61)


- Adding key properties to ListTile title, price - [`item_tile.dart`](https://github.com/allioli/paella/blob/master/flutter-playground/my_infinite_list/lib/src/item_tile.dart#L33)
 
### Exposing Semantic identifier, label for each item inside the list
For the Appium test, we want to locate item tiles as a whole. Here we use real backend service and we limit the amount of granular asserts, focusing on the happy path scenario without digging too deep into fine detail.

We need to wrap up the Item tile Widget in a `Semantics` object that contains an `identifier` property (that will be exposed to appium as `resource-id` in Android / `accessibilityIdentifier` iOS). There is also an example of usage for the `label` property. This one will be exposed to appium as part of `content-desc` in Android / `label` in iOS. Here we are using a neat approach shared in this [article](https://maestro.mobile.dev/platform-support/flutter) to return the wrapped Widget, passing the desired identifier to the constructor.

```
/// This is the widget responsible for building the item in the list,
/// once we have the actual data [item].
class ItemTile extends StatelessWidget {
  final Item item;
  final String identifier;
  const ItemTile({required this.item, super.key, required this.identifier});

  @override
  Widget build(BuildContext context) {
    return Semantics(
      dentifier: identifier, // to expose unique id to appium (Android resource-id, iOS accessibility-id)
      label: identifier, // to expose semantics label to Flutter finder find.bySemanticsLabel(RegExp(r'identifier'))
      explicitChildNodes: true, //to expose separated elements for the ItemTile and its child Text elements
      container: false,
      child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: ListTile(
            leading: AspectRatio(
              aspectRatio: 1,
              child: Container(
                color: item.color,
              ),
            ),
            title: Text(
                item.name,
                style: Theme.of(context).textTheme.titleLarge,
                key: const ValueKey("item_title") // to expose key to Flutter finder find.byKey)
            ),
            trailing: Text(
                '\€ ${(item.price / 100).toStringAsFixed(2)}',
                key: const ValueKey("item_price")
            ),
          ),
      )
    );
  }
}
```

This is how the semantic identifier is defined in the constructor of ItemTile in [`main.dart`](lib/main.dart)
```
// Catalog provides a single synchronous method for getting the
// current data.
return switch (catalog.getByIndex(index)) {
  Item(isLoading: true) => const LoadingItemTile(),
  // Define Semantics identifier in constructor
  var item => ItemTile(item: item, identifier:  'colour-item')
};
```

### Finding by key, text and Semantics label in Flutter test
The existing [`smoke_test.dart`](test/smoke_test.dart) has been modified to check 
1. List loading state
2. New loaded items
3. User scroll down
4. List loading state
5. New loaded items

You will find examples of key and text finders, as well as [Semantics label](https://github.com/allioli/paella/blob/master/flutter-playground/my_infinite_list/test/smoke_test.dart#L44).

Last but not least, there is also an example of text retrieval from widget and assert on literal text:
```
// Check for title and price of top widget
    Text topItemTitle = tester.firstWidget(itemTitleFinder);
    expect(topItemTitle.data, 'Colour #0');

    Text topItemPrice = tester.firstWidget(itemPriceFinder);
    expect(topItemPrice.data, '€ 0.50');
```

## Results from Appium inspector / Limitations

### Result iOS (on `xcuitest`driver)
```
    <XCUIElementTypeOther type="XCUIElementTypeOther" enabled="true" visible="true" accessible="false" x="0" y="133" width="393" height="72" index="1">
                          <XCUIElementTypeOther type="XCUIElementTypeOther" name="colour-item" label="colour-item" enabled="true" visible="true" accessible="true" x="0" y="133" width="393" height="72" index="0"/>
                          <XCUIElementTypeStaticText type="XCUIElementTypeStaticText" value="Colour #0&#10;€ 0.50" name="Colour #0&#10;€ 0.50" label="Colour #0&#10;€ 0.50" enabled="true" visible="true" accessible="true" x="8" y="141" width="377" height="56" index="1"/>        
```
We can see how the `label` value is composed by the actual Widget text and the `Semantics.label` value. In this case the colour-item and its companion text nodes are exposed as sibling nodes.

## Result Android (on `flutter` and `uiautomator2` drivers)
```
                      <android.view.View index="0" package="dev.flutter.infinite_list" class="android.view.View" text="" content-desc="colour-item" resource-id="colour-item" checkable="false" checked="false" clickable="false" enabled="true" focusable="true" focused="false" long-clickable="false" password="false" scrollable="false" selected="false" bounds="[0,322][1080,511]" displayed="true">
                        <android.view.View index="0" package="dev.flutter.infinite_list" class="android.view.View" text="" content-desc="item_title&#10;item_price" resource-id="" checkable="false" checked="false" clickable="false" enabled="true" focusable="true" focused="false" long-clickable="false" password="false" scrollable="false" selected="false" bounds="[21,343][1059,490]" displayed="true" />
                 

```
Notice how the `content-desc` value is composed by the actual Widget text and the `Semantics.label` value. Here the `resource-id` value matches `Semantics.identifier`. In this case the colour-item and its companion text nodes are exposed as parent-child nodes.

#### iOS identifiers
Property `Semantics.identifier` not exposed as accessibilityIdentifier when Appium Inspector connects to this Flutter App using `xcuitest` or `flutter` drivers, as described [here](https://forums.developer.apple.com/forums/thread/743902). On the other hand, property `Semantics.label` did transate into the element label, as expected.

- Could not debug presence of `accessibilityIdentifier` from Xcode "Debug View Hierarchy". The views we want to access via `accessibilityIdentifier` are all rendered inside a `CAMetalLayer`, and this tool [shows Metal rendered content in black](https://forums.developer.apple.com/forums/thread/743902). 

#### Appium inspector session with flutter-driver iOS
I wasn't able to properly establish a session with this appium driver. The app would start, but then it was killed immediately after. WebDriverAgent app was installed. Tried with 2 different device / iOS version combinations with the same result.


### Code relevant to the following flutter versions

```
Flutter 3.19.5 • channel stable • https://github.com/flutter/flutter.git
Framework • revision 300451adae • 2024-03-27 21:54:07 -0500
Engine • revision e76c956498
Tools • Dart 3.3.3 • DevTools 2.31.1
```

## Sources
* [Flutter Widget Finders](https://docs.flutter.dev/cookbook/testing/widget/finders)
* [Interacting with widgets by Semantic Label by Maestro](https://maestro.mobile.dev/platform-support/flutter)
* [Preparing for Flutter cloud testing - testgrid](https://testgrid.io/blog/appium-flutter-testing/)
* [Appium Flutter driver docs](https://github.com/appium/appium-flutter-driver/blob/master/README.md)

 
