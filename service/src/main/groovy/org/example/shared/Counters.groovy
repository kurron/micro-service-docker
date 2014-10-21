package org.example.shared

/**
 * Enumeration of all the counters in the system providing metrics.
 */
enum Counters {
    HEALTH_CHECK

    @Override
    String toString() {
        new StringBuilder( 'org.example.' ).append( name().toLowerCase().replace( '_', '.' ) )
    }
}
