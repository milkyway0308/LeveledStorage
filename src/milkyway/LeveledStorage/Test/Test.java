package milkyway.LeveledStorage.Test;

import milkyway.LeveledStorage.AccessibleDataStorage;
import milkyway.LeveledStorage.Data.DataWritableRegistry;
import milkyway.LeveledStorage.Exception.LeveledStorageException;
import milkyway.LeveledStorage.Test.Writable.TestWritable;

public class Test {
    public static void main(String[] args) {

        DataWritableRegistry.registerWritable(new TestWritable(499));
        try {
            AccessibleDataStorage<Integer, TestWritable> storage = new AccessibleDataStorage<>("C:/PluginTest/TestLoc", "Test<Number>", 4, true, true);
            storage.startSchedule();

            /*
            for(int i = 0;i < 4000;i++){
                storage.setValue(i,new TestWritable(i));
                if(i % 300 == 0)
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
            storage.forceSave();
             */
            storage.printTest();
            storage.stopTask();
        } catch (LeveledStorageException e) {
            e.printStackTrace();
        }
    }

    /*
    StringUtil.extractIntegers("00");
        String eval = String.valueOf(Long.MIN_VALUE);
        long time =  System.currentTimeMillis();
        for(int i = 0;i < 99999999;i++){
            Long.parseLong(eval);
        }

        System.out.println("ParseLong used " + (System.currentTimeMillis() - time) + "ms");

        time =  System.currentTimeMillis();
        for(int i = 0;i < 99999999;i++){
            StringUtil.extractLong(eval);
        }

        System.out.println("extractLong used " + (System.currentTimeMillis() - time) + "ms");
     */
}
