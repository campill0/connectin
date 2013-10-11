package ejb;

import javax.ejb.Remote;

@Remote
public interface LocaleRemote {
	public String getLocale();
	public void setLocaleEn();
	public void setLocaleEs();
	public void setLocaleDe();
	public void setLocaleZh();
	public void setLocaleIt();
	public void setLocaleFr();
	public void setLocale(String localeCode);

}
