package application;

public class User {
	private String UID = "";				// user login
	private String password = "";			// users password	( instance only )
	String name = "not defined";
	
	
	public User(String login, String password)
	{
		this.UID      = login;
		/*
		 * HQ-XXX	is HQ login
		 * SM-XXX	is Store manger
		 * WM-XXX	is Warehouse manager
		 * E-XXX	is Employee login
		 */
		this.password = password;
		
	}
}
