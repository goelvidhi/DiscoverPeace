package com.anv.discover.peace.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.anv.discover.peace.StressList;
import com.anv.discover.peace.R;
import com.anv.discover.peace.StressSolution;

import com.anv.discover.peace.stressbean.Emotional;
import com.anv.discover.peace.stressbean.Environmental;
import com.anv.discover.peace.stressbean.Mental;
import com.anv.discover.peace.stressbean.Nutritional;
import com.anv.discover.peace.stressbean.Physical;
import com.anv.discover.peace.stressbean.PsychoSpiritual;
import com.anv.discover.peace.stressbean.StressType;
import com.anv.discover.peace.stressbean.Traumatic;


import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;


public class XMLAdapter {
	
	
	private Context mActivityContext;
	private final String LOG_TAG = "DP: XML Adapter";
	public static int RECEIVE_STRESS_TYPE = 1;
	public static int RECEIVE_STRESS_SUMMARY = 2;
	public static final int RECEIVE_SOLUTION = 3;
	
	private XmlResourceParser causeXml;
	private XmlResourceParser solutionXml;
	private XmlResourceParser eulaXml;
	
	StressSolution solutionInstance;
	private String summary;
	private ArrayList<String> solution_ids;
	private ArrayList<String> solutions_title;
	private ArrayList<String> solutions;
	
	
	
	public XMLAdapter(Context context) {
		// TODO Auto-generated constructor stub
		if (context.getClass().getSimpleName().equals("StressList"))
		initStressCause(context);
		else if (context.getClass().getSimpleName().equals("StressSolution"))
		initStressSolution(context);
		else if (context.getClass().getSimpleName().equals("Main"))
		initEula(context);
		
		
	}

	
	private void initEula(Context context) {
		// TODO Auto-generated method stub
		mActivityContext = context;
		eulaXml = mActivityContext.getResources().getXml(R.xml.eula_content);
	}


	private void initStressCause(Context context) {
		// TODO Auto-generated method stub
		mActivityContext = context;
		causeXml = mActivityContext.getResources().getXml(R.xml.problem_set);
		 
		
	}
	
	private void initStressSolution(Context context) {
		// TODO Auto-generated method stub
		mActivityContext = context;
		causeXml = mActivityContext.getResources().getXml(R.xml.problem_set);
		solutionXml = mActivityContext.getResources().getXml(R.xml.solution_set);
		summary = null;
		solution_ids = new ArrayList<String>();
		solutions_title = new ArrayList<String>();
		solutions = new ArrayList<String>();	
	}

	
	
	
	
	public String readEula() {
		// TODO Auto-generated method stub
		
		String eula = null;
		
		eula  = "Disclaimer" + readEulaTag("disclaimer") + '\n';
		eula += "PrivacyPolicy" + readEulaTag("privacypolicy") + '\n';
		eula += "Links" + readEulaTag("links") + '\n';
		eula += "Acceptance" + readEulaTag("acceptance") + '\n';

		
		return eula;
		}
	
	
	private String readEulaTag(String tag)
	{
		
		String nodeElement = null;
		String nodeValue = null;
		
		
		try{
		while(eulaXml.getEventType() != XmlPullParser.START_TAG)
			eulaXml.next();
			
		nodeElement = eulaXml.getName();
		
	
		while(nodeElement != null && !nodeElement.equals(tag) )
			{
			 eulaXml.next();
			 if(eulaXml.getEventType()== XmlPullParser.START_TAG)
			 nodeElement = eulaXml.getName();
			}
	
				eulaXml.next();
	
		if(eulaXml.getEventType()== XmlPullParser.TEXT)
					{
						nodeValue = eulaXml.getText();
					}
		
		}
		catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//Get next parse event
		
		return nodeValue;
		
	}
	
	public void readCause()
	{
		readCauseThread.start();
	}

	Thread readCauseThread = new Thread(new Runnable() {
		
		public void run() {
			// TODO Auto-generated method stub
		
			readCauseFromXml();
		}
	});
	
	
	
