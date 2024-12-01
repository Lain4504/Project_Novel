package com.backend.notificationservice.enums;

import lombok.Getter;

@Getter
public enum NotificationTemplate {
    ACTIVATION_EMAIL("""
        <html>
        <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f4f4f4;
                    margin: 0;
                    padding: 0;
                }
                .container {
                    max-width: 600px;
                    margin: 20px auto;
                    background-color: #ffffff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                }
                .button {
                    display: inline-block;
                    padding: 12px 20px;
                    background-color: #000000;
                    color: white;
                    text-decoration: none;
                    border-radius: 5px;
                    font-weight: bold;
                    text-align: center;
                    margin-top: 20px;
                    transition: background-color 0.3s;
                }
                .button:hover {
                    background-color: #333333;
                }
                .button:link, .button:visited, .button:active {
                    color: white;  /* Ensure text remains white in all states */
                }
                h2 {
                    color: #333;
                }
                p {
                    font-size: 16px;
                    color: #555;
                }
            </style>
        </head>
        <body>
            <div class='container'>
                <h2>Welcome to our platform!</h2>
                <p>Thank you for registering with us. To activate your account, please click the button below:</p>
                <a href='%s' class='button'>Activate Account</a>
                <p>If you did not request this activation, please ignore this email.</p>
                <p>Best regards,</p>
                <p>The Forever Bookstore Team</p>
            </div>
        </body>
        </html>
        """),

    RESET_PASSWORD_EMAIL("""
        <html>
        <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f4f4f4;
                    margin: 0;
                    padding: 0;
                }
                .container {
                    max-width: 600px;
                    margin: 20px auto;
                    background-color: #ffffff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                }
                .button {
                    display: inline-block;
                    padding: 12px 20px;
                    background-color: #000000;
                    color: white;
                    text-decoration: none;
                    border-radius: 5px;
                    font-weight: bold;
                    text-align: center;
                    margin-top: 20px;
                    transition: background-color 0.3s;
                }
                .button:hover {
                    background-color: #333333;
                }
                .button:link, .button:visited, .button:active {
                    color: white;  /* Ensure text remains white in all states */
                }
                h2 {
                    color: #333;
                }
                p {
                    font-size: 16px;
                    color: #555;
                }
            </style>
        </head>
        <body>
        <div class="container">
            <h2>Reset Your Password</h2>
                <p>We received a request to reset your password. If you requested this, click the button below:</p>
                <a href="%s" class="button">Reset Password</a>
            <p>If you did not request a password reset, please ignore this email.</p>
                <p>Best regards,</p>
        </div>
        </body>
        </html>
        """);

    private final String template;

    NotificationTemplate(String template) {
        this.template = template;
    }

}
