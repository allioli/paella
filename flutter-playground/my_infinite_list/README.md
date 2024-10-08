# Infinite List widget locators

A Flutter sample app based on [infinite_list sample](https://github.com/flutter/samples/tree/main/infinite_list) that shows different strategies to locate widgets.

Companion Appium mobile automation project in [appium_flutter_infinite_list](appium_flutter_infinite_list)

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
      identifier: identifier, // to expose unique id to appium (Android resource-id, iOS accessibility-id)
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
* [New Flutter Semantics identifier property](https://github.com/flutter/flutter/issues/137735)

 