	public void readCauseFromXml() 
	{
		StressList listInstance = (StressList)mActivityContext;
		
		
		
		// TODO Auto-generated method stub
		
		StressType stressType = null;
		String nodeElement = null;
		String nodeValue = null;
		
		boolean isGroupElement = false;
		boolean isChildElement = false;
		String stressClass = null;
		String stressCause = null;
		
		try {
			causeXml.next();
			int eventType = causeXml.getEventType(); //Get current xml event i.e., START_DOCUMENT
			
		while(eventType!= XmlPullParser.END_DOCUMENT)
			{
				if(eventType == XmlPullParser.START_TAG)
				{
					nodeElement = causeXml.getName();
										
					if(nodeElement.equals("stress-class")) isGroupElement = true;
					if(nodeElement.equals("stress-cause")) isChildElement = true;
						
					
					// Enter inside the stress-class element
					if(nodeElement != null && (isGroupElement || isChildElement))
					{
						// Iterate till the START_TAG equals the "title"
						while(!nodeElement.equals("title") )
						{
							causeXml.next();
							if(causeXml.getEventType()== XmlPullParser.START_TAG)
							nodeElement = causeXml.getName();
						}
		
						nodeElement = causeXml.getName();
			//   The causeXml currently points to the title start_tag. Go next to read the text
						causeXml.next();
			// Read the text inside "title" and save to the stressclass arraylist.
						if(causeXml.getEventType()== XmlPullParser.TEXT)
						{
							nodeValue = causeXml.getText();
							
							if(isGroupElement)
							{
							stressClass = nodeValue;
							isGroupElement = false;
							}
							else if(isChildElement)
							{
								if(nodeElement.equals("title"))
									stressCause = nodeValue;
							
									stressType = setData(stressClass, stressCause);
									
									Message msg = new Message();
									msg.obj = stressType;
									msg.what = RECEIVE_STRESS_TYPE;
									listInstance.handler.sendMessage(msg);
																
									isChildElement = false;	
							}// end of isChildElement
						}// end of if element is text
					}	
				}// end of START_TAG event type
				
				else if (eventType == XmlPullParser.END_DOCUMENT)
				{
					break;
				}
				eventType = causeXml.next();
			}
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//Get next parse event
		
	}

	private StressType setData(String stressClass, String stressCause) 
	{
		// TODO Auto-generated method stub
		StressType stressType = null;
		
		if("Physical".equalsIgnoreCase(stressClass))
		{
			stressType = new Physical(stressCause);
			stressType.setStressGroup(stressClass);
			
		}
		
		else if("Mental".equalsIgnoreCase(stressClass))
		{
			stressType = new Mental(stressCause);
			stressType.setStressGroup(stressClass);
		
		}
		else if("Emotional".equalsIgnoreCase(stressClass))
		{
			stressType = new Emotional(stressCause);
			stressType.setStressGroup(stressClass);
		
		}
		else if("Nutritional".equalsIgnoreCase(stressClass))
		{
			stressType = new Nutritional(stressCause);
			stressType.setStressGroup(stressClass);
		
		}
		else if("Traumatic".equalsIgnoreCase(stressClass))
		{
			stressType = new Traumatic(stressCause);
			stressType.setStressGroup(stressClass);
		
		}
		else if("Psycho-spiritual".equalsIgnoreCase(stressClass))
		{
			stressType = new PsychoSpiritual(stressCause);
			stressType.setStressGroup(stressClass);
		
		}
		
		else if("Environmental".equalsIgnoreCase(stressClass))
		{
			stressType = new Environmental(stressCause);
			stressType.setStressGroup(stressClass);
		
		}
		
		log(stressType.toString());
	return stressType;
	}
	
	private String stressCauseFromList = null;
	public void findSolution(String stressCause)
	{
		readSolutionThread.start();
		stressCauseFromList = stressCause;
	}

	Thread readSolutionThread = new Thread(new Runnable() {
		
	
		public void run() {
			// TODO Auto-generated method stub
		
			findSolutionFromXml(stressCauseFromList);
		}
	});
	
	
	public void findSolutionFromXml(String stressCause)
	{
		
		solutionInstance = (StressSolution)mActivityContext;
		readStressSummary(stressCause);
		readSolution();
	}
	

	

	private void readStressSummary(String stressCause) {
		
		
		
		String nodeElement = null;
		String nodeValue = null;
		
		try {
			causeXml.next();
			int eventType = causeXml.getEventType(); //Get current xml event i.e., START_DOCUMENT
			
		while(eventType!= XmlPullParser.END_DOCUMENT)
			{
				if(eventType == XmlPullParser.START_TAG)
				{
					nodeElement = causeXml.getName();
					
					// go inside the stress cause tag.
					if(nodeElement.equals("stress-cause"))
					{
						// read the xml title
						while(!nodeElement.equals("title") )
						{
							causeXml.next();
							if(causeXml.getEventType()== XmlPullParser.START_TAG)
							nodeElement = causeXml.getName();
						}
			//   The causeXml currently points to the title start_tag. Go next to read the text
						causeXml.next();

						if(causeXml.getEventType()== XmlPullParser.TEXT)
						{
							nodeValue = causeXml.getText();
						
			// compare the xmltitle with the clicked item cause			
				if (nodeValue.equals(stressCause))
					{
					// read the summary for matched XML cause
						while(!nodeElement.equals("summary") )
						{
							causeXml.next();
							if(causeXml.getEventType()== XmlPullParser.START_TAG)
							nodeElement = causeXml.getName();
						}
						causeXml.next();

						if(causeXml.getEventType()== XmlPullParser.TEXT)
						{
							nodeValue = causeXml.getText();
							
							summary = nodeValue;
							
							Message msg = new Message();
							msg.obj = summary;
							msg.what = RECEIVE_STRESS_SUMMARY;
							solutionInstance.handler.sendMessage(msg);
							
						}
										
					// reach the start tag of solution-ref
						while(!nodeElement.equals("solution-ref") )
						{
							causeXml.next();
							if(causeXml.getEventType()== XmlPullParser.START_TAG)
							nodeElement = causeXml.getName();
						}
						
			//   The causeXml currently points to the title start_tag.
						causeXml.next();
						nodeElement = causeXml.getName();
						
			// Continue to read text tags till the end of solution-ref is reached
						while(!nodeElement.equals("solution-ref"))
						{
						if(causeXml.getEventType()== XmlPullParser.TEXT)
						{
							nodeValue = causeXml.getText();
							solution_ids.add(nodeValue);
							
						}
						
						causeXml.next();
						if(causeXml.getEventType()== XmlPullParser.END_TAG)
							nodeElement = causeXml.getName();
						
					}
					} // nodevalue is compared with passed stresscause
				}// text tag was read
					
				}// nodeelement is stress-cause
				}// start-tag event
				
				else if (eventType == XmlPullParser.END_DOCUMENT)
				{
					break;
				}
				eventType = causeXml.next();
			}
			
		} 
		catch (XmlPullParserException e) {e.printStackTrace();}
		catch (IOException e) {	e.printStackTrace(); }
	}
	
	private void readSolution() 
	{
		
		Iterator<String> solutionIterator = solution_ids.iterator();
		
		while(solutionIterator.hasNext())
		{
			String solution_id = solutionIterator.next();
			
			readSolutionFromXml(solution_id);
			
		}
		
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putStringArrayList("solution_title", solutions_title);
		data.putStringArrayList("solutions", solutions);
		msg.setData(data);
		msg.what = RECEIVE_SOLUTION;
		solutionInstance.handler.sendMessage(msg);
		
		
			
	}

	private void readSolutionFromXml(String solutionId) 
	{
		String nodeElement = null;
		String nodeValue = null;
		boolean isGroupElement = false;
		boolean isChildElement = false;
		String parentId = null;
		String childId = null;
		String currentId = null;
		
		String title = null;
		String solution = null;
		
		resetSolutionXml();
		// TODO Auto-generated method stub
		
					
		try {
			solutionXml.next();
			int eventType = solutionXml.getEventType(); //Get current xml event i.e., START_DOCUMENT
			
		while(eventType!= XmlPullParser.END_DOCUMENT)
			{
				if(eventType == XmlPullParser.START_TAG)
				{
					nodeElement = solutionXml.getName();
					
					if(nodeElement.equals("solution-class")) isGroupElement = true;
					if(nodeElement.equals("solution")) isChildElement = true;
						
					
					// Enter inside the stress-class element
					if(nodeElement != null && (isGroupElement || isChildElement))
					{
						// Iterate till the START_TAG equals the "title"
						while(!nodeElement.equals("id") )
						{
							solutionXml.next();
							if(solutionXml.getEventType()== XmlPullParser.START_TAG)
							nodeElement = solutionXml.getName();
						}
		
			//   The solutionXml currently points to the title start_tag. Go next to read the text
						solutionXml.next();
			// Read the text inside "title" and save to the stressclass arraylist.
						if(solutionXml.getEventType()== XmlPullParser.TEXT)
						{
							nodeValue = solutionXml.getText();
							
							if(isGroupElement)
							{
							parentId = nodeValue;
							isGroupElement = false;
							}
							else if(isChildElement)
							{
							childId = nodeValue;
							
							currentId = parentId + "#" + childId;
							
							
							isChildElement= false;
		
							if(solutionId.equals(currentId))
					
								{
						
									while(!nodeElement.equals("title") )
									{
							solutionXml.next();
							if(solutionXml.getEventType()== XmlPullParser.START_TAG)
							nodeElement = solutionXml.getName();
									}
		
		//   The causeXml currently points to the title start_tag. Go next to read the text
						solutionXml.next();
			// Read the text inside "title" and save to the stressclass arraylist.
						if(solutionXml.getEventType()== XmlPullParser.TEXT)
						{
							nodeValue = solutionXml.getText();
							title = nodeValue;
				
							solutions_title.add(title);
							
							
						}
												
						while(!nodeElement.equals("summary") )
						{
							solutionXml.next();
							if(solutionXml.getEventType()== XmlPullParser.START_TAG)
							nodeElement = solutionXml.getName();
						}
		
		//   The causeXml currently points to the title start_tag. Go next to read the text
						solutionXml.next();
			// Read the text inside "title" and save to the stressclass arraylist.
						if(solutionXml.getEventType()== XmlPullParser.TEXT)
						{
							nodeValue = solutionXml.getText();
							solution = nodeValue;
					
							solutions.add(solution);
						}
						
						
						
					}// we are inside matching condition of ids tag
							
			}// if child element
		}// text tag was read
		}// node elemnet was not null and either child or parent
							
					
				}// end of start tag
				
				else if (eventType == XmlPullParser.END_DOCUMENT)
				{
					break;
				}
				eventType = solutionXml.next();
			}
		
		}// end of try
		catch (XmlPullParserException e) {e.printStackTrace();}
		catch (IOException e) {	e.printStackTrace(); }
			
	}

	private void resetSolutionXml() 
	{
		// TODO Auto-generated method stub
		solutionXml.close();
		solutionXml = mActivityContext.getResources().getXml(R.xml.solution_set);
	}

/*	private boolean solutionIdMatches(String solutionId) {
		String nodeElement = null;
		String nodeValue = null;
		boolean isGroupElement = false;
		boolean isChildElement = false;
		String parentId = null;
		String childId = null;
		String currentId = null;
		
		try {
			resetSolutionXml();
			solutionXml.next();
			int eventType = solutionXml.getEventType(); //Get current xml event i.e., START_DOCUMENT
			
		while(eventType!= XmlPullParser.END_DOCUMENT)
			{
				if(eventType == XmlPullParser.START_TAG)
				{
					nodeElement = solutionXml.getName();
					
					if(nodeElement.equals("solution-class")) isGroupElement = true;
					if(nodeElement.equals("solution")) isChildElement = true;
						
					
					// Enter inside the stress-class element
					if(nodeElement != null && (isGroupElement || isChildElement))
					{
						// Iterate till the START_TAG equals the "title"
						while(!nodeElement.equals("id") )
						{
							solutionXml.next();
							if(solutionXml.getEventType()== XmlPullParser.START_TAG)
							nodeElement = solutionXml.getName();
						}
		
			//   The solutionXml currently points to the title start_tag. Go next to read the text
						solutionXml.next();
			// Read the text inside "title" and save to the stressclass arraylist.
						if(solutionXml.getEventType()== XmlPullParser.TEXT)
						{
							nodeValue = solutionXml.getText();
							
							if(isGroupElement)
							{
							parentId = nodeValue;
							isGroupElement = false;
							}
							else if(isChildElement)
							{
							childId = nodeValue;
							
							currentId = parentId + "#" + childId;
							
							
							isChildElement= false;
							if(solutionId.equals(currentId))
							{
								return true;
							}	
							
							
							}
							
						}
					}// end of isgroup or ischild
					
					
					
				}// end of start tag
				
				else if (eventType == XmlPullParser.END_DOCUMENT)
				{
					break;
				}
				eventType = solutionXml.next();
			}
		
		}// end of try
		catch (XmlPullParserException e) {e.printStackTrace();}
		catch (IOException e) {	e.printStackTrace(); }
		return false;
	}
	*/

	private void log(String string) {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG,string);
		
	}

}
