package ca.mattpayne.progresstracker.models;

public class Checkin {
	public final String Longitude;
	public final String Latitude;
	public final String CheckinDate;
	
	public Checkin(String longitude, String latitude, String checkinDate)
	{
		Longitude = longitude;
		Latitude = latitude;
		CheckinDate = checkinDate;
	}
	
	public String getDescription()
	{
		return CheckinDate + "\nLatitude: " + Latitude + "\nLongitude: " + Longitude;
	}
}
