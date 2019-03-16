package dev.vrsek.localization.exceptions;

public class LocalizationPackageNotFoundException extends RuntimeException {
	public LocalizationPackageNotFoundException(String packageName) {
		super("Package: " + packageName);
	}
}
