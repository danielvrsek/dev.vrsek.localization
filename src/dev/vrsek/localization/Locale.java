package dev.vrsek.localization;

public class Locale {
	public static final Locale CZECH = new Locale("cz");
	public static final Locale ENGLISH = new Locale("en");

	public static final Locale DEFAULT = new Locale("@DEFAULT");

	private String identifier;

	public Locale(String identifier){
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return identifier;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Locale)) {
			return false;
		}
		Locale locale = (Locale)obj;
		return locale.identifier.equals(identifier);
	}

	@Override
	public int hashCode() {
		return identifier.hashCode();
	}
}
