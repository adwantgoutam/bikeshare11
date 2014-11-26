package com.project11.bikeshare.Service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;




import org.neo4j.cypher.internal.compiler.v2_1.ast.rewriters.isolateAggregation;

import com.project11.bikeshare.Beans.Bikes;
import com.project11.bikeshare.Beans.Coordinates;
import com.project11.bikeshare.DBImpl.BikesDAO;

public class BikesService {
	
	public List<Bikes> getLocations(Coordinates coordinates) throws UnknownHostException
	{
		List<Bikes> list=new BikesDAO().getLocations(coordinates);
		//System.out.println(list.size());
		List<Bikes> newList=new ArrayList<Bikes>();
		for(int i=0;i<list.size();i++)
		{
			Bikes b=list.get(i);
			//System.out.println(b.getIsBikeAvailable());
			if(b.getIsBikeAvailable().equals("yes"))
			{
				newList.add(b);
			}
		}
		return newList;
	}

}
