package de.bcxp.challenge.countries;

import java.math.BigDecimal;
import java.util.Objects;

public class Country {

    private final String name;
    private final int population;
    private final double area;
    private final BigDecimal density;

    public Country(Builder builder) {
        this.name = builder.name;
        this.population = builder.population;
        this.area = builder.area;
        this.density = builder.density;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public double getArea() {
        return area;
    }

    public BigDecimal getDensity() {
        return density;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Country country = (Country) o;
        return population == country.population && Double.compare(area, country.area) == 0 && Objects.equals(name, country.name)
                && Objects.equals(density, country.density);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, population, area, density);
    }

    @Override
    public String toString() {
        return "Country{" + "name='" + name + '\'' + ", population=" + population + ", area=" + area + ", density=" + density + '}';
    }

    public static class Builder {
        private String name;
        private int population;
        private double area;
        private BigDecimal density;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder population(int population) {
            this.population = population;
            return this;
        }

        public Builder area(double area) {
            this.area = area;
            return this;
        }

        public Builder density(BigDecimal density) {
            this.density = density;
            return this;
        }

        public Country build() {
            return new Country(this);
        }
    }
}
