package milkyway.LeveledStorage.AdditionalClass.Java.Times.Calendar;

import milkyway.LeveledStorage.AdditionalClass.Java.Times.Calendar.Impl.WritableGregorianCalendar;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class WritableCalendar implements WritableData{
    private static int hash = "WritableCalendar".hashCode();

    protected int year;

    protected int month;

    protected int date;

    protected int hour;

    protected int minute;

    protected int second;

    public WritableCalendar(Calendar calc){
        year = calc.get(Calendar.YEAR);
        month = calc.get(Calendar.MONTH);
        date = calc.get(Calendar.DATE);
        hour = calc.get(Calendar.HOUR_OF_DAY);
        minute = calc.get(Calendar.MINUTE);
        second = calc.get(Calendar.SECOND);
    }

    public WritableCalendar(){

    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeInt(year);
        stream.writeInt(month);
        stream.writeInt(date);
        stream.writeInt(hour);
        stream.writeInt(minute);
        stream.writeInt(second);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        year = stream.readInt();
        month = stream.readInt();
        date = stream.readInt();
        hour = stream.readInt();
        minute = stream.readInt();
        second = stream.readInt();
    }

    public static WritableCalendar getCalendar(Calendar calc){
        if(calc instanceof GregorianCalendar)
            return new WritableGregorianCalendar(calc);
        return null;
    }


}
