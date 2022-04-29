package top.yztz.sched;

import android.util.Property;
import android.util.TypedValue;
import android.widget.TextView;

public class TextSizeProperty extends Property<TextView, Float> {

    public TextSizeProperty() {
        super(Float.class, "TextSize");
    }

    @Override
    public Float get(TextView object) {
        return object.getTextSize();
    }
    @Override
    public void set(TextView object, Float value) {
        object.setTextSize(TypedValue.COMPLEX_UNIT_PX, value);
    }
}
