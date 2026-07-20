package main.java.webservice;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stringArray")
public class StringArrayWrapper {
    private List<String> values;

    public StringArrayWrapper() {}
    public StringArrayWrapper(List<String> values) {
        this.values = values;
    }

    @XmlElement(name = "item")
    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
