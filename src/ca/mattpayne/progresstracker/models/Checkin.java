package ca.mattpayne.progresstracker.models;

public class Checkin {
	public final String Longitude;
	public final String Latitude;
	public final String CheckinDate;
	private final String _description;
	
	public Checkin(String longitude, String latitude, String checkinDate)
	{
		Longitude = longitude;
		Latitude = latitude;
		CheckinDate = checkinDate;
		_description = CheckinDate + "\nLatitude: " + Latitude + "\nLongitude: " + Longitude;
	}
	
	public String getDescription()
	{
		return _description;
	}
}
