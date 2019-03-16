package dev.vrsek.localization.json;

import com.google.gson.*;
import dev.vrsek.localization.Locale;
import dev.vrsek.localization.LocalizationPackage;
import dev.vrsek.localization.LocalizationString;
import dev.vrsek.utils.collections.Collections;

import java.lang.reflect.Type;
import java.util.function.Function;

public class LocalizationPackageJsonDeserializer<T extends LocalizationPackage> implements JsonDeserializer<T> {
	private final Locale locale;
	private final Function<Locale, T> activator;

	public LocalizationPackageJsonDeserializer(Locale locale, Function<Locale, T> activator) {
		this.locale = locale;
		this.activator = activator;
	}

	@Override
	public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		JsonObject jObject = jsonElement.getAsJsonObject();

		T localizationPackage = activator.apply(locale);

		var groupsByResourceName = Collections.groupBy(jObject.keySet(), LocalizationString::getResourceName);

		for (var nameGroup : groupsByResourceName) {
			LocalizationString localizationString = new LocalizationString(nameGroup.getKey());
			var availableLocales = Collections.toHashTable(nameGroup, LocalizationString::getLocaleId);

			for (var localeId : availableLocales.keySet()) {
				Locale locale = new Locale(localeId);
				String resourceName = availableLocales.get(localeId);
				String resourceString = jObject.get(resourceName).getAsString();

				localizationString.put(locale, resourceString);
			}

			localizationPackage.addLocalizationString(localizationString);
		}

		return localizationPackage;
	}
}
