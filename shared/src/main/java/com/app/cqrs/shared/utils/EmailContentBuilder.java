package com.app.cqrs.shared.utils;

/**
 * Utility class for building email content including subjects and bodies
 */
public class EmailContentBuilder {

    /**
     * Builds the subject line for an order confirmation email
     * 
     * @param orderId the order ID
     * @return formatted email subject
     */
    public static String buildOrderConfirmationSubject(String orderId) {
        return "Order Confirmation - Order #" + orderId;
    }

    /**
     * Builds the body content for an order confirmation email
     * 
     * @param orderId the order ID
     * @return formatted email body
     */
    public static String buildOrderConfirmationBody(String orderId) {
        return "Dear Customer,\n\n" +
               "Your order #" + orderId + " has been successfully processed and approved.\n" +
               "Thank you for your business!\n\n" +
               "Best regards,\n" +
               "The Order Processing Team";
    }

    /**
     * Builds the subject line for an order confirmation email with customer name
     * 
     * @param orderId the order ID
     * @param customerName the customer name
     * @return formatted email subject
     */
    public static String buildOrderConfirmationSubject(String orderId, String customerName) {
        return "Order Confirmation - Order #" + orderId + " for " + customerName;
    }

    /**
     * Builds the body content for an order confirmation email with customer name
     * 
     * @param orderId the order ID
     * @param customerName the customer name
     * @return formatted email body
     */
    public static String buildOrderConfirmationBody(String orderId, String customerName) {
        return "Dear " + customerName + ",\n\n" +
               "Your order #" + orderId + " has been successfully processed and approved.\n" +
               "Thank you for your business!\n\n" +
               "Best regards,\n" +
               "The Order Processing Team";
    }

    /**
     * Builds the subject line for an order failure email
     * 
     * @param orderId the order ID
     * @return formatted email subject
     */
    public static String buildOrderFailureSubject(String orderId) {
        return "Order Processing Failed - Order #" + orderId;
    }

    /**
     * Builds the body content for an order failure email
     * 
     * @param orderId the order ID
     * @param reason the failure reason
     * @return formatted email body
     */
    public static String buildOrderFailureBody(String orderId, String reason) {
        return "Dear Customer,\n\n" +
               "We regret to inform you that your order #" + orderId + " could not be processed.\n" +
               "Reason: " + reason + "\n\n" +
               "Please contact our customer service team for assistance.\n\n" +
               "Best regards,\n" +
               "The Order Processing Team";
    }

    /**
     * Builds the subject line for a payment failure email
     * 
     * @param orderId the order ID
     * @return formatted email subject
     */
    public static String buildPaymentFailureSubject(String orderId) {
        return "Payment Failed - Order #" + orderId;
    }

    /**
     * Builds the body content for a payment failure email
     * 
     * @param orderId the order ID
     * @return formatted email body
     */
    public static String buildPaymentFailureBody(String orderId) {
        return "Dear Customer,\n\n" +
               "We were unable to process the payment for your order #" + orderId + ".\n" +
               "Please check your payment information and try again.\n\n" +
               "Best regards,\n" +
               "The Order Processing Team";
    }
}