// Copyright 2020 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

import 'package:infinitelist/main.dart';

void main() {
  testWidgets('Infinite list smoke test', (tester) async {

    final handle = tester.ensureSemantics();

    // Refresh UI
    await tester.pumpWidget(const MyApp());

    const loadingDuration = Duration(milliseconds: 500);

    // Test keys used by the app to distinguish UI elements.
    final loadingItemTitleFinder = find.byKey(const ValueKey("item_title_loading"));
    final itemTitleFinder = find.byKey(const ValueKey("item_title"));
    final itemPriceFinder = find.byKey(const ValueKey("item_price"));

    // At first, the catalog shows only "..." (loading items).
    // Use test key + expected text to check for presence
    expect(loadingItemTitleFinder, findsWidgets);
    expect(find.text('...'), findsWidgets);

    // Use test key to check for no presence
    expect(itemTitleFinder, findsNothing);

    // Refresh UI
    await tester.pump(loadingDuration);

    // After the app has had a chance to load items, it should no longer
    // show "..." (loading items).

    // Use test key to check for no presence
    expect(loadingItemTitleFinder, findsNothing);

    // Use test key + expected text to check for presence
    expect(itemTitleFinder, findsWidgets);
    expect(find.bySemanticsLabel(RegExp(r'colour-item')), findsWidgets);
    expect(find.text('Colour #1'), findsOneWidget);
    expect(itemPriceFinder, findsWidgets);

    // Check for title and price of top widget
    Text topItemTitle = tester.firstWidget(itemTitleFinder);
    expect(topItemTitle.data, 'Colour #0');

    Text topItemPrice = tester.firstWidget(itemPriceFinder);
    expect(topItemPrice.data, '€ 0.50');

    // Flinging up quickly (i.e. scrolling down).
    await tester.fling(find.byType(ListView), const Offset(0, -2000), 5000);

    // As we scroll down, we should see more items loading.

    // Use test key + expected text to check for presence
    expect(loadingItemTitleFinder, findsWidgets);
    expect(find.text('...'), findsWidgets);

    // The item at the top with specific text should no longer be in view.
    expect(find.text('Colour #1'), findsNothing);

    // Refresh UI
    await tester.pump(loadingDuration);

    // New items should be visible
    expect(itemTitleFinder, findsWidgets);
    expect(itemPriceFinder, findsWidgets);

    handle.dispose();
  });
}
