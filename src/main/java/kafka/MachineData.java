package kafka;

import java.io.*;
import java.util.Date;
public class MachineData {
    public MachineData(String s, String value){}
    private int EngineId;
    private String EngineName;
    private Date StartDate;
    private int vTemp;
    private int Vibration;

    public MachineData(){}

    public MachineData(int id, String name,Date dt, int vtemp, int vibration ) {
        this.EngineId = id;
        this.EngineName = name;
        this.vTemp = vtemp;
        this.Vibration = vibration;
        this.StartDate = dt;
    }


    public int getEngineId() {
        return EngineId;
    }

    public void setEngineId(int engineId) {
        EngineId = engineId;
    }

    public String getEngineName() {
        return EngineName;
    }

    public void setEngineName(String engineName) {
        EngineName = engineName;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public int getvTemp() {
        return vTemp;
    }

    public void setvTemp(int vTemp) {
        this.vTemp = vTemp;
    }


    public int getVibration() {
        return Vibration;
    }

    public void setVibration(int vibration) {
        Vibration = vibration;
    }
}
