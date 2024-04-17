// Copyright 2020 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/material.dart';

import 'api/item.dart';

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
      explicitChildNodes: true,
      container: true,
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

/// This is the widget responsible for building the "still loading" item
/// in the list (represented with "..." and a crossed square).
class LoadingItemTile extends StatelessWidget {
  const LoadingItemTile({super.key});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: ListTile(
        leading: const AspectRatio(
          aspectRatio: 1,
          child: Placeholder(),
        ),
        title: Text(
            '...',
            key: const ValueKey("item_title_loading"),
            style: Theme.of(context).textTheme.titleLarge
        ),
        trailing: const Text('\$ ...'),
      ),
    );
  }
}
