###  LeveledStorage
<br> </br>
> 빠르고 안정적인 깊이 기반 데이터 저장소

레벨스토리지는 기본적으로 속도와 안정성을 위해 개발된 저장 프로젝트입니다.

자바의 모든 기본형 데이터를 저장할 수 있으며, WritableData를 상속받아 데이터를 작성해주는 코드를 작성하면

해당 클래스도 저장이 가능합니다.


## 작동 원리

레벨 스토리지는 ```ObjectStream```을 사용하여 기본형 데이터를 빠르게 저장하게 설계되어 있습니다.

데이터 저장 클래스, ```AccessibleDataStorage```는 입력된 주기마다 1회씩 저장을 실행하며,

저장 시도 횟수가 스토리지 레벨 이상이 되면 자동으로 저장이 실행되고 다음 레벨의 스토리지로 데이터가 넘어갑니다.

만약 최근에 접근된 데이터가 존재하면 해당 데이터는 0레벨 스토리지로 이동됩니다.


## 목표


- [ ] Add all Collection
    - [ ] Add Lists (Java Branch)
        - [x] ~~Add ArrayList~~
        - [x] ~~Add LinkedList~~
        - [ ] Add CopyOnWriteArrayList
        - [ ] Add Stack
        - [ ] Add Vector
    - [ ] Add Maps (Java Branch)
        - [x] ~~Add HashMap~~
        - [ ] Add HashTable
        - [ ] Add EnumMap
        - [ ] Add ConcurrentHashMap
        - [ ] Add ConcurrentSkipListMap
        - [ ] Add TreeMap
        - [ ] Add IdentityHashMap
        - [ ] Add Properties
     - [ ] Add Auto-Identitier (Java Branch)
        - [x] ~~Add Calendar Identitier~~ (Completed,but have to check another calendars)
        - [ ] Add Map Identitier
        - [ ] Add List Identitier
        - [ ] ~~Add Iterator Identitier~~ (Abandonded - Cannot find iterator classes)


#### 작동 원리 - 데이터 이동
```
0레벨 스토리지

데이터: [0,1,2,3,4,5]

1레벨 스토리지

데이터: [6,7,8]




* 저장 시도

0레벨 스토리지

데이터: []

- 저장됨 : [1,2,3,4,5]

1레벨 스토리지

데이터: [0,1,2,3,4,5]


2레벨 스토리지

데이터: [6,7,8]

```

