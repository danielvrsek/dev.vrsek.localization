package dev.vrsek.localization.generator;

import dev.vrsek.localization.Locale;
import dev.vrsek.localization.LocalizationString;

import java.util.Hashtable;

public class LocalizationPackage extends dev.vrsek.localization.LocalizationPackage {
	public LocalizationPackage(Locale locale) {
		super(locale);
	}

	protected Hashtable<String, LocalizationString> getLocalizationStrings() {
		return super.getLocalizationStrings();
	}
}
