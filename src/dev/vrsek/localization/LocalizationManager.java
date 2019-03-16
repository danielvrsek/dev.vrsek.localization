package dev.vrsek.localization;

import dev.vrsek.utils.exceptions.NotInitializedException;

public class LocalizationManager {
	private static Localization localization;

	public static Localization get() throws NotInitializedException {
		if (localization == null) {
			throw new NotInitializedException("LocalizationManager");
		}

		return localization;
	}

	public static void initialize(Localization localization) {
		LocalizationManager.localization = localization;
	}
}
