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
    public WritableData getNewInstance() {
        return new WritableGregorianCalendar();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL){
            Calendar gregorian = new GregorianCalendar();
            gregorian.set(Calendar.YEAR,year);
            gregorian.set(Calendar.MONTH,month);
            gregorian.set(Calendar.DATE,date);
            gregorian.set(Calendar.HOUR_OF_DAY,hour);
            gregorian.set(Calendar.MINUTE,minute);
            gregorian.set(Calendar.SECOND,second);
        }
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        return null;
    }
}
