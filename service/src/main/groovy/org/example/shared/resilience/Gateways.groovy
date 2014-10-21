package org.example.shared.resilience

/**
 * Enumeration of all the resource gateways in the system..
 */
enum Gateways {
    SAVE_ECHO_DOCUMENT

    @Override
    String toString() {
        name().toLowerCase().replace( '_', '-' )
    }
}
