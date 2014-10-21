package org.example.shared.concurrency

import groovy.transform.Immutable

/**
 * A message indicating a desire to look up the price of a particular stock.
 */
@Immutable
class Lookup {
    String ticker
}
