package dev.vrsek.localization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.vrsek.localization.*;

public class JsonLocalizationLoader implements ILocalizationLoader {
	private final String json;

	public JsonLocalizationLoader(String json) {
		this.json = json;
	}

	@Override
	public void load(Locale locale) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Localization.class, new LocalizationJsonDeserializer<>(Localization::new));
		gsonBuilder.registerTypeAdapter(LocalizationPackage.class, new LocalizationPackageJsonDeserializer<>(locale, LocalizationPackage::new));

		Gson gson = gsonBuilder.create();
		Localization localization = gson.fromJson(json, Localization.class);

		LocalizationManager.initialize(localization);
	}
}
