package dev.vrsek.localization.generator;

import dev.vrsek.localization.LocalizationString;
import dev.vrsek.source.builders.model.AccessModifier;
import dev.vrsek.utils.IBuilder;
import dev.vrsek.utils.JavaSourceFormatter;
import dev.vrsek.utils.StringUtils;
import dev.vrsek.utils.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LocalizationPackageSourceBuilder implements IBuilder<String> {
	private final String packageName;
	private final LocalizationPackage localizationPackage;

	public LocalizationPackageSourceBuilder(String packageName, LocalizationPackage localizationPackage){
		this.packageName = packageName;
		this.localizationPackage = localizationPackage;
	}

	@Override
	public String build() throws ValidationException {
		JavaClassSourceBuilder builder = new JavaClassSourceBuilder(new JavaSourceFormatter(), new AccessModifierToStringMapper());
		builder.setClassName(StringUtils.capitalize(packageName));
		builder.addImports("LocalizationManager");

		for (var localizationString : localizationPackage.getLocalizationStrings().values()) {
			builder.addMembers(createMembersFor(localizationString));
		}

		return builder.build();
	}

	private Collection<IMemberSourceBuilder> createMembersFor(LocalizationString localizationString) {
		List<IMemberSourceBuilder> builders = new ArrayList<>();
		String resourceName = localizationString.getResourceName();

		JavaFieldSourceBuilder fieldSourceBuilder = new JavaFieldSourceBuilder(new AccessModifierToStringMapper());
		fieldSourceBuilder.setAccessModifier(AccessModifier.PRIVATE);
		fieldSourceBuilder.setTypeName("String");
		fieldSourceBuilder.setName(resourceName);
		builders.add(fieldSourceBuilder);

		JavaMethodSourceBuilder methodSourceBuilder = new JavaMethodSourceBuilder(new AccessModifierToStringMapper());
		methodSourceBuilder.setAccessModifier(AccessModifier.PUBLIC);
		methodSourceBuilder.setTypeName("String");
		methodSourceBuilder.setName("get" + resourceName);
		methodSourceBuilder.setBody(new String[] {
				String.format("return LocalizationManager.get().getString(\"%s\");", packageName + "." + resourceName)
		});
		builders.add(methodSourceBuilder);

		return builders;
	}

	public String getPackageName() {
		return packageName;
	}
}
