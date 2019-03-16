package dev.vrsek.localization.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.vrsek.localization.Locale;
import dev.vrsek.localization.LocalizationPackageHashtable;
import dev.vrsek.localization.json.LocalizationJsonDeserializer;
import dev.vrsek.localization.json.LocalizationPackageJsonDeserializer;
import dev.vrsek.utils.StringUtils;
import dev.vrsek.utils.exceptions.ValidationException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LocalizationGenerator {
	public static void main(String[] args) throws ValidationException, IOException {
		String inputJson = "{\n" +
				"  \"errors\": {\n" +
				"    \"cz.fileNotFoundError\": \"Soubor nenalezen.\",\n" +
				"    \"en.fileNotFoundError\": \"File was not found.\",\n" +
				"\n" +
				"    \"cz.invalidFile\": \"Neplatny soubor\",\n" +
				"    \"en.invalidFile\": \"Invalid file\"\n" +
				"  },\n" +
				"  \"files\": {\n" +
				"    \"cz.filename\": \"Nazev souboru\",\n" +
				"    \"en.filename\": \"File name\"\n" +
				"  }\n" +
				"}";
		String outputDirectory = "C:\\Dev\\java-tester\\src\\main\\java\\dev\\vrsek\\javatester\\resources\\";

		Localization localization = deserializeJson(inputJson);
		var sourceBuilders = createSourceBuilder(localization.getPackages());
		writeSources(outputDirectory, sourceBuilders);
	}

	private static void writeSources(String directoryPath, Collection<LocalizationPackageSourceBuilder> builders) throws IOException, ValidationException {
		for (var builder : builders) {
			String path = Paths.get(directoryPath, StringUtils.capitalize(builder.getPackageName()) + ".java").toString();

			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(builder.build());

			writer.close();
		}

	}

	private static Collection<LocalizationPackageSourceBuilder> createSourceBuilder(LocalizationPackageHashtable packages) throws ValidationException {
		List<LocalizationPackageSourceBuilder> sourceBuilders = new ArrayList<>();

		for (var pckgName : packages.keySet()) {
			LocalizationPackageSourceBuilder builder =
					new LocalizationPackageSourceBuilder(pckgName, (LocalizationPackage)packages.get(pckgName));

			sourceBuilders.add(builder);
		}

		return sourceBuilders;
	}

	private static Localization deserializeJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeHierarchyAdapter(
				dev.vrsek.localization.Localization.class,
				new LocalizationJsonDeserializer<>(Localization::new)
		);
		gsonBuilder.registerTypeHierarchyAdapter(
				dev.vrsek.localization.LocalizationPackage.class,
				new LocalizationPackageJsonDeserializer<>(Locale.DEFAULT, LocalizationPackage::new)
		);

		Gson gson = gsonBuilder.create();
		return gson.fromJson(json, Localization.class);
	}
}
