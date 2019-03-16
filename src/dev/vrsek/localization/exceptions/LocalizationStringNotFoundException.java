package dev.vrsek.localization.exceptions;

public class LocalizationStringNotFoundException extends RuntimeException {
	public LocalizationStringNotFoundException(String resourceName) {
		super("Name: " + resourceName);
	}

}
