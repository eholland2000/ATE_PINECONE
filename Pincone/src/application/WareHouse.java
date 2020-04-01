package application;

import java.util.ArrayList;

public class WareHouse {
	/*
	 * pending.get( OrderID );
	 * pending.get( getOrderBy(id) ).get( item Line );
	 */
	private int whID;
	private ArrayList<Cart> pending = new ArrayList<Cart>();
	
	public WareHouse( int id )
	{
		this.whID = id;
	}
	public WareHouse( int id, ArrayList<Cart> pending )
	{
		/*
		 * pending orders was already created
		 */
		this.whID = id;
		pendingAdd(pending);
	}
	public ArrayList<Cart> getPending() 
	{
		return pending;
	}
	public boolean completeOrder(String OrderId)
	{
		int index = 0;
		for( Cart c : this.pending )
		{
			if( c.getOrderID().equals(OrderId) )
			{
				HeadQuarters.removePendingOrder( c.getOrderID() );
				this.pending.remove(index);
				return true;
			}
			index++;
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
