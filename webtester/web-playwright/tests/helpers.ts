// @ts-check

import { Locator } from "@playwright/test";

export async function clearAndFill(locator: Locator, text: string) {
    await locator.clear();
    await locator.fill(text);
}