package com.lowcost.letters;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


public class CreateTextForLetterTwo {
	static java.util.Date today = new java.util.Date();
	static Timestamp timestampToday = new Timestamp(today.getTime());
	
	public static String createLetterTwo(BigDecimal fullPrice,String name,String surname,
			String telephone,String email,int amountTick,String fromCity,
			String toCity,Date dateDeparture, Time timeDeparture,Date dateArrival, Time timeArrival,
			String fromReturnCity,String toReturnCity,Date dateReturnDeparture,
			Time timeReturnDeparture,Date dateReturnArrival, Time timeReturnArrival, String BookingNumber){
	String txt="Уважаемый клиент,"+"\n"+
	"Благодарим Вас за то, что воспользовались сервисом ukrwings.com.ua"+"\n"+
	"Во вложенных файлах находится подтверждение  бронирования и информация для оплаты. "+"\n"+
	"Дата вылета туда: "+dateDeparture+"\n"+
	"Дата вылета обратно: "+dateReturnDeparture+"\n"+
	"Итого к оплате: "+fullPrice+ "ГРН."+"\n"+
	"Пожалуйста, срок действия счета для оплаты 3 дня, далее Ваша бронировка удаляется.(Время бронировка:"+timestampToday+" )"+"\n"+	 
	"Во вложении «Информация об оплате» Вы найдете необходимые данные."+
	"После покупки авиабилета возврат и изменения (фамилия, маршрут, дата и т.д) производятся согласно правилам авиакомпании."+"\n"+
	"Если Вы хотите внести какие-либо изменения в Ваше бронирование, пожалуйста, свяжитесь с нашими менеджерами по телефону"+"\n"+
	"+38044-406-71-34; 093-377-33-77 или электронной почте margooowa91@gmail.com в часы работы нашего офиса."+"\n"+	 
	"Информация о сделанном Вами бронировании авиабилетов:"+"\n"+"\n"+
	name+" "+surname+"\n"+ 
	"Номер бронирования: "+ BookingNumber+"\n"+
	"Контактный телефон: "+ telephone+"\n"+
	"E-mail: "+email+"\n"+
	"Контактное лицо: "+name+" "+surname+"\n"+
	"Количество билетов: "+amountTick+"\n"+
	"Общая сумма: "+fullPrice+"\n"+
	"Примечания: "+"\n"+
	"Форма оплаты: Оплата банковским переводом"+"\n"+"\n"+
	"Информация о билете туда"+"\n"+
	"Из города: "+fromCity+"\n"+
	"В город: "+toCity+"\n"+
	"Время отправки: "+timeDeparture+"\n"+
	 "Дата отправка: "+dateDeparture+"\n"+
	 "Время прибытия: "+timeArrival+"\n"+
	 "Дата прибытия: "+dateArrival+"\n"+"\n"+
	 "Информация о билете туда"+"\n"+
		"Из города: "+fromReturnCity+"\n"+
		"В город: "+toReturnCity+"\n"+
		"Время отправки: "+timeReturnDeparture+"\n"+
		 "Дата отправка: "+dateReturnDeparture+"\n"+
		 "Время прибытия: "+timeReturnArrival+"\n"+
		 "Дата прибытия: "+dateReturnArrival+"\n"+
	 "\n"+"\n"+
	"С уважением, Ваш «UKR WINGS»";
	return txt;
}
	
}
