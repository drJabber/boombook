package rnk.bb.util.json;


import javax.json.bind.adapter.JsonbAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter implements JsonbAdapter<Date, String> {

    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date adaptFromJson(String s) throws Exception {
        return sdf.parse(s);
    }

    @Override
    public String adaptToJson(Date date) throws Exception {
        return sdf.format(date);
    }
}