package ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class locale
 */

@Stateful
@LocalBean
public class Locale implements LocaleRemote {
	private String locale;
    public Locale() {   
    	locale="es";
    }

	@Override
	public String getLocale() {	return locale;	}

	@Override
	public void setLocaleEn() {locale="en";}

	@Override
	public void setLocaleEs() {locale="es";}

	@Override
	public void setLocaleDe() {locale="de";}

	@Override
	public void setLocaleZh() {locale="zh";}

	@Override
	public void setLocaleIt() {locale="it";}

	@Override
	public void setLocaleFr() {locale="fr";}

	@Override
	public void setLocale(String localeCode) {locale=localeCode;}
}
