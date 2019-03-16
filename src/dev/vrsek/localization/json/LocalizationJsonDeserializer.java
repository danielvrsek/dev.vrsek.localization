package dev.vrsek.localization.json;

import com.google.gson.*;
import dev.vrsek.localization.Localization;
import dev.vrsek.localization.LocalizationPackage;
import dev.vrsek.localization.LocalizationPackageHashtable;

import java.lang.reflect.Type;
import java.util.function.Function;

public class LocalizationJsonDeserializer<T extends Localization> implements JsonDeserializer<T> {
	private final Function<LocalizationPackageHashtable, T> activator;

	public LocalizationJsonDeserializer(Function<LocalizationPackageHashtable, T> activator) {
		this.activator = activator;
	}

	@Override
	public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		LocalizationPackageHashtable hashtable = new LocalizationPackageHashtable();

		JsonObject jObject = jsonElement.getAsJsonObject();
		for (String packageName : jObject.keySet()) {
			LocalizationPackage localizationPackage = jsonDeserializationContext.deserialize(jObject.get(packageName), LocalizationPackage.class);
			hashtable.put(packageName, localizationPackage);
		}

		return activator.apply(hashtable);
	}
}
