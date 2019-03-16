package dev.vrsek.localization;

import dev.vrsek.localization.exceptions.LocalizationStringNotFoundException;

import java.util.Hashtable;

public class LocalizationPackage implements ILocalizationSource {
	private Locale locale;
	private Hashtable<String, LocalizationString> localizationStrings;

	public LocalizationPackage(Locale locale) {
		this.locale = locale;

		localizationStrings = new Hashtable<>();
	}

	protected Hashtable<String, LocalizationString> getLocalizationStrings() {
		return localizationStrings;
	}

	public void addLocalizationString(LocalizationString localizationString) {
		localizationStrings.put(localizationString.getResourceName(), localizationString);
	}

	@Override
	public String getString(String key) {
		String resourceName = LocalizationString.getResourceName(key);
		LocalizationString localizationString = localizationStrings.get(resourceName);

		if (localizationString == null) {
			throw new LocalizationStringNotFoundException(resourceName);
		}

		return localizationString.get(locale);
	}
}
