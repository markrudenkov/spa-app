package com.future_rates.base_classifier.model;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import org.apache.commons.lang3.ArrayUtils;
import com.future_rates.rates.model.Query.Rate;

import java.util.ArrayList;
import java.util.List;

public class RateInstance {

    protected int atributes;

    public RateInstance(int atributes) {
        this.atributes = atributes;
    }

    protected Instance createInstance(int classValuePosition, List<Rate> rates) {
        Instance instance = new DenseInstance(getAtributesArray(classValuePosition, rates));
        instance.setClassValue(estimateClassValue(classValuePosition, rates));
        return instance;
    }

    public Instance createUnclasifiedInstance(List<Rate> rates) {
        Instance instance = new DenseInstance(getAtributesArray(this.atributes, rates));
        return instance;
    }

    private double[] getAtributesArray(int classValuePosition, List<Rate> rates) {
        List<Double> atributesList = new ArrayList<Double>();
        for (int j = classValuePosition - this.atributes; j < classValuePosition; j++) {
            Rate rate = rates.get(j);
            atributesList.add(rate.getOpen().doubleValue());
            atributesList.add(rate.getHigh().doubleValue());
            atributesList.add(rate.getLow().doubleValue());
            atributesList.add(rate.getClose().doubleValue());
        }
        double[] instanceAtributes = ArrayUtils.toPrimitive(atributesList.toArray(new Double[atributesList.size()]));
        return instanceAtributes;
    }

    private String estimateClassValue(int classValuePosition, List<Rate> rates) {
        Rate rate = rates.get(classValuePosition);
        String classValue;
        if (rate.getOpen().doubleValue() - rate.getClose().doubleValue() > 0) {
            classValue = "black";
        } else {
            classValue = "white";
        }
        return classValue;
    }

    public int getAtributes() {
        return this.atributes;
    }

    public void setAtributes(int atributes) {
        this.atributes = atributes;
    }
}
