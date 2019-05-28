package cn.it.yip.beans.propertyeditors;


import cn.it.yip.utils.NumberUtils;
import cn.it.yip.utils.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-21 01:48
 **/
public class CustomNumberEditor extends PropertyEditorSupport {

    private final Class<? extends Number> numberClass;
    private final NumberFormat numberFormat;
    private final boolean allowEmpty;

    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) {
        this(numberClass, null, allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty) {
        if (numberClass == null && Number.class.isAssignableFrom(numberClass))
            throw new IllegalArgumentException("property class must be a subclass of Number");
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setValue(Object value) {
        if(value instanceof Number){
            super.setValue(NumberUtils.convertNumberToTargetClass((Number) value,this.numberClass));
        }else
            super.setValue(value);
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        if (value == null)
            return null;
        else if (this.numberFormat != null)
            return this.numberFormat.format(value);
        else
            return value.toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text))
            setValue(null);
        else if (this.numberFormat != null)
            setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        else
            setValue(NumberUtils.parseNumber(text, this.numberClass));
    }
}
