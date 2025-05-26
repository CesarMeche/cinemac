package co.edu.uptc;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import co.edu.uptc.view.MainFrame;

public class sd {
    public static void main(String[] args) {

        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        MainFrame m = new MainFrame(null);
        m.init();
        m.add(datePanel, BorderLayout.EAST);
  

        String s=  model.getYear()+"-0"+(model.getMonth()+1)+"-"+model.getDay()+"T00:00:0.";
        System.out.println(s);
        LocalDateTime sd= LocalDateTime.now();
        System.out.println(sd);

    }

}
