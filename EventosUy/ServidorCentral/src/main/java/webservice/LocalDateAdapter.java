package main.java.webservice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE;
    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return (v == null || v.isEmpty()) ? null : LocalDate.parse(v, F);
    }
    @Override
    public String marshal(LocalDate v) throws Exception {
        return (v == null) ? null : v.format(F);
    }
}