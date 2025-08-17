package de.bcxp.challenge.weather;

import java.util.Objects;

public class Weather {

    private final int dayOfMonth;
    private final int minimumTemperature;
    private final int maximumTemperature;
    private final int temperatureSpread;


    public Weather(Builder builder) {
        this.dayOfMonth = builder.dayOfMonth;
        this.minimumTemperature = builder.minimumTemperature;
        this.maximumTemperature = builder.maximumTemperature;
        this.temperatureSpread = builder.temperatureSpread;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getMinimumTemperature() {
        return minimumTemperature;
    }

    public int getMaximumTemperature() {
        return maximumTemperature;
    }

    public int getTemperatureSpread() {
        return temperatureSpread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Weather weather = (Weather) o;
        return dayOfMonth == weather.dayOfMonth && minimumTemperature == weather.minimumTemperature
                && maximumTemperature == weather.maximumTemperature && temperatureSpread == weather.temperatureSpread;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfMonth, minimumTemperature, maximumTemperature, temperatureSpread);
    }

    public static class Builder {
        private int dayOfMonth;
        private int minimumTemperature;
        private int maximumTemperature;
        private int temperatureSpread;

        public Builder dayOfMonth(int dayOfMonth) {
            this.dayOfMonth = dayOfMonth;
            return this;
        }

        public Builder minimumTemperature(int minimumTemperature) {
            this.minimumTemperature = minimumTemperature;
            return this;
        }

        public Builder maximumTemperature(int maximumTemperature) {
            this.maximumTemperature = maximumTemperature;
            return this;
        }

        public Builder temperatureSpread(int temperatureSpread) {
            this.temperatureSpread = temperatureSpread;
            return this;
        }

        public Weather build() {
            return new Weather(this);
        }
    }
}
