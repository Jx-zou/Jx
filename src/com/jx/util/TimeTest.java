package com.jx.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeTest {

    private final Class<? extends TimeTest> tClass = this.getClass();
    private String PROMPT_PREFIX = "[" + tClass.getSimpleName() + "]:";
    private int DEFAULT_SPACE = 7;

    public void testTime() {
        long time;
        long fastestTime = 0;
        String fastMethodName = "";
        List<Method> testMethods = getTestMethod();
        for (int i = 0; i < testMethods.size(); i++) {
            Method method = testMethods.get(i);
            String name = method.getName();
            if (name.startsWith("testMethod")) {
                try {
                    time = System.currentTimeMillis();
                    method.invoke(this);
                    time = System.currentTimeMillis() - time;
                    if (i == 0) {
                        fastestTime = time;
                        fastMethodName = name;
                    }
                    if (time < fastestTime) {
                        fastestTime = time;
                        fastMethodName = name;
                    }
                    RectangleCenteredPromptBox(PROMPT_PREFIX + name + "-->invoke参考测试时间:" + formatTime(time), "-", DEFAULT_SPACE);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    System.out.println(PROMPT_PREFIX + name + "-->invoke错误.");
                    e.printStackTrace();
                }
            }
        }
        RectangleCenteredPromptBox(PROMPT_PREFIX + fastMethodName + "-->最快,invoke参考测试时间:" + formatTime(fastestTime), "=", DEFAULT_SPACE);
    }

    private void RectangleCenteredPromptBox(String prompt, String frame, int space) {
        int chineseCharNum = findChineseCharNum(prompt);
        int len = prompt.length() + space * 2 + 3;
        if (chineseCharNum > 0){
            len = len + chineseCharNum / 2;
        }

        StringBuilder line = new StringBuilder();
        StringBuilder tip = new StringBuilder();
        for (int i = 0; i < len; i++) {
            line.insert(0, frame);
        }
        tip.insert(0, "||");
        for (int i = 0; i < space * 2; i++) {
            tip.insert(1, " ");
        }
        tip.insert(space + 1, prompt);
        System.out.println(line);
        System.out.println(tip);
        System.out.println(line);
    }

    private int findChineseCharNum(String tip) {
        int num = 0;
        if (isContainChinese(tip)) {
            for (char c : tip.toCharArray()) {
                if (String.valueOf(c).getBytes(StandardCharsets.UTF_8).length > 1) {
                    num++;
                }
            }
        }
        return num;
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    private List<Method> getTestMethod() {
        List<Method> methodList = new ArrayList<>();
        for (Method method : tClass.getMethods()) {
            if (method.getName().startsWith("testMethod")) {
                methodList.add(method);
            }
        }
        return methodList;
    }

    public int getDEFAULT_SPACE() {
        return DEFAULT_SPACE;
    }

    public void setDEFAULT_SPACE(int DEFAULT_SPACE) {
        this.DEFAULT_SPACE = DEFAULT_SPACE;
    }

    public String getPROMPT_PREFIX() {
        return PROMPT_PREFIX;
    }

    public void setPROMPT_PREFIX(String PROMPT_PREFIX) {
        this.PROMPT_PREFIX = PROMPT_PREFIX;
    }

    /**
     * 时间格式化
     *
     * @param time 所需格式化的毫秒值
     * @return 返回格式化后的时间字符串
     */
    public String formatTime(long time) {
        StringBuilder timeStr = null;
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    timeStr = new StringBuilder(time % 1000 + "ms");
                    if (time < 1000) {
                        return timeStr.toString();
                    }
                    time = time / 1000;
                    break;
                case 1:
                    timeStr.insert(0, time % 60 + "s ");
                    if (time < 60) {
                        return timeStr.toString();
                    }
                    time = time / 60;
                    break;
                case 2:
                    timeStr.insert(0, time % 60 + "m:");
                    if (time < 60) {
                        return timeStr.toString();
                    }
                    time = time / 60;
                    break;
                case 3:
                    timeStr.insert(0, time % 60 + "h:");
                    if (time < 24) {
                        return timeStr.toString();
                    }
                    time = time / 24;
                    break;
                case 4:
                    timeStr.insert(0, time + "d:");
                    return timeStr.toString();
            }
        }
        return "";
    }
}
