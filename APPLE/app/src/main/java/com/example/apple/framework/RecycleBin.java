package com.example.apple.framework;

import java.util.ArrayList;
import java.util.HashMap;

public class RecycleBin {
    private static HashMap<Class, ArrayList<Recyclable>> recycleBin = new HashMap<>();

    public static void init() {
        recycleBin.clear();
    }

    public static Recyclable get(Class clazz) {
        ArrayList<Recyclable> bin = recycleBin.get(clazz);
        if (bin == null) return null;   // 한번도 재활용된적이 없는 경우
        if (bin.size() == 0) return null;   // 재활용 된 적이 있지만 재황용 할 수 있는 객체가 없는 경우
        return bin.remove(0);
    }

    public static void add(Recyclable object) {
        Class clazz = object.getClass();
        ArrayList<Recyclable> bin = recycleBin.get(clazz);
        if (bin == null) {      // 최초로 해당 클래스의 객체가 add 되면 arraylist가 없으므로 null이라서 오류발생
            bin = new ArrayList<>();
            recycleBin.put(clazz, bin);
        }
        if (bin.indexOf(object) >= 0) {
            // 이미 재활용 대상인 경우 또 추가하지 않도록
            return;
        }
        bin.add(object);
    }
}
