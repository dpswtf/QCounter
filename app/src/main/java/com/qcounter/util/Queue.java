package com.qcounter.util;


public class Queue {
    private String queueName;
    private String serviceName;

    // String because, for example, we can have either E01 or just 01.
    private String currentNumberName;


    public Queue(String queueName, String serviceName, String currentNumberName){
        this.queueName = queueName;
        this.serviceName = serviceName;
        this.currentNumberName = currentNumberName;
    }

    public Queue(String queueName, String serviceName){
        this.queueName = queueName;
        this.serviceName = serviceName;
    }

    public Queue(String queueName){
        this.queueName = queueName;
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

    public String getCurrentNumberName() {
        return currentNumberName;
    }

    public void setCurrentNumberName(String numberName) {
        this.currentNumberName = numberName;
    }


}
