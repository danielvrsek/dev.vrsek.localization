package dev.vrsek.localization;

import dev.vrsek.localization.exceptions.LocalizationPackageNotFoundException;
import dev.vrsek.utils.DictionaryCacheService;
import dev.vrsek.utils.ICacheService;

import java.util.StringTokenizer;

public class Localization implements ILocalizationSource {
	private LocalizationPackageHashtable packages;

	private ICacheService<LocalizationPackage> cacheService;

	public Localization(LocalizationPackageHashtable packages) {
		this.packages = packages;

		this.cacheService = new DictionaryCacheService<>();
	}

	@Override
	public String getString(String key) {
		LocalizationPackage localizationPackage = cacheService.get(key);

		if (localizationPackage == null) {
			localizationPackage = find(key);
		}

		assert localizationPackage != null;

		return localizationPackage.getString(key);
	}

	protected LocalizationPackageHashtable getPackages() {
		return packages;
	}

	private LocalizationPackage find(String key) {
		StringTokenizer tokenizer = new StringTokenizer(key, ".");
		String packageName = tokenizer.nextToken();

		LocalizationPackage localizationPackage = packages.get(packageName);

		if (localizationPackage == null) {
			throw new LocalizationPackageNotFoundException(packageName);
		}

		return localizationPackage;
	}
}
