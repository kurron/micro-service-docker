package org.example.shared

import java.security.SecureRandom
import spock.lang.Specification

/**
* Base class for all Spock-based tests.  A nice place to put shared data generation routines.
**/
class BaseSpecification extends Specification {

    /**
     * Generates random data.
     */
    final SecureRandom randomizer = new SecureRandom()

    String randomHexString() {
        Integer.toHexString( randomPositiveInteger( Integer.MAX_VALUE ) ).toUpperCase()
    }

    int randomPositiveInteger( int bound ) {
        randomizer.nextInt( bound )
    }
}
