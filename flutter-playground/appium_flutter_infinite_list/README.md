# Appium mobile automation project

This is a companion project to the Flutter modified sample in [my_infinite_list](my_infinite_list).




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
I wasn't able to properly establish a session with this appium driver. The app would start, but then it was killed immediately after. WebDriverAgent app was installed. Tried with 2 different device / iOS version combinations with the same result. However, from the appium client project the connection worked fine.


