package application;

import java.util.ArrayList;

public class WareHouse {
	/*
	 * pending.get( OrderID );
	 * pending.get( getOrderBy(id) ).get( item Line );
	 */
	private int whID;												// Unique
	private ArrayList<Cart> pending = new ArrayList<Cart>();		// Fetched from HQ on creation 
	
	public WareHouse( int id )
	{
		this.whID = id;
		this.pending = HeadQuarters.getPending( this.whID );		
	}
	public ArrayList<Cart> getPending() 
	{
		return pending;
	}
	public boolean completeOrder(String OrderId)
	{
		if( HeadQuarters.removePendingOrder( OrderId ) )
		{
			return true; 
		}
		return false;
	}
	public void revalidatePending()
	{
		// testing
		this.pending = HeadQuarters.getPending(this.whID);
	}
	public int getID()
	{
		return this.whID;
	}
	public void pendingAdd(ArrayList<Cart> pending) 
	{
		// testing
		this.pending = pending;
	}
}
