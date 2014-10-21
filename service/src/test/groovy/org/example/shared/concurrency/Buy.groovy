package org.example.shared.concurrency

import groovy.transform.Immutable

/**
 * A message indicating a request to purchase shares.
 */
@Immutable
class Buy {
    String ticker
    int quantity
}
