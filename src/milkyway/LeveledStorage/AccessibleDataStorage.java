package milkyway.LeveledStorage;

import milkyway.LeveledStorage.Configuration.StorageConfiguration;
import milkyway.LeveledStorage.Exception.FileAlreadyExistException;
import milkyway.LeveledStorage.Exception.FileNameFormatIncorrectException;
import milkyway.LeveledStorage.Exception.LeveledStorageException;
import milkyway.LeveledStorage.Util.PatternCrashReplacer;
import milkyway.LeveledStorage.Util.StringUtil;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessibleDataStorage<K, V> {
    private List<StoredData<K, V>> storageLevels = new ArrayList<>();

    private Queue<Map.Entry<K, V>> temporyQueue = new ArrayDeque<>();


    private StorageConfiguration config = new StorageConfiguration();

    private boolean isSaving = false;

    private boolean isMoving = false;

    private Pattern FileFormat;

    private boolean stopTask = false;

    private final Object lock = new Object();

    private final Object saveLock = new Object();


    public AccessibleDataStorage(String folderLocation, String fileNameFormat, int maxDeepLevel,int saveCycle, boolean asyncMove, boolean asyncSave) throws LeveledStorageException {
        if (!fileNameFormat.contains("<Number>"))
            throw new FileNameFormatIncorrectException();
        config.setSaveAsync(asyncSave);
        config.setDataMoveAsync(asyncMove);
        config.setSaveCycle(saveCycle);
        FileFormat = Pattern.compile(PatternCrashReplacer.replacePatternCrashItems(fileNameFormat).replace("\\<Number\\>", "(\\d+)"));
        File folder = new File(folderLocation);
        if (folder.exists()) {
            if (folder.isFile())
                throw new FileAlreadyExistException(folderLocation);
            for (File file : folder.listFiles()) {
                try {
                    Matcher matcher = FileFormat.matcher(file.getName());
                    if (matcher.find()) {
                        storageLevels.add(new StoredData<K, V>(folderLocation, fileNameFormat).loadData(file).setStoragePriority(StringUtil.extractIntegers(matcher.group(1))));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else
            folder.mkdirs();
        if (maxDeepLevel <= 0) {
            storageLevels.add(new StoredData<>(folderLocation, fileNameFormat));
        } else
            for (int i = storageLevels.size(); i < maxDeepLevel; i++) {
                StoredData<K, V> kv = new StoredData<>(folderLocation, fileNameFormat);
                kv.setStoragePriority(i);
                storageLevels.add(kv);
            }
    }

    public StorageConfiguration getConfig() {
        return config;
    }

    public V getValue(K key) {
        synchronized (lock) {
            StoredData<K, V> last = storageLevels.get(0);
            for (StoredData<K, V> kv : storageLevels) {
                V v = kv.accessData(key, last);
                if (v != null)
                    return v;
            }
        }
        return null;
    }

    public V setValue(K key, V value) {
        getValue(key);
        synchronized (lock) {
            return storageLevels.get(0).storeData(key, value);
        }
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isSaving() {
        return isSaving;
    }



    public void startSchedule() {
        final Thread currentThread = Thread.currentThread();
        new Thread() {
            int currentTime = 0;

            @Override
            public void run() {
                while (!stopTask) {
                    if (currentTime > getConfig().getSaveCycle()) {
                        currentTime = 0;
                        saveStorage(currentThread, false);
                        moveStorage(currentThread);
                    }
                    currentTime++;
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void forceSave() {
        Thread currentThread = Thread.currentThread();
        saveStorage(currentThread, true);
        //  moveStorage(currentThread);
    }

    public void stopTask() {
        stopTask = true;
    }

    private void saveStorage(Thread threadCurrent, boolean force) {
        isSaving = true;
        if (!force) {
            if (config.isSaveAsync()) {
                new Thread() {
                    @Override
                    public void run() {
                        synchronized (saveLock) {
                            for (StoredData<K, V> lv : storageLevels)
                                if (lv.addTime())
                                    lv.saveData();
                        }
                        isSaving = false;
                    }
                }.start();
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        threadCurrent.suspend();
                        synchronized (saveLock) {
                            for (StoredData<K, V> lv : storageLevels)
                                if (lv.addTime())
                                    lv.saveData();
                        }
                        isSaving = false;
                        threadCurrent.resume();
                    }
                }.start();
            }
        } else {
            if (config.isSaveAsync()) {
                new Thread() {
                    @Override
                    public void run() {
                        synchronized (saveLock) {
                            for (StoredData<K, V> lv : storageLevels)
                                lv.saveData();
                        }
                        isSaving = false;
                    }
                }.start();
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        threadCurrent.suspend();
                        synchronized (saveLock) {
                            for (StoredData<K, V> lv : storageLevels)
                                lv.saveData();
                        }
                        isSaving = false;
                        threadCurrent.resume();
                    }
                }.start();
            }
        }
    }

    private void moveStorage(Thread threadCurrent) {
        if (config.isDataMoveAsync()) {
            new Thread() {
                @Override
                public void run() {
                    while (isSaving)
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    isMoving = true;
                    synchronized (lock) {
                        for (int i = storageLevels.size() - 2; i >= 0; i--) {
                            if (storageLevels.get(i).doSave())
                                storageLevels.get(i).moveData(storageLevels.get(i + 1));
                        }
                    }
                    isMoving = false;

                }
            }.start();
        } else {
            while (isSaving)
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            isMoving = true;
            synchronized (lock) {
                for (int i = 0; i < storageLevels.size() - 1; i++) {
                    if (storageLevels.get(i).doSave())
                        storageLevels.get(i).moveData(storageLevels.get(i + 1));
                }
            }
            isMoving = false;
        }
    }

    public void printTest() {
        synchronized (lock) {
            for (StoredData<K, V> kv : storageLevels) {
                System.out.println(kv);
            }
        }
    }

    @Deprecated
    private void storeData(K key, V Value) {
        storageLevels.get(0).storeData(key, Value);
    }

    @Deprecated
    private void storeData(int storage, K key, V Value) {
        storageLevels.get(Math.min(Math.max(0, storage), storageLevels.size() - 1)).storeData(key, Value);
    }

}
