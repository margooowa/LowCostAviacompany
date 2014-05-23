package com.lowcost.letters;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


public class CreateTextForLetter {
	static java.util.Date today = new java.util.Date();
	static Timestamp timestampToday = new Timestamp(today.getTime());
	
	public static String createLetter(BigDecimal fullPrice,String name,String surname,
			String telephone,String email,int amountTick,String fromCity,
			String toCity,Date dateDeparture, Time timeDeparture,Date dateArrival, Time timeArrival, String BookingNumber){
	String txt="��������� ������,"+"\n"+
	"���������� ��� �� ��, ��� ��������������� �������� ukrwings.com.ua"+"\n"+
	"�� ��������� ������ ��������� �������������  ������������ � ���������� ��� ������. "+"\n"+
	"���� ������: "+dateDeparture+"\n"+
	"����� � ������: "+fullPrice+ "���."+"\n"+
	"����������, ���� �������� ����� ��� ������ 3 ���, ����� ���� ���������� ���������.(����� ����������:"+timestampToday+" )"+"\n"+	 
	"�� �������� ����������� �� ������ �� ������� ����������� ������."+
	"����� ������� ���������� ������� � ��������� (�������, �������, ���� � �.�) ������������ �������� �������� ������������."+"\n"+
	"���� �� ������ ������ �����-���� ��������� � ���� ������������, ����������, ��������� � ������ ����������� �� ��������"+"\n"+
	"+38044-406-71-34; 093-377-33-77 ��� ����������� ����� margooowa91@gmail.com � ���� ������ ������ �����."+"\n"+	 
	"���������� � ��������� ���� ������������ �����������:"+"\n"+"\n"+
	name+" "+surname+"\n"+ 
	"����� ������������: "+ BookingNumber+"\n"+
	"���������� �������: "+ telephone+"\n"+
	"E-mail: "+email+"\n"+
	"���������� ����: "+name+" "+surname+"\n"+
	"���������� �������: "+amountTick+"\n"+
	"����� �����: "+fullPrice+"\n"+
	"����������: "+"\n"+
	"����� ������: ������ ���������� ���������"+"\n"+"\n"+
	"���������� � ������"+"\n"+
	"�� ������: "+fromCity+"\n"+
	"� �����: "+toCity+"\n"+
	"����� ��������: "+timeDeparture+"\n"+
	 "���� ��������: "+dateDeparture+"\n"+
	 "����� ��������: "+timeArrival+"\n"+
	 "���� ��������: "+dateArrival+"\n"+
	 "\n"+"\n"+
	"� ���������, ��� �UKR WINGS�";
	return txt;
}
	
}
