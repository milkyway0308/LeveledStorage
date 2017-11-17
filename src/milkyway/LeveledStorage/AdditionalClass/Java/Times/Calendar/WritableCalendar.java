package milkyway.LeveledStorage.AdditionalClass.Java.Times.Calendar;

import milkyway.LeveledStorage.AdditionalClass.Java.Times.Calendar.Impl.WritableGregorianCalendar;
import milkyway.LeveledStorage.Data.WritableData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class WritableCalendar implements WritableData{
    private static int hash = "WritableCalendar".hashCode();

    protected Calendar main;

    public WritableCalendar(Calendar calc){
        this.main = calc;
    }

    public WritableCalendar(){

    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeInt(main.get(Calendar.YEAR));
        stream.writeInt(main.get(Calendar.MONTH));
        stream.writeInt(main.get(Calendar.DATE));
        stream.writeInt(main.get(Calendar.HOUR_OF_DAY));
        stream.writeInt(main.get(Calendar.MINUTE));
        stream.writeInt(main.get(Calendar.SECOND));
        stream.writeInt(main.get(Calendar.MILLISECOND));


    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        main = getCalendar(stream.readInt()
                ,stream.readInt(),stream.readInt(),stream.readInt(),stream.readInt(),stream.readInt(),stream.readInt()
        );
    }

    protected abstract Calendar getCalendar(int year,int month,int date,int hour,int minute,int second,int millisecond);

    public static WritableCalendar getCalendar(Calendar calc){
        if(calc instanceof GregorianCalendar)
            return new WritableGregorianCalendar(calc);

        return null;
    }


}

