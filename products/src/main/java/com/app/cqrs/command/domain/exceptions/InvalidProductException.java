package com.app.cqrs.command.domain.exceptions;

/**
 * Domain exception thrown when a product fails validation rules.
 * 
 * <p>This exception is typically thrown when attempting to create or update a product
 * with invalid data that violates business rules. Common scenarios include:
 * <ul>
 *   <li>Product price is less than or equal to zero</li>
 *   <li>Product name is null, empty, or exceeds maximum length</li>
 *   <li>Product description contains invalid characters</li>
 *   <li>Product category is not recognized</li>
 * </ul>
 * 
 * <p>This exception follows the domain-driven design pattern where business rule
 * violations are expressed as domain exceptions rather than generic validation errors.
 * 
 * @author Generated
 * @since 1.0.0
 * @see RuntimeException
 */
public class InvalidProductException extends RuntimeException {

    private String message;

    public InvalidProductException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
