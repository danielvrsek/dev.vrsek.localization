package dev.vrsek.localization;

import java.util.Hashtable;
import java.util.regex.Pattern;

public class LocalizationString {
	private static final String DELIMITER = ".";

	private final String resourceName;
	private final Hashtable<Locale, String> hashtable;

	public LocalizationString(String resourceName){
		this.resourceName = resourceName;
		this.hashtable = new Hashtable<>();
	}

	public String get(Locale locale) {
		String resourceString = hashtable.get(locale);

		if (resourceString == null) {
			resourceString = hashtable.get(Locale.DEFAULT);
		}

		return resourceString;
	}

	public void put(Locale locale, String resourceString) {
		hashtable.put(locale, resourceString);
	}

	public String getResourceName() {
		return resourceName;
	}

	public static String getLocaleId(String localizationStringIdentifier) {
		assert localizationStringIdentifier != null && !localizationStringIdentifier.isEmpty();

		String[] split = localizationStringIdentifier.split(Pattern.quote(DELIMITER));

		// Always first
		if (split.length > 1) {
			return split[0];
		}

		return Locale.DEFAULT.toString();
	}

	public static String getResourceName(String localizationStringIdentifier) {
		assert localizationStringIdentifier != null && !localizationStringIdentifier.isEmpty();

		String[] split = localizationStringIdentifier.split(Pattern.quote(DELIMITER));

		// Always last
		return split[split.length - 1];
	}
}
