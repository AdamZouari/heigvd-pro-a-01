package locale;

import java.util.Locale;

public final class I18N {

    public final static Locale EN = new Locale("en", "EN");
    public final static Locale FR = new Locale("fr", "FR");

    private static Locale selected = EN;

    public static void setLocale(Locale locale) {
        selected = locale;
    }

    public static Locale getLocale() {
        return selected;
    }
}
