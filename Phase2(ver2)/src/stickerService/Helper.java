package stickerService;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class Helper {
	
	static final String SERVERROOT = "localhost:4434";
	static final int USERID = 0;
	static final int SHOPID = 1;
	static final int CUSTOMERID = 0;

	public static XMLGregorianCalendar getXMLGregorianCalendarNow() 
      throws DatatypeConfigurationException
{
  GregorianCalendar gregorianCalendar = new GregorianCalendar();
  DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
  XMLGregorianCalendar now = 
      datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
  return now;
}
	
	
}
