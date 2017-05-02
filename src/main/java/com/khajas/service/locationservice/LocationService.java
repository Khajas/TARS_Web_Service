package com.khajas.service.locationservice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;

// Will get the user location based of the request from an ip address
public class LocationService extends ApiCall{
	private String userIP;
	private String userCity;
	private String userCountry;
	private String zipcode;
	private String countryCode;
	private String userState;
	private String lat, lng;
	public LocationService(String ipaddress){
			this.userIP=ipaddress;
			this.findCity(this.userIP);
			super.addIntent("what's my location", "locationservice","You're location in ");
			super.addIntent("my location", "locationservice","It seems you're in ");
			super.addIntent("where am I now", "locationservice","I guess you're at ");
			super.addIntent("where am I located", "locationservice","I guess you're at ");
			super.print_commands();
	}
	/**
	 * This method accepts ip address of the user to determine location
	 * what if there are many users acccess the api ?
	 * @param ipaddress
	 * @return user city and country
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public void findCity(String ipaddress){
        URL url;
		try {
			url = new URL("https://ipinfo.io/"+URLEncoder.encode(ipaddress,"UTF-8")
							+"/json");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			this.userCity=this.userCountry="unknown";
			return;
		}
		JSONObject json;
		try {
			json = super.callApi(url);
		} catch (IOException | JSONException e) {
			this.userCity=this.userCountry="unknown";
			return;
		}
		try {
			this.userCity=json.getString("city");
			this.userCountry=json.getString("country");
			this.zipcode=json.getString("postal");
			this.countryCode=json.getString("country");
			this.userState=json.getString("region");
			String [] loc=json.getString("loc").split(",");
			this.lat=loc[0];
			this.lng=loc[1];
		} catch (JSONException e) {
			this.userCity=this.userCountry="unknown";
		}
	}
	@Override
	public String serve(String append){
		findCity(this.userIP);
		return append+this.userCity+", "+this.userState+" "+this.userCountry+".";
	}
	
	// GETTERS FOR ALL THE PARAMETERS
	
	/**
	 * This method returns the city in which the user is living
	 * @return userCity
	 */
	public String getUserCity(){
		return this.userCity;
	}
	/**
	 * This method returns the zipcode in which the user is living
	 * @return zipcode
	 */
	public String getZipcode(){
		return this.zipcode;
	}
	/**
	 * This method returns the countrycode in which the user is living
	 * @return countrycode
	 */
	public String getCountryCode(){
		return this.countryCode;
	}
	/**
	 * This method returns the userState in which the user is living
	 * @return userState
	 */
	public String getUserState(){
		return this.userState;
	}
	/**
	 * This method returns the country in which the user is living
	 * @return userCountry
	 */
	public String getUserCountry(){
		return this.userCountry;
	}
	/**
	 * This method returns the lattitue in which the user is living
	 * @return userCity
	 */
	public String getUserLat(){
		return this.lat;
	}
	/**
	 * This method returns the longitude in which the user is living
	 * @return userCity
	 */
	public String getUserLng(){
		return this.lng;
	}
}
/** Sample response
{
	"ip": "2601:242:4000:be10:1acf:5eff:fedc:9a68",
	"hostname": "No Hostname",
	"city": "Sycamore",
	"region": "Illinois",
	"country": "US",
	"loc": "42.0106,-88.6671",
	"org": "AS7922 Comcast Cable Communications, LLC",
	"postal": "60178"
}
*/
