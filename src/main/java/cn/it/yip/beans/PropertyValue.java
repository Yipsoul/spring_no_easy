package cn.it.yip.beans;

/**
 * property标签的对象，
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-19 15:10
 **/
public class PropertyValue {
    private String name;
    private Object value;
    private boolean converted = false;
    private Object convertedValue;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {

        this.value = value;
    }

    public synchronized boolean isConverted() {
        return this.converted;
    }

    public synchronized void setConvertedValue(Object convertedValue) {
        this.converted = true;
        this.convertedValue = convertedValue;
    }

    public synchronized Object getConvertedValue() {
        return convertedValue;
    }
}
