package VideoGame_Discounts;

import java.io.BufferedReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ParseWebsites {

		private String[] redditList;
		private String[] cheapList;
		private String[] gamesdealList;
		private String[] greenList;
		private String[] steamList;
		
		BufferedReader br = null;
	
	    public void parseReddit() 
	    {
	    	try {		
	    		String link = "https://www.reddit.com/r/GameDeals/new/";
	    		Document doc = Jsoup.connect(link).get();	    		
	    		
	    		redditList = new String[doc.select("a.title.may-blank").size()];
	    		
	    		for (int i = 0; i < doc.select("a.title.may-blank").size(); i++)
	    		{
	    			redditList[i] = (doc.select("a.title.may-blank").get(i).text()) + " - submitted " +
	    							(doc.select("time.live-timestamp").get(i).text());
	    		}
	    		
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	
	    }
	    
	    public void parseCheap() 
	    {
	    	
	    	try {
	    		
	    		String link = "https://www.cheapassgamer.com/?app=home&show=all";
	    		Document doc = Jsoup.connect(link).get();	    		

	    		String[] temp1;
	    		String[] real = new String[250];
	    		int count = 0;
	    		temp1 = (doc.toString()).split("\n");
	    		
	    		for (int i = 0; i < temp1.length; i++)
		    	{
		    		if (temp1[i].contains("<div class=\"grid-33 tablet-grid-50 mobile-grid-100 textFeature\">"))
		    			{
		    				// is the grid object marked as popular?
		    				if (temp1[i+4].contains("<i class=\"fa fa-fire popular\"></i>"))
		    				{
		    					String time = (temp1[i + 17].replaceAll("\\s+", " ")).substring(7);
		    					real[count] = ("POPULAR:" + temp1[i+10].replaceAll("\\s+", " ") + time);
		    					count++;
		    				}
		    			
		    				// make sure grid object is not a podcast or something unrelated
		    				else if (temp1[i+3].contains("<div class=\"imageBar feature\" style=\""))
		    				{
		    					continue;
		    				}
		    				
		    				//nothing special in the html code
		    				else
		    				{
		    					String time = (temp1[i + 14].replaceAll("\\s+", " ")).substring(7);
		    					real[count] = (temp1[i+7].replaceAll("\\s+", " ") + time);
		    					count++;
		    				}
		    				
		    			}
		    	}
	    		
	    		cheapList = new String[count];
	    		int i = 0; 
	    		
	    		for (String e : real)
	    		{	
	    			if (e != null)
	    				cheapList[i++] = e;
	    		}
	    		
	    		real = null;
	    		
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    public void parseGamesdeal() 
	    {
	    	
	    	try {
	    		
	    		String link = "https://www.gamesdeal.com/game-deals.html";
	    		Document doc = Jsoup.connect(link).get();	    		
	    		
	    		String[] real = new String[250];
	    		int count = 0;

	    		
	    		real[count++] = "---------- Daily Deal ----------";
	   
	    		// Get Daily Deal and Time-Limited Deals
	    		for (int i = 0; i < doc.select("h2.product-name").size(); i++)
	    		{	
	    			String temp = doc.select("h2.product-name").get(i).text() + ", ";
    				temp += doc.select("p.special-price").get(i).text().substring(14) + " (";
    				temp += doc.select("span.discount-count").get(i).text() + ")";
    				real[count++] = temp;
    				
    				// Record Daily Deal
	    			if (count == 2)
	    			{
	    				real[count++] = "";
	    				real[count++] = "---------- Time-Limited Deals ----------";
	    			}
	    		}
	    		
	    		real[count++] = "";
				real[count++] = "---------- Top 3 Best Sellers ----------";				
	    		
	    		link = "https://www.gamesdeal.com/best-seller.html";
	    		doc = Jsoup.connect(link).get();
	    		
	    		// Record No. 1 in Top 3
	    		String temp = "No. 1: " + doc.select("h1").get(0).text() + ", " + 
	    					  doc.select("div.BS-num_1-right p").get(0).text() + " (" + 
	    					  doc.select("div.BS-num_1-right-discount").get(0).text() + ")";
	    		real[count++] = temp;
	    		
	    		// Record No. 2 in Top 3
	    		temp = "No. 2: " + doc.select("h1").get(1).text() + ", " + 
	    			   doc.select("div.BS-num_2-price p").get(0).text() + " (" + 
  					   doc.select("div.BS-num_2-price i").get(0).text() + ")";
	    		real[count++] = temp;
	    		
	    		// Record No. 3 in Top 3
	    		temp = "No. 3: " + doc.select("h1").get(2).text() + ", " + 
	    			   doc.select("div.BS-num_2-price p").get(1).text() + " (" + 
	  				   doc.select("div.BS-num_2-price i").get(1).text() + ")";
	    		real[count++] = temp;
	    		
	    		real[count++] = "";
	    		real[count++] = "---------- Other Bestsellers ----------";
	    		
	    		// Record the rest of the deals on the page
	    		for (int i = 0; i < doc.select("div.BS-footer-proList").size(); i++)
	    		{
	    			temp = doc.select("h2").get(i).text() + ", " +
	    				   doc.select("div.BS-pro-pri-inner p").get(i).text() + " (" +
	    				   doc.select("div.BS-footer-discou").get(i).text() + ")";
	    			real[count++] = temp;
	    		}
	    		
	    		gamesdealList = new String[count];
	    		int i = 0; 
	    		
	    		for (String e : real)
	    		{	
	    			if (e != null)
	    				gamesdealList[i++] = e;
	    		}
	    		
	    		real = null;
	    		
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	
	    }
	    
	    public void parseGreen() 
	    {
	    	try {		
	    		String link = "https://www.greenmangaming.com/deal-of-the-day/";
	    		Document doc = Jsoup.connect(link).get();	    		
	    		
	    		int count = 0;
	    		String[] temp1 = new String[250];
	    		
	    		// Get Deal of the Day
	    		temp1[count++] = "---------- Deal of the Day ----------";
	    		String temp = doc.select("h2.prod-name.notranslate").get(0).text() + ", " +
	    					  doc.select("div.col-xs-10.col-sm-12.prod-details span.current-price").get(0).text() + " (" + 
	    					  doc.select("div.col-xs-2.col-sm-3.col-md-2.discount p").get(0).text() + ")";
	    		temp1[count++] = temp;
	    		temp1[count++] = "";
	    		temp1[count++] = "---------- Hot Deals ----------";
	    		
	    		link = "https://www.greenmangaming.com/hot-deals/";
	    		doc = Jsoup.connect(link).get();	
	    		
	    		for (int i = 0; i < doc.select("div.break-word").size(); i++)
	    		{
	    			 Element tempDoc = doc.select("div.break-word").get(i);
	    			 String yes = tempDoc.toString();
	    			 
	    			 // get deal blocks we want
	    			 if (yes.contains("<section class=\"product-block medium-product-block\">") ||
	    				 yes.contains("<section class=\"new-games\">"))
	    			 {
	    					 System.out.println("valid1");
	    					 for (int j = 0; j < tempDoc.select("h3.prod-name.notranslate").size(); j++)
	    					 {
	    						 temp = tempDoc.select("h3.prod-name.notranslate").get(j).text() + ", " +
	    								tempDoc.select("span.current-price").get(j).text() + " (" +
	    								tempDoc.select("div.col-xs-3.discount p").get(j).text() + ")";
	    						 System.out.println(temp);
	    						 temp1[count++] = temp;

	    					 }
	    			 }
	    			 
	    			 // Record the deals from items on the carousel (at the bottom of the page) 
	    			 else if (yes.contains("<section class=\"mini-carousel\">"))
	    			 {
	    				 temp1[count++] = "";
	    				 temp1[count++] = "---------- Other Deals ----------";
	    				 for (int j = 0; j < tempDoc.select("div.col-xs-2.module").size(); j++)
    					 {
    						 temp = tempDoc.select("h3.prod-name.notranslate").get(j).text() + ", " +
    								tempDoc.select("span.current-price").get(j).text() + " (" +
    								tempDoc.select("div.col-xs-4.discount p").get(j).text() + ")";
    						 temp1[count++] = temp;

    					 }
	    			 }
	    			 
	    		}
	    		
	    		greenList = new String[count];
	    		int i = 0; 
	    		
	    		for (String e : temp1)
	    		{	
	    			if (e != null)
	    				greenList[i++] = e;
	    		}
	    		
	    		temp1 = null;
	    		
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	
	    }
	    
	    public void parseSteam()
	    {
	    	try {	    		
    		
	    		int count = 0;
	    		String[] temp1 = new String[250];
	    		
	    		temp1[count++] = "---------- Top 100 Relevant Specials on Steam ----------";
	    		
	    		// loop 4 times to get items from the first 4 pages
	    		for (int i = 0; i < 4; i++)
	    		{
		    		String link = "http://store.steampowered.com/search/?specials=1&page=" + (i + 1);
		    		Document doc = Jsoup.connect(link).get();
	    			
		    		// Record the items from each page (25 per page)
		    		for (int j = 0; j < doc.select("div.col.search_price.discounted.responsive_secondrow").size(); j++)
		    		{
		    			String temp2 = doc.select("div.col.search_price.discounted.responsive_secondrow").get(j).text();
		    			String temp = "No. " + (count) + ": " + doc.select("span.title").get(j).text() + ", " +
		    						  (doc.select("div.col.search_price.discounted.responsive_secondrow").get(j).text()).substring(temp2.indexOf(".") + 4) + " (" +
		    						  doc.select("div.col.search_discount.responsive_secondrow").get(j).text() + ")";
		    			temp1[count++] = temp;
		    		}
		    		
	    		}
	    		
	    		steamList = new String[count];
	    		int i = 0; 
	    		
	    		for (String e : temp1)
	    		{	
	    			if (e != null)
	    				steamList[i++] = e;
	    		}
	    		
	    		temp1 = null;

	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    public String[] getReddit()
		{
			return redditList;
		}
	    
	    public String[] getCheap()
		{
			return cheapList;
		}
	    
	    
	    public String[] getGamesdeal()
	    {
	    	return gamesdealList;
	    }
	    
	    
	    public String[] getGreen()
	    {
	    	return greenList;
	    }
	    
	    public String[] getSteam()
		{
			return steamList;
		}
	    
}