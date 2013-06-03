package ca.mattpayne.progresstracker.models;

public class Checkin {
	public String Longitude;
	public String Latitude;
	public String CheckinDate;
	
	public String getDescription()
	{
		return CheckinDate + "\nLatitude: " + Latitude + "\nLongitude: " + Longitude;
	}
}
