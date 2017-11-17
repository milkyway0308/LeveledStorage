package milkyway.LeveledStorage.AdditionalClass.Java.Times.Calendar.Impl;

import milkyway.LeveledStorage.AdditionalClass.Java.Times.Calendar.WritableCalendar;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class WritableGregorianCalendar extends WritableCalendar{

    public WritableGregorianCalendar(Calendar calc){
        super(calc);
    }

    public WritableGregorianCalendar(){
        super();
    }

    @Override
    protected Calendar getCalendar(int year, int month, int date, int hour, int minute, int second, int millisecond) {
        Calendar calc = new GregorianCalendar(year,month,date,hour,minute,second);
        calc.set(Calendar.MILLISECOND,millisecond);
        return calc;
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableGregorianCalendar();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL){
            return super.main;
        }
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        return null;
    }
}

