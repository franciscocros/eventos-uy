package main.java.webservice;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;

public class StringArrayAdapter extends XmlAdapter<StringArrayWrapper, String[]> {

    @Override
    public String[] unmarshal(StringArrayWrapper v) {
        return v != null && v.getValues() != null
                ? v.getValues().toArray(new String[0])
                : new String[0];
    }

    @Override
    public StringArrayWrapper marshal(String[] v) {
        return v != null ? new StringArrayWrapper(Arrays.asList(v)) : new StringArrayWrapper();
    }
}
