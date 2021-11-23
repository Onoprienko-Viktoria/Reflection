package com.luxoft.testclasses;

public class Employee extends Person implements TestInterface {
    private String name;
    private int age;
    private long id;
    private int workedDays;
    private boolean isWorking;

    public Employee(String name, int age, long id, int workedDays, boolean isWorking) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.workedDays = workedDays;
        this.isWorking = isWorking;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getIsWorking() {
        return isWorking;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean isWorking() {
        isWorking = !isWorking;
        return isWorking;
    }

    boolean isWalking(boolean isWalking) {
        return isWalking;
    }

    private final int getRate(int multiplier) {
        return this.workedDays * multiplier;
    }

    public final int getWorkedHours(int workedHoursInDay, boolean isWorking) {
        return this.workedDays * workedHoursInDay;
    }
}
