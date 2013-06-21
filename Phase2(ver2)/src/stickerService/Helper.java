package stickerService;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import stickerApp.Adress;

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
	
	public static Adress createAdress (String familyName,
			String firstName,
			String street,
			String number,
			String postalCode,
			String city,
			String province,
			String country,
			String telephone,
			String email) {
		Adress adress = new Adress();
		adress.setCity(city);
		adress.setEmail(email);
		adress.setFamilyName(familyName);
		adress.setFirstName(firstName);
		adress.setNumber(number);
		adress.setPostalCode(postalCode);
		adress.setStreet(street);
		if(!"".equals(province)) adress.setProvince(province);
		adress.setCountry(country);
		if(!"".equals(telephone)) adress.setTelephone(telephone);
		if(!"".equals(email)) adress.setEmail(email);
		return adress;
	}
	
}
