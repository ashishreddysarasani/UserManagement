package com.example.service;

import org.springframework.core.io.Resource;

public interface NotificationServiceInterface {
	public boolean send(
			String to,
			String cc[],
			String bcc[],
			String subject,
			String text,
			Resource file
			);
}
