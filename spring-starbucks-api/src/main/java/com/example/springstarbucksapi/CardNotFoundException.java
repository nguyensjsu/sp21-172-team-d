package com.example.springstarbucksapi;

class CardNotFoundException extends RuntimeException {

    /**
     * Did this to get rid of a serializable class warning
     */
    private static final long serialVersionUID = 1L;

    CardNotFoundException(Long id) {
        super("Could not find card " + id);
    }
}
