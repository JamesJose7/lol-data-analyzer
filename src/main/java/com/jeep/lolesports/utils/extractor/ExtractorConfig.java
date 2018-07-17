package com.jeep.lolesports.utils.extractor;

public class ExtractorConfig {
    private int horas;
    private int minutos;

    public ExtractorConfig(int horas, int minutos) {
        this.horas = horas;
        this.minutos = minutos;
    }

    public ExtractorConfig() {
        horas = 1;
        minutos = 1;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
}
