package com.qcounter.util;


public class Queue {
    private String queueName;
    private String serviceName;

    // Sometimes we can have either a number or a number name
    private String numberName;
    private int currentNumber;

    public Queue(String queueName, String serviceName, String numberName){
        this.queueName = queueName;
        this.serviceName = serviceName;
        this.numberName = numberName;
    }

    public Queue(String queueName, String serviceName){
        this.queueName = queueName;
        this.serviceName = serviceName;
    }

    public Queue(String queueName){
        this.queueName = queueName;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getNumberName() {
        return numberName;
    }

    public void setNumberName(String numberName) {
        this.numberName = numberName;
    }


}
