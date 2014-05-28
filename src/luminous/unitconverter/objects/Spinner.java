package luminous.unitconverter.objects;

public class Spinner {
	public Spinner(String ID, String title, boolean isDownloaded) {
		this.ID = ID;
		this.title = title;
		this.isDownloaded = isDownloaded;
	}

	public Spinner(String ID, String title) {
		this.ID = ID;
		this.title = title;
	}

	public Spinner(String title) {
		this.title = title;
	}

	public String ID;
	public String title;
	public boolean isDownloaded;
}
