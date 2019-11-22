package com.user.management.app.service.api;

/**
 * email operations
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
public interface IEmailService {

    /**
     * Send mail to given email with specific subject and message
     * @param toEmail
     * @param subject
     * @param message
     */
    void sendMail (String toEmail, String subject, String message);
}
